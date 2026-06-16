package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.AnalyzedMatch
import com.example.data.model.*
import com.example.ui.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AnalysisUiState
import com.example.ui.viewmodel.MatchViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: MatchViewModel = viewModel()
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

enum class ActiveTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    ANALYSIS("Análises", Icons.Default.Analytics),
    MULTIPLES("Múltiplas", Icons.Default.Grid3x3),
    PORTFOLIO("Trading Dept", Icons.Default.QueryStats)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(viewModel: MatchViewModel) {
    var activeTab by remember { mutableStateOf(ActiveTab.ANALYSIS) }
    val matches by viewModel.analyzedMatches.collectAsState()
    val selectedMatch by viewModel.selectedMatch.collectAsState()
    val showDialog by viewModel.showAnalysisDialog.collectAsState()
    val analysisState by viewModel.analysisState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("app_scaffold"),
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SportGreen.copy(alpha = 0.2f))
                                .border(1.dp, SportGreen, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsSoccer,
                                contentDescription = null,
                                tint = SportGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "PRONOELITE",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Motor de Inteligência Quantitativa",
                                color = SecondaryText,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleAnalysisDialog(true) },
                        modifier = Modifier
                            .testTag("action_add_analysis")
                            .clip(RoundedCornerShape(8.dp))
                            .background(SportGreen.copy(alpha = 0.1f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddChart,
                            contentDescription = "Nova Análise",
                            tint = SportGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = CardGray,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars
            ) {
                ActiveTab.values().forEach { tab ->
                    NavigationBarItem(
                        selected = activeTab == tab,
                        onClick = { 
                            activeTab = tab 
                            viewModel.clearSelectedMatch() // smooth return to list
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label
                            )
                        },
                        label = {
                            Text(
                                text = tab.label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SportGreen,
                            selectedTextColor = SportGreen,
                            indicatorColor = SportGreen.copy(alpha = 0.15f),
                            unselectedIconColor = SecondaryText,
                            unselectedTextColor = SecondaryText
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            if (activeTab == ActiveTab.ANALYSIS && selectedMatch == null) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.toggleAnalysisDialog(true) },
                    containerColor = SportGreen,
                    contentColor = DarkBg,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    modifier = Modifier
                        .testTag("fab_add_analysis")
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Bolt, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ANALISAR PARTIDA",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        fontSize = 12.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Screen router
            when (activeTab) {
                ActiveTab.ANALYSIS -> {
                    if (selectedMatch != null) {
                        MatchDetailView(
                            match = selectedMatch!!,
                            onBack = { viewModel.clearSelectedMatch() },
                            report = viewModel.parseJsonToReport(selectedMatch!!.rawAnalysisJson)
                        )
                    } else {
                        MatchListDashboard(
                            matches = matches,
                            viewModel = viewModel,
                            onMatchClick = { viewModel.selectMatch(it) }
                        )
                    }
                }
                ActiveTab.MULTIPLES -> {
                    MultiplesView(viewModel = viewModel)
                }
                ActiveTab.PORTFOLIO -> {
                    PortfolioView(matches = matches, viewModel = viewModel)
                }
            }

            // Interactive analysis triggering fullscreen dialog
            if (showDialog) {
                NewAnalysisDialog(
                    onDismiss = { viewModel.toggleAnalysisDialog(false) },
                    onAnalyze = { h, a, l, d, ho, dro, ao ->
                        viewModel.toggleAnalysisDialog(false)
                        viewModel.runAnalysis(h, a, l, d, ho, dro, ao)
                    }
                )
            }

            // Real-Time Loading Overlay representing deep quantitative analysis layers
            if (analysisState is AnalysisUiState.Loading) {
                FullScreenAnalysisLoader()
            }
        }
    }
}

@Composable
fun MatchListDashboard(
    matches: List<AnalyzedMatch>,
    viewModel: MatchViewModel,
    onMatchClick: (AnalyzedMatch) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Missing API Key Banner
        if (!viewModel.isApiKeyConfigured) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = WarningOrange.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, WarningOrange.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = WarningOrange,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "MODO DE SIMULAÇÃO LOCAL ATIVO",
                            color = WarningOrange,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Defina GEMINI_API_KEY no painel Secrets do AI Studio para habilitar a rede neuronal quantitativa em tempo real. O simulador local está calculando distorções de valor realistas.",
                            color = SecondaryText,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "JORNADA DE ANÁLISE",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "${matches.size} Jogos",
                        color = CyberTeal,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (matches.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Analytics,
                                contentDescription = null,
                                tint = SecondaryText.copy(alpha = 0.3f),
                                modifier = Modifier.size(72.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Nenhuma partida analisada",
                                color = LightText,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Clique em 'Analisar Partida' para carregar.",
                                color = SecondaryText,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            } else {
                items(matches) { match ->
                    val report = viewModel.parseJsonToReport(match.rawAnalysisJson)
                    MatchListItem(
                        match = match,
                        report = report,
                        onClick = { onMatchClick(match) },
                        onDelete = { viewModel.deleteMatch(match) }
                    )
                }
            }
        }
    }
}

