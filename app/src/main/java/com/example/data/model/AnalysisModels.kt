package com.example.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnalysisReport(
    val confidenceScore: Int,
    val recommendedSingleBet: RecommendedSingleBet,
    val layer1: Layer1Data,
    val layer2: Layer2Data,
    val layer3: Layer3Data,
    val layer4: Layer4Data,
    val layer5: Layer5Data,
    val conclusaoExecutiva: String
)

@JsonClass(generateAdapter = true)
data class RecommendedSingleBet(
    val market: String,
    val odd: Double,
    val probability: Double,
    val ev: Double,
    val confidence: String, // e.g. "Elite", "Excelente", "Boa"
    val justification: String
)

@JsonClass(generateAdapter = true)
data class Layer1Data(
    val homeRecentRecentG: List<String>, // list of last results or text summary
    val awayRecentRecentG: List<String>,
    val homeXG: Double,
    val awayXG: Double,
    val homeXGA: Double,
    val awayXGA: Double,
    val trend: String,
    val completions: String, // e.g. "12/5", home/away completions
    val cornersAndCards: String
)

@JsonClass(generateAdapter = true)
data class Layer2Data(
    val classification: String, // "🟢 Valor Positivo", "🟡 Neutro", "🔴 Sem Valor"
    val odDecline: String, // context on drop in odds
    val inflatedOdds: String, // context on inflated odds
    val biasFavoritism: String // comments on favoritism bias
)

@JsonClass(generateAdapter = true)
data class Layer3Data(
    val probWinHome: Double,
    val probDraw: Double,
    val probWinAway: Double,
    val probOver05: Double,
    val probOver15: Double,
    val probOver25: Double,
    val probOver35: Double,
    val probBTTS: Double,
    val estCorners: String,
    val estCards: String
)

@JsonClass(generateAdapter = true)
data class Layer4Data(
    val riskIndex: Int, // 0-100
    val injuries: String,
    val rosterRotation: String,
    val environmentalFactors: String, // weather, travel, crowd
    val pressureAndMotivation: String
)

@JsonClass(generateAdapter = true)
data class Layer5Data(
    val realProbability: Double,
    val impliedProbability: Double,
    val expectedValue: Double, // EV
    val isEvPositive: Boolean
)

@JsonClass(generateAdapter = true)
data class MatchProbabilityAnalysis(
    val probHome: Double,
    val probDraw: Double,
    val probAway: Double,
    val justification: String
)
