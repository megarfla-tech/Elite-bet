package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.AnalyzedMatch
import com.example.data.repository.MatchRepository
import com.example.data.api.NetworkModule
import com.example.data.model.AnalysisReport
import com.example.data.model.RecommendedSingleBet
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import android.util.Log
import com.example.BuildConfig

sealed interface AnalysisUiState {
    object Idle : AnalysisUiState
    object Loading : AnalysisUiState
    data class Success(val report: AnalysisReport) : AnalysisUiState
    data class Error(val message: String) : AnalysisUiState
}

// Model to represent a composite multiple/accumulator recommendation
data class AccumulatorRecommendation(
    val title: String,
    val type: String, // "Ultra Segura", "Premium", "Agressiva", "Extrema"
    val targetOddRange: String,
    val selections: List<AccumulatorSelection>,
    val totalOdd: Double,
    val estimatedProbability: Double,
    val riskLevel: String // "Baixo", "Médio", "Alto", "Extremo"
)

data class AccumulatorSelection(
    val matchName: String,
    val selection: String,
    val odd: Double,
    val prob: Double
)

class MatchViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = MatchRepository(database.matchDao())

    // Stream of analyzed matches from Room DB
    val analyzedMatches: StateFlow<List<AnalyzedMatch>> = repository.allMatches
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _analysisState = MutableStateFlow<AnalysisUiState>(AnalysisUiState.Idle)
    val analysisState: StateFlow<AnalysisUiState> = _analysisState.asStateFlow()

    private val _selectedMatch = MutableStateFlow<AnalyzedMatch?>(null)
    val selectedMatch: StateFlow<AnalyzedMatch?> = _selectedMatch.asStateFlow()

    private val _showAnalysisDialog = MutableStateFlow(false)
    val showAnalysisDialog: StateFlow<Boolean> = _showAnalysisDialog.asStateFlow()

    // Dynamic Accumulators State
    private val _accumulators = MutableStateFlow<List<AccumulatorRecommendation>>(emptyList())
    val accumulators: StateFlow<List<AccumulatorRecommendation>> = _accumulators.asStateFlow()

    // Check if the API key is active/real
    val isApiKeyConfigured: Boolean
        get() {
            val key = BuildConfig.GEMINI_API_KEY.trim()
            return key.isNotEmpty() && key != "MY_GEMINI_API_KEY" && !key.contains("placeholder", ignoreCase = true)
        }

    init {
        viewModelScope.launch {
            // Seed DB if it's empty
            repository.checkAndSeedDatabase()
            
            // Once we have matches, recalculate/re-build our accumulators list dynmically
            analyzedMatches.collect { matches ->
                buildAccumulators(matches)
            }
        }
    }

    fun selectMatch(match: AnalyzedMatch) {
        _selectedMatch.value = match
    }

    fun clearSelectedMatch() {
        _selectedMatch.value = null
    }

    fun toggleAnalysisDialog(show: Boolean) {
        _showAnalysisDialog.value = show
    }

    fun runAnalysis(
        homeTeam: String,
        awayTeam: String,
        league: String,
        matchDate: String,
        homeOdd: Double,
        drawOdd: Double,
        awayOdd: Double
    ) {
        viewModelScope.launch {
            _analysisState.value = AnalysisUiState.Loading
            try {
                val report = repository.analyzeAndSaveMatch(
                    homeTeam = homeTeam,
                    awayTeam = awayTeam,
                    league = league,
                    matchDate = matchDate,
                    homeOdd = homeOdd,
                    drawOdd = drawOdd,
                    awayOdd = awayOdd
                )
                _analysisState.value = AnalysisUiState.Success(report)
                
                // Select the newly-saved match so the user sees detail screen
                val updatedList = repository.allMatches.firstOrNull() ?: emptyList()
                val newestMatch = updatedList.firstOrNull { 
                    it.homeTeam == homeTeam && it.awayTeam == awayTeam && !it.isSample 
                }
                if (newestMatch != null) {
                    _selectedMatch.value = newestMatch
                }
            } catch (e: Exception) {
                Log.e("MatchViewModel", "Analysis failed", e)
                _analysisState.value = AnalysisUiState.Error(e.message ?: "Erro desconhecido ao executar análise.")
            }
        }
    }

    fun deleteMatch(match: AnalyzedMatch) {
         viewModelScope.launch {
             if (_selectedMatch.value?.id == match.id) {
                 _selectedMatch.value = null
             }
             repository.deleteMatch(match)
         }
    }

    fun resetAnalysisState() {
        _analysisState.value = AnalysisUiState.Idle
    }

    /**
     * Parses the raw JSON stored in room database into the model class.
     */
    fun parseJsonToReport(json: String): AnalysisReport? {
        return try {
            NetworkModule.reportAdapter.fromJson(json)
        } catch (e: Exception) {
            Log.e("MatchViewModel", "Failed to deserialize local match JSON", e)
            null
        }
    }

    /**
     * Generates a curated, structured list of betting accumulators/múltiplas 
     * based on the active set of analyzed matches in the database.
     */
    private fun buildAccumulators(matches: List<AnalyzedMatch>) {
        if (matches.isEmpty()) {
            _accumulators.value = emptyList()
            return
        }

        val reportsAndMatches = matches.mapNotNull { match ->
            val report = parseJsonToReport(match.rawAnalysisJson)
            if (report != null) Pair(match, report) else null
        }

        if (reportsAndMatches.isEmpty()) return

        val accList = mutableListOf<AccumulatorRecommendation>()

        // 1. MÚLTIPLAs - ULTRA SEGURA (Permits: Over 0.5, DC, Over 1.5. Target Odd: 1.80 to 3.00)
        val ultraSelections = mutableListOf<AccumulatorSelection>()
        var ultraTotalOdd = 1.0
        var ultraCombinedProbability = 100.0

        // Grab highly probable items
        val safePool = reportsAndMatches.sortedByDescending { it.second.confidenceScore }
        for (item in safePool) {
            val report = item.second
            val match = item.first
            
            // Ultra safe bets like Double Chance or Over 1.5 with high prob
            val isSafe = report.recommendedSingleBet.probability >= 70.0
            if (isSafe && ultraTotalOdd < 2.5) {
                ultraSelections.add(
                    AccumulatorSelection(
                        matchName = "${match.homeTeam} vs ${match.awayTeam}",
                        selection = report.recommendedSingleBet.market,
                        odd = report.recommendedSingleBet.odd,
                        prob = report.recommendedSingleBet.probability
                    )
                )
                ultraTotalOdd *= report.recommendedSingleBet.odd
                ultraCombinedProbability *= (report.recommendedSingleBet.probability / 100.0)
            }
        }

        if (ultraSelections.isNotEmpty() && ultraTotalOdd >= 1.50) {
            accList.add(
                AccumulatorRecommendation(
                    title = "Múltipla Ultra Segura",
                    type = "Ultra Segura",
                    targetOddRange = "1.80 a 3.00",
                    selections = ultraSelections,
                    totalOdd = String.format("%.2f", ultraTotalOdd).replace(",", ".").toDouble(),
                    estimatedProbability = String.format("%.1f", ultraCombinedProbability).replace(",", ".").toDouble(),
                    riskLevel = "Muito Baixo"
                )
            )
        }

        // 2. MÚLTIPLA PREMIUM (Target Odd: 3.00 to 8.00)
        val premiumSelections = mutableListOf<AccumulatorSelection>()
        var premiumTotalOdd = 1.0
        var premiumCombinedProbability = 100.0

        val premiumPool = reportsAndMatches.sortedByDescending { it.second.recommendedSingleBet.ev }
        for (item in premiumPool) {
            val report = item.second
            val match = item.first
            if (premiumTotalOdd < 5.0) {
                premiumSelections.add(
                    AccumulatorSelection(
                        matchName = "${match.homeTeam} vs ${match.awayTeam}",
                        selection = report.recommendedSingleBet.market,
                        odd = report.recommendedSingleBet.odd,
                        prob = report.recommendedSingleBet.probability
                    )
                )
                premiumTotalOdd *= report.recommendedSingleBet.odd
                premiumCombinedProbability *= (report.recommendedSingleBet.probability / 100.0)
            }
        }

        if (premiumSelections.size >= 2) {
            accList.add(
                AccumulatorRecommendation(
                    title = "Múltipla Premium",
                    type = "Premium",
                    targetOddRange = "3.00 a 8.00",
                    selections = premiumSelections,
                    totalOdd = String.format("%.2f", premiumTotalOdd).replace(",", ".").toDouble(),
                    estimatedProbability = String.format("%.1f", premiumCombinedProbability).replace(",", ".").toDouble(),
                    riskLevel = "Médio"
                )
            )
        }

        // 3. MÚLTIPLA AGRESSIVA (Target Odd: 8.00 to 20.00)
        // Combine 3-4 interesting markets to shoot for positive odds
        val aggressiveSelections = mutableListOf<AccumulatorSelection>()
        var aggTotalOdd = 1.0
        var aggCombinedProbability = 100.0

        for (item in reportsAndMatches) {
            if (aggTotalOdd < 15.0) {
                // Synthesize an aggressive option slightly higher than recommended market or use direct
                val currentOdd = item.second.recommendedSingleBet.odd
                val currentMarket = item.second.recommendedSingleBet.market
                val currentProb = item.second.recommendedSingleBet.probability

                aggressiveSelections.add(
                    AccumulatorSelection(
                        matchName = "${item.first.homeTeam} vs ${item.first.awayTeam}",
                        selection = currentMarket,
                        odd = currentOdd,
                        prob = currentProb
                    )
                )
                aggTotalOdd *= currentOdd
                aggCombinedProbability *= (currentProb / 100.0)
            }
        }

        if (aggressiveSelections.size >= 3) {
            accList.add(
                AccumulatorRecommendation(
                    title = "Múltipla Agressiva",
                    type = "Agressiva",
                    targetOddRange = "8.00 a 20.00",
                    selections = aggressiveSelections,
                    totalOdd = String.format("%.2f", aggTotalOdd).replace(",", ".").toDouble(),
                    estimatedProbability = String.format("%.1f", aggCombinedProbability).replace(",", ".").toDouble(),
                    riskLevel = "Alto"
                )
            )
        }

        // 4. MÚLTIPLA EXTREMA (Target Odd: 20.00+)
        // Mix all games with speculative goals and match results
        val extremeSelections = mutableListOf<AccumulatorSelection>()
        var extremeTotalOdd = 1.0
        var extremeCombinedProbability = 100.0

        for (i in reportsAndMatches.indices) {
            val item = reportsAndMatches[i]
            val report = item.second
            val match = item.first

            // Use slightly higher individual odds or accumulate everything
            val selectionMarket = if (i % 2 == 0) "Ambas Marcam e Over 2.5 Gols" else report.recommendedSingleBet.market
            val selectionOdd = if (i % 2 == 0) 2.20 else report.recommendedSingleBet.odd
            val selectionProb = if (i % 2 == 0) 50.0 else report.recommendedSingleBet.probability

            if (extremeTotalOdd < 40.0) {
                extremeSelections.add(
                    AccumulatorSelection(
                        matchName = "${match.homeTeam} vs ${match.awayTeam}",
                        selection = selectionMarket,
                        odd = selectionOdd,
                        prob = selectionProb
                    )
                )
                extremeTotalOdd *= selectionOdd
                extremeCombinedProbability *= (selectionProb / 100.0)
            }
        }

        if (extremeSelections.size >= 3) {
            accList.add(
                AccumulatorRecommendation(
                    title = "Múltipla Extrema",
                    type = "Extrema",
                    targetOddRange = "20.00+",
                    selections = extremeSelections,
                    totalOdd = String.format("%.2f", extremeTotalOdd).replace(",", ".").toDouble(),
                    estimatedProbability = String.format("%.2f", extremeCombinedProbability).replace(",", ".").toDouble(),
                    riskLevel = "Extremo"
                )
            )
        }

        _accumulators.value = accList
    }
}