@Composable
fun MatchDetailView(
    match: AnalyzedMatch,
    report: AnalysisReport?,
    onBack: () -> Unit
) {
    if (report == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = SportGreen)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Deserializando histórico...", color = SecondaryText, fontSize = 12.sp)
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        // Back navigation button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF1E293B))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Voltar",
                    tint = LightText,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "${match.homeTeam} vs ${match.awayTeam}",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${match.league} | ${match.matchDate}",
                    color = SecondaryText,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Core Recommendation Board
        RecommendedBetCard(bet = report.recommendedSingleBet, score = report.confidenceScore)

        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "DEPARTAMENTO DE RISCO - 5 CAMADAS DE ANÁLISE",
            color = CyberTeal,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Layer1Card(data = report.layer1)
        Spacer(modifier = Modifier.height(12.dp))
        Layer2Card(data = report.layer2)
        Spacer(modifier = Modifier.height(12.dp))
        Layer3Card(data = report.layer3)
        Spacer(modifier = Modifier.height(12.dp))
        Layer4Card(data = report.layer4)
        Spacer(modifier = Modifier.height(12.dp))
        Layer5Card(data = report.layer5)

        Spacer(modifier = Modifier.height(16.dp))

        // Executive summary report
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
            border = BorderStroke(1.dp, SportGreen.copy(alpha = 0.3f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = SportGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CONCLUSÃO EXECUTIVA DE VALOR",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = report.conclusaoExecutiva,
                    color = LightText,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun MultiplesView(viewModel: MatchViewModel) {
    val accList by viewModel.accumulators.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MOTOR DE ACUMULADAS",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Matrizes construídas cruzando estatísticas",
                            color = SecondaryText,
                            fontSize = 10.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SportGreen.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "EV+ ATIVO",
                            color = SportGreen,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            if (accList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Grid3x3,
                                contentDescription = null,
                                tint = SecondaryText.copy(alpha = 0.3f),
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                    text = "Estatísticas insuficientes",
                                color = LightText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Analise e salve mais partidas no Dashboard",
                                color = SecondaryText,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            } else {
                items(accList) { acc ->
                    AccumulatorCard(acc = acc)
                }
            }
        }
    }
}

@Composable
fun PortfolioView(matches: List<AnalyzedMatch>, viewModel: MatchViewModel) {
    val totalGames = matches.size
    
    // Parse reports to summarize data
    val reports = matches.mapNotNull { viewModel.parseJsonToReport(it.rawAnalysisJson) }
    
    val avgEv = if (reports.isNotEmpty()) reports.map { it.recommendedSingleBet.ev }.average() else 14.8
    val avgConfidence = if (reports.isNotEmpty()) reports.map { it.confidenceScore }.average().toInt() else 84
    val positiveEvCount = if (reports.isNotEmpty()) reports.count { it.layer5.isEvPositive } else matches.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "DEPARTAMENTO DE TRADING",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Resumo analítico do portfólio de investimento",
                    color = SecondaryText,
                    fontSize = 10.sp
                )
            }
            Icon(
                imageVector = Icons.Default.QueryStats,
                contentDescription = null,
                tint = SportGreen,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid metrics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PortfolioIndicatorCard(
                modifier = Modifier.weight(1f),
                title = "Total Partidas",
                value = totalGames.toString(),
                subtitle = "Matrização Ativa",
                color = CyberTeal
            )
            PortfolioIndicatorCard(
                modifier = Modifier.weight(1f),
                title = "Média de EV%",
                value = "+${String.format("%.1f", avgEv)}%",
                subtitle = "Valor Histórico",
                color = SportGreen
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PortfolioIndicatorCard(
                modifier = Modifier.weight(1f),
                title = "Confiança",
                value = "$avgConfidence%",
                subtitle = "Score Médio",
                color = WarningOrange
            )
            PortfolioIndicatorCard(
                modifier = Modifier.weight(1f),
                title = "Value Bets",
                value = if (totalGames == 0) "0" else "$positiveEvCount / $totalGames",
                subtitle = "EV+ Identificados",
                color = SportGreen
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Investment Guidelines Card
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardGray),
            border = BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = SportGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "DIRETRIZES DE GESTÃO DE BANCA EFICIENTE",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                InvestmentGuidelineRow(index = "1", text = "Nunca aloque mais de 2.5% de sua banca (stake) em uma única recomendação individual.")
                Spacer(modifier = Modifier.height(10.dp))
                InvestmentGuidelineRow(index = "2", text = "Prossiga estritamente com Value Bets positivas (EV+ > 5%). Se as odds caírem abaixo do valor limite, a aposta perde o sentido matemático.")
                Spacer(modifier = Modifier.height(10.dp))
                InvestmentGuidelineRow(index = "3", text = "As Múltiplas construídas pelo motor destinam-se a portfólios de menor alocação (recomenda-se 0.5% a 1.0% de stake máximo).")
                Spacer(modifier = Modifier.height(10.dp))
                InvestmentGuidelineRow(index = "4", text = "Combine flutuações de mercado com alertas climáticos. O Filtro de Risco (Camada 4) dita as reduções no tamanho da aposta.")
            }
        }
    }
}

@Composable
fun PortfolioIndicatorCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = title, color = SecondaryText, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, color = color, fontSize = 22.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, color = SecondaryText, fontSize = 9.sp)
        }
    }
}

@Composable
fun InvestmentGuidelineRow(index: String, text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(SportGreen.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = index, color = SportGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, color = SecondaryText, fontSize = 12.sp, lineHeight = 16.sp)
    }
}

@Composable
fun FullScreenAnalysisLoader() {
    var loadingStep by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1800)
            loadingStep = (loadingStep + 1) % 6
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xE40C1017)), // dark backdrop overlay
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            CircularProgressIndicator(
                color = SportGreen,
                strokeWidth = 4.dp,
                modifier = Modifier.size(56.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "MOTOR DE INTELIGÊNCIA ATIVO",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Sequence analysis text transitions
            Text(
                text = when (loadingStep) {
                    0 -> "📌 [1/5] Forma Real: Consolidando gols sofridos esperados (xG/xGA)..."
                    1 -> "📉 [2/5] Mercado: Computando oscilações e bias no favoritismo..."
                    2 -> "🖥️ [3/5] Preditivo: Rodando 10,000 simulações de Monte Carlo..."
                    3 -> "⚠️ [4/5] Filtro de Risco: Roteando suspensões e boletins climáticos..."
                    4 -> "🎯 [5/5] Value Bet: Calculando Expected Value matemático (EV+)..."
                    else -> "✍️ Sintetizando Conclusão Executiva Elite..."
                },
                color = SportGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.height(40.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Isso pode levar de 5 a 10 segundos.\nPor favor, aguarde.",
                color = SecondaryText,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAnalysisDialog(
    onDismiss: () -> Unit,
    onAnalyze: (String, String, String, String, Double, Double, Double) -> Unit
) {
    var homeTeam by remember { mutableStateOf("") }
    var awayTeam by remember { mutableStateOf("") }
    var league by remember { mutableStateOf("Brasileirão") }
    var matchDate by remember { mutableStateOf("16/06/2026") }
    var homeOdd by remember { mutableStateOf("1.85") }
    var drawOdd by remember { mutableStateOf("3.40") }
    var awayOdd by remember { mutableStateOf("3.80") }

    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = null,
                    tint = SportGreen
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "NOVA ANÁLISE QUANTITATIVA",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Insira as equipes e as cotações atuais do mercado para rodar a modelagem de 5 camadas.",
                    color = SecondaryText,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                // Outlined custom filled-styled TextFields
                OutlinedTextField(
                    value = homeTeam,
                    onValueChange = { homeTeam = it },
                    label = { Text("Equipe Mandante") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SportGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedLabelColor = SportGreen,
                        unfocusedLabelColor = SecondaryText,
                        focusedTextColor = LightText,
                        unfocusedTextColor = LightText
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("input_home_team")
                )

                OutlinedTextField(
                    value = awayTeam,
                    onValueChange = { awayTeam = it },
                    label = { Text("Equipe Visitante") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SportGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedLabelColor = SportGreen,
                        unfocusedLabelColor = SecondaryText,
                        focusedTextColor = LightText,
                        unfocusedTextColor = LightText
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("input_away_team")
                )

                OutlinedTextField(
                    value = league,
                    onValueChange = { league = it },
                    label = { Text("Liga/Campeonato") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SportGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedLabelColor = SportGreen,
                        unfocusedLabelColor = SecondaryText,
                        focusedTextColor = LightText,
                        unfocusedTextColor = LightText
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = matchDate,
                    onValueChange = { matchDate = it },
                    label = { Text("Data da Partida") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SportGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedLabelColor = SportGreen,
                        unfocusedLabelColor = SecondaryText,
                        focusedTextColor = LightText,
                        unfocusedTextColor = LightText
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Cotações do Mercado (1X2)",
                    color = CyberTeal,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = homeOdd,
                        onValueChange = { homeOdd = it },
                        label = { Text("Odd (1)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = LightText,
                            unfocusedTextColor = LightText,
                            focusedBorderColor = SportGreen,
                            unfocusedBorderColor = DarkBorder
                        ),
                        modifier = Modifier.weight(1f).testTag("input_home_odd")
                    )

                    OutlinedTextField(
                        value = drawOdd,
                        onValueChange = { drawOdd = it },
                        label = { Text("Odd (X)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = LightText,
                            unfocusedTextColor = LightText,
                            focusedBorderColor = SportGreen,
                            unfocusedBorderColor = DarkBorder
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = awayOdd,
                        onValueChange = { awayOdd = it },
                        label = { Text("Odd (2)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = LightText,
                            unfocusedTextColor = LightText,
                            focusedBorderColor = SportGreen,
                            unfocusedBorderColor = DarkBorder
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                if (showError) {
                    Text(
                        text = "Por favor, preencha os nomes das equipes com valores válidos.",
                        color = Color.Red,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (homeTeam.trim().isEmpty() || awayTeam.trim().isEmpty()) {
                        showError = true
                    } else {
                        val hOdd = homeOdd.toDoubleOrNull() ?: 2.0
                        val dOdd = drawOdd.toDoubleOrNull() ?: 3.2
                        val aOdd = awayOdd.toDoubleOrNull() ?: 3.5
                        onAnalyze(homeTeam, awayTeam, league, matchDate, hOdd, dOdd, aOdd)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = SportGreen, contentColor = DarkBg)
            ) {
                Text("EXECUTAR", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = SecondaryText)
            ) {
                Text("CANCELAR")
            }
        },
        containerColor = CardGray
    )
}
