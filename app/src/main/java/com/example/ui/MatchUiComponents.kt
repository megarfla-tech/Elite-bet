package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AnalyzedMatch
import com.example.data.model.AnalysisReport
import com.example.ui.theme.*
import com.example.ui.viewmodel.AccumulatorRecommendation

@Composable
fun MatchListItem(
    match: AnalyzedMatch,
    report: AnalysisReport?,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("match_item_${match.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = match.league.uppercase(),
                    color = CyberTeal,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = match.matchDate,
                    color = SecondaryText,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1.3f)) {
                    Text(
                        text = match.homeTeam,
                        color = LightText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = match.awayTeam,
                        color = LightText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (report != null) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.weight(0.7f)
                    ) {
                        Surface(
                            color = when {
                                report.confidenceScore >= 90 -> SportGreen.copy(alpha = 0.15f)
                                report.confidenceScore >= 80 -> CyberTeal.copy(alpha = 0.15f)
                                else -> WarningOrange.copy(alpha = 0.15f)
                            },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(
                                1.dp,
                                when {
                                    report.confidenceScore >= 90 -> SportGreen
                                    report.confidenceScore >= 80 -> CyberTeal
                                    else -> WarningOrange
                                }
                            )
                        ) {
                            Text(
                                text = "${report.confidenceScore}% EV+",
                                color = when {
                                    report.confidenceScore >= 90 -> SportGreen
                                    report.confidenceScore >= 80 -> CyberTeal
                                    else -> WarningOrange
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = report.recommendedSingleBet.confidence,
                            color = LightText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Analytics,
                        contentDescription = null,
                        tint = SecondaryText,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Traditional Full-Time Odds Board inside list item
            Surface(
                color = DarkBg,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OddsBadge(label = "1", odd = match.homeOdd)
                        OddsBadge(label = "X", odd = match.drawOdd)
                        OddsBadge(label = "2", odd = match.awayOdd)
                    }

                    if (match.isSample) {
                        Text(
                            text = "PRE-SET",
                            color = CyberTeal.copy(alpha = 0.8f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    } else {
                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Deletar partida",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OddsBadge(label: String, odd: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = label, color = SecondaryText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(
            text = String.format("%.2f", odd),
            color = LightText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun RecommendedBetCard(bet: com.example.data.model.RecommendedSingleBet, score: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, SportGreen.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DoubleArrow,
                        contentDescription = null,
                        tint = SportGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ENTRADA RECOMENDADA",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    color = SportGreen.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, SportGreen)
                ) {
                    Text(
                        text = "CONFIANÇA: ${bet.confidence.uppercase()}",
                        color = SportGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = bet.market,
                color = SportGreen,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricWidget(title = "Odd Alvo", value = String.format("%.2f", bet.odd), iconTint = CyberTeal)
                MetricWidget(title = "Probabilidade", value = "${bet.probability}%", iconTint = SportGreen)
                MetricWidget(
                    title = "Valor Esperado (EV)", 
                    value = "+${bet.ev}%", 
                    iconTint = SportGreen,
                    valueColor = SportGreen
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = bet.justification,
                color = SecondaryText,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun MetricWidget(
    title: String, 
    value: String, 
    iconTint: Color,
    valueColor: Color = LightText
) {
    Column {
        Text(text = title, color = SecondaryText, fontSize = 11.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, color = valueColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

// CAMADA 1: Forma Recente Card
@Composable
fun Layer1Card(data: com.example.data.model.Layer1Data) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardTitle(step = "C1", title = "FORMA REAL DAS EQUIPES", icon = Icons.Default.SportsSoccer)

            Spacer(modifier = Modifier.height(12.dp))

            // Recent form results
            FormTeamSection(teamName = "Mandante", results = data.homeRecentRecentG)
            Spacer(modifier = Modifier.height(8.dp))
            FormTeamSection(teamName = "Visitante", results = data.awayRecentRecentG)

            Spacer(modifier = Modifier.height(16.dp))

            // Gols Esperados (xG) metrics comparative bars
            Text(
                text = "Gols Esperados (xG) vs xGA (Sofridos Esperados)",
                color = LightText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            XGBarComparisons(
                homeVal = data.homeXG,
                awayVal = data.awayXG,
                label = "Média de Gols Esperados (xG)"
            )
            Spacer(modifier = Modifier.height(8.dp))
            XGBarComparisons(
                homeVal = data.homeXGA,
                awayVal = data.awayXGA,
                label = "Média de xGA (Gols Sofridos Esperados)",
                homeBarColor = WarningOrange
            )

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = CyberTeal,
                    modifier = Modifier.size(16.dp).offset(y = 2.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(text = "Tendência Coletiva", color = CyberTeal, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = data.trend, color = SecondaryText, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Aproveitamentos: ${data.completions}",
                    color = SecondaryText,
                    fontSize = 11.sp
                )
                Text(
                    text = data.cornersAndCards,
                    color = SecondaryText,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun FormTeamSection(teamName: String, results: List<String>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = teamName,
            color = LightText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(70.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            for (res in results.take(5)) {
                val letter = res.firstOrNull()?.toString() ?: "E"
                val color = when (letter) {
                    "V" -> SportGreen
                    "E" -> Color(0xFF94A3B8)
                    else -> Color(0xFFEF4444)
                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color.copy(alpha = 0.2f))
                        .border(1.dp, color, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = letter,
                        color = color,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun XGBarComparisons(
    homeVal: Double,
    awayVal: Double,
    label: String,
    homeBarColor: Color = CyberTeal,
    awayBarColor: Color = Color(0xFF64748B)
) {
    val total = homeVal + awayVal
    val homeWeight = if (total > 0) (homeVal / total).toFloat() else 0.5f

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, color = SecondaryText, fontSize = 10.sp)
            Row {
                Text(text = "C: $homeVal", color = homeBarColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(text = " | ", color = SecondaryText, fontSize = 11.sp)
                Text(text = "F: $awayVal", color = awayBarColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color(0xFF0F172A))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(homeWeight.coerceIn(0.05f, 0.95f))
                    .background(homeBarColor)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight((1f - homeWeight).coerceIn(0.05f, 0.95f))
                    .background(awayBarColor)
            )
        }
    }
}

// CAMADA 2: Leitura de Mercado Card
@Composable
fun Layer2Card(data: com.example.data.model.Layer2Data) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardTitle(step = "C2", title = "LEITURA DE MERCADO (ODDS)", icon = Icons.Default.Troubleshoot)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Classificação de Valor", color = SecondaryText, fontSize = 13.sp)
                
                val (badgeColor, bgColor) = when {
                    data.classification.contains("Positivo") -> Pair(SportGreen, SportGreen.copy(alpha = 0.15f))
                    data.classification.contains("Neutro") -> Pair(Color.Yellow, Color.Yellow.copy(alpha = 0.15f))
                    else -> Pair(Color.Red, Color.Red.copy(alpha = 0.15f))
                }

                Surface(
                    color = bgColor,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, badgeColor)
                ) {
                    Text(
                        text = data.classification.uppercase(),
                        color = badgeColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            MarketFactorRow(subtitle = "Queda de Odds", content = data.odDecline, icon = Icons.Outlined.TrendingDown)
            Spacer(modifier = Modifier.height(10.dp))
            MarketFactorRow(subtitle = "Odds Infladas", content = data.inflatedOdds, icon = Icons.Outlined.TrendingUp)
            Spacer(modifier = Modifier.height(10.dp))
            MarketFactorRow(subtitle = "Excessos de Favoritismo", content = data.biasFavoritism, icon = Icons.Outlined.Shield)
        }
    }
}

@Composable
fun MarketFactorRow(subtitle: String, content: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = CyberTeal,
            modifier = Modifier.size(16.dp).offset(y = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = subtitle, color = LightText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = content, color = SecondaryText, fontSize = 12.sp)
        }
    }
}

// CAMADA 3: Modelo Preditivo Card
@Composable
fun Layer3Card(data: com.example.data.model.Layer3Data) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardTitle(step = "C3", title = "MODELO PREDITIVO (10K SIMULAÇÕES)", icon = Icons.Default.QueryStats)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Desfecho Regulamentar (1X2)",
                color = LightText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))

            // Multibar representing 1X2 distribution
            Distribution1X2Bar(home = data.probWinHome, draw = data.probDraw, away = data.probWinAway)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Probabilidades Disponíveis (Mercados Alternativos)",
                color = LightText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    ProbMetricBar(label = "Over 0.5 Gols", value = data.probOver05)
                    Spacer(modifier = Modifier.height(8.dp))
                    ProbMetricBar(label = "Over 1.5 Gols", value = data.probOver15)
                    Spacer(modifier = Modifier.height(8.dp))
                    ProbMetricBar(label = "Over 2.5 Gols", value = data.probOver25)
                }
                Column(modifier = Modifier.weight(1f)) {
                    ProbMetricBar(label = "Over 3.5 Gols", value = data.probOver35)
                    Spacer(modifier = Modifier.height(8.dp))
                    ProbMetricBar(label = "Ambas Marcam (BTTS)", value = data.probBTTS)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Média Escanteios: ${data.estCorners}", color = SecondaryText, fontSize = 11.sp)
                Text(text = "Média Cartões: ${data.estCards}", color = SecondaryText, fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun Distribution1X2Bar(home: Double, draw: Double, away: Double) {
    val total = (home + draw + away).toFloat()
    val homeWeight = if (total > 0) (home / total).toFloat() else 0.33f
    val drawWeight = if (total > 0) (draw / total).toFloat() else 0.33f
    val awayWeight = if (total > 0) (away / total).toFloat() else 0.33f

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFF0F172A))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(homeWeight)
                    .background(SportGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${home.toInt()}%", color = Color(0xFF06150D), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(drawWeight)
                    .background(Color(0xFF475569)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${draw.toInt()}%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(awayWeight)
                    .background(CyberTeal),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${away.toInt()}%", color = Color(0xFF002227), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Mandante (1)", color = SportGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(text = "Empate (X)", color = Color(0xFF94A3B8), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(text = "Visitante (2)", color = CyberTeal, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProbMetricBar(label: String, value: Double) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, color = SecondaryText, fontSize = 10.sp)
            Text(text = "${value.toInt()}%", color = LightText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = Color(0xFF0F172A),
            shape = RoundedCornerShape(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth((value / 100.0).toFloat().coerceIn(0f, 1f))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(CyberTeal, SportGreen)
                        )
                    )
            )
        }
    }
}

// CAMADA 4: Filtro de Risco Card
@Composable
fun Layer4Card(data: com.example.data.model.Layer4Data) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardTitle(step = "C4", title = "FILTRO DE RISCO & NOTÍCIAS", icon = Icons.Default.CrisisAlert)

            Spacer(modifier = Modifier.height(14.dp))

            // Risk indicator circle state
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(
                            Brush.sweepGradient(
                                colors = listOf(SportGreen, WarningOrange, SportGreen)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(CardGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${data.riskIndex}%",
                            color = if (data.riskIndex > 50) WarningOrange else SportGreen,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Índice de Volatilidade de Risco",
                        color = LightText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = when {
                            data.riskIndex >= 60 -> "RISCO CRÍTICO: Evite alocações extremas de capital."
                            data.riskIndex >= 40 -> "RISCO MODERADO: Exige cautela e táticas conservadoras."
                            else -> "RISCO SEGURO: Cenário favorável para trading esportivo."
                        },
                        color = if (data.riskIndex >= 50) WarningOrange else SportGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            MarketFactorRow(subtitle = "Lesões e Suspensões", content = data.injuries, icon = Icons.Default.MedicalServices)
            Spacer(modifier = Modifier.height(10.dp))
            MarketFactorRow(subtitle = "Rotação e Calendário", content = data.rosterRotation, icon = Icons.Default.CalendarMonth)
            Spacer(modifier = Modifier.height(10.dp))
            MarketFactorRow(subtitle = "Ambiente e Clima", content = data.environmentalFactors, icon = Icons.Default.CloudQueue)
            Spacer(modifier = Modifier.height(10.dp))
            MarketFactorRow(subtitle = "Pressão Extra e Motivação", content = data.pressureAndMotivation, icon = Icons.Default.Psychology)
        }
    }
}

// CAMADA 5: Value Bet Equation Card
@Composable
fun Layer5Card(data: com.example.data.model.Layer5Data) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, SportGreen.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardTitle(step = "C5", title = "DETECÇÃO DE VALUE BET", icon = Icons.Default.Calculate)

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                color = DarkBg,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "PROBABILIDADE REAL (A)", color = SecondaryText, fontSize = 10.sp)
                            Text(text = "${data.realProbability}%", color = SportGreen, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = ">", 
                            color = SecondaryText, 
                            fontSize = 24.sp, 
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.offset(y = 10.dp)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "IMPLÍCITA DA ODD (B)", color = SecondaryText, fontSize = 10.sp)
                            Text(text = "${data.impliedProbability}%", color = CyberTeal, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "VALOR ESPERADO (EV): ", color = LightText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "+${data.expectedValue}%", 
                            color = SportGreen, 
                            fontSize = 18.sp, 
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = if (data.expectedValue > 0) Icons.Default.CheckCircle else Icons.Default.Cancel,
                    contentDescription = null,
                    tint = if (data.expectedValue > 0) SportGreen else Color.Red,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = if (data.expectedValue > 0) "VALOR ESPERADO POSITIVO IDENTIFICADO (+EV)" else "SEM SINAL DE VALOR ESPERADO",
                    color = if (data.expectedValue > 0) SportGreen else Color.Red,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun CardTitle(step: String, title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = CyberTeal.copy(alpha = 0.15f),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = step,
                color = CyberTeal,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = LightText,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            color = LightText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}

// Curated Dynamic Accumulator/Múltipla Card Widget
@Composable
fun AccumulatorCard(acc: AccumulatorRecommendation) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(
            1.dp,
            when (acc.type) {
                "Ultra Segura" -> SportGreen.copy(alpha = 0.6f)
                "Premium" -> CyberTeal.copy(alpha = 0.6f)
                else -> WarningOrange.copy(alpha = 0.6f)
            }
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = when (acc.type) {
                            "Ultra Segura" -> Icons.Default.VerifiedUser
                            "Premium" -> Icons.Default.Star
                            "Agressiva" -> Icons.Default.LocalFireDepartment
                            else -> Icons.Default.FlashOn
                        },
                        contentDescription = null,
                        tint = when (acc.type) {
                            "Ultra Segura" -> SportGreen
                            "Premium" -> CyberTeal
                            else -> WarningOrange
                        },
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = acc.title.uppercase(),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Aprovado por Múltiplas Inteligências",
                            color = SecondaryText,
                            fontSize = 10.sp
                        )
                    }
                }

                Surface(
                    color = when (acc.type) {
                        "Ultra Segura" -> SportGreen.copy(alpha = 0.15f)
                        "Premium" -> CyberTeal.copy(alpha = 0.15f)
                        else -> WarningOrange.copy(alpha = 0.15f)
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "ALVO: ${acc.targetOddRange}",
                        color = when (acc.type) {
                            "Ultra Segura" -> SportGreen
                            "Premium" -> CyberTeal
                            else -> WarningOrange
                        },
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Odd Acumulada", color = SecondaryText, fontSize = 11.sp)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = String.format("%.2f", acc.totalOdd),
                            color = when (acc.type) {
                                "Ultra Segura" -> SportGreen
                                "Premium" -> CyberTeal
                                else -> WarningOrange
                            },
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Probabilidade Combinada", color = SecondaryText, fontSize = 11.sp)
                    Text(
                        text = "${acc.estimatedProbability}%",
                        color = LightText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(12.dp))

            // Selections count and toggler
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = null,
                        tint = CyberTeal,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Ver ${acc.selections.size} seleções incluídas",
                        color = CyberTeal,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = CyberTeal,
                    modifier = Modifier.size(18.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (sel in acc.selections) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DarkBg, RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = sel.matchName, color = LightText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = sel.selection, color = SportGreen, fontSize = 11.sp)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "@${String.format("%.2f", sel.odd)}",
                                    color = CyberTeal,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${sel.prob.toInt()}%",
                                    color = SecondaryText,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(WarningOrange.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                            .border(1.dp, WarningOrange.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = WarningOrange,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Nível de Risco: ${acc.riskLevel}. Jogue com moderação.",
                                color = WarningOrange,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProbabilityDashboardRecharts(
    homeTeam: String,
    awayTeam: String,
    homeOdd: Double,
    drawOdd: Double,
    awayOdd: Double,
    probHome: Double,
    probDraw: Double,
    probAway: Double,
    modifier: Modifier = Modifier
) {
    val totalImplied = (1.0 / homeOdd) + (1.0 / drawOdd) + (1.0 / awayOdd)
    val impliedHome = if (totalImplied > 0) ((1.0 / homeOdd) / totalImplied) * 100.0 else 33.3
    val impliedDraw = if (totalImplied > 0) ((1.0 / drawOdd) / totalImplied) * 100.0 else 33.3
    val impliedAway = if (totalImplied > 0) ((1.0 / awayOdd) / totalImplied) * 100.0 else 33.3

    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var animateChart by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animateChart = true
    }

    val scaleHomeGemini by animateFloatAsState(
        targetValue = if (animateChart) (probHome / 100.0).toFloat().coerceIn(0.01f, 1f) else 0.01f,
        animationSpec = tween(durationMillis = 1000)
    )
    val scaleHomeMarket by animateFloatAsState(
        targetValue = if (animateChart) (impliedHome / 100.0).toFloat().coerceIn(0.01f, 1f) else 0.01f,
        animationSpec = tween(durationMillis = 1000)
    )

    val scaleDrawGemini by animateFloatAsState(
        targetValue = if (animateChart) (probDraw / 100.0).toFloat().coerceIn(0.01f, 1f) else 0.01f,
        animationSpec = tween(durationMillis = 1000)
    )
    val scaleDrawMarket by animateFloatAsState(
        targetValue = if (animateChart) (impliedDraw / 100.0).toFloat().coerceIn(0.01f, 1f) else 0.01f,
        animationSpec = tween(durationMillis = 1000)
    )

    val scaleAwayGemini by animateFloatAsState(
        targetValue = if (animateChart) (probAway / 100.0).toFloat().coerceIn(0.01f, 1f) else 0.01f,
        animationSpec = tween(durationMillis = 1000)
    )
    val scaleAwayMarket by animateFloatAsState(
        targetValue = if (animateChart) (impliedAway / 100.0).toFloat().coerceIn(0.01f, 1f) else 0.01f,
        animationSpec = tween(durationMillis = 1000)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("probability_dashboard_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardGray),
        border = BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.QueryStats,
                        contentDescription = null,
                        tint = SportGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "DASHBOARD DE PROBABILIDADES 1X2",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Análise quantitativa real-time via API Gemini",
                            color = SecondaryText,
                            fontSize = 10.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberTeal.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "RECHARTS ENGINE",
                        color = CyberTeal,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val heights = listOf(0f, 0.25f, 0.5f, 0.75f, 1.0f)
                    heights.forEach { fraction ->
                        val y = size.height * fraction
                        drawLine(
                            color = Color(0xFF334155).copy(alpha = 0.4f),
                            start = androidx.compose.ui.geometry.Offset(0f, y),
                            end = androidx.compose.ui.geometry.Offset(size.width, y),
                            strokeWidth = 1f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom
                ) {
                    InteractiveBarColumn(
                        label = "Mandante",
                        geminiPct = probHome,
                        marketPct = impliedHome,
                        scaleGemini = scaleHomeGemini,
                        scaleMarket = scaleHomeMarket,
                        colorGemini = SportGreen,
                        colorMarket = CyberTeal,
                        isSelected = selectedIndex == 0,
                        onClick = { selectedIndex = if (selectedIndex == 0) null else 0 }
                    )

                    InteractiveBarColumn(
                        label = "Empate",
                        geminiPct = probDraw,
                        marketPct = impliedDraw,
                        scaleGemini = scaleDrawGemini,
                        scaleMarket = scaleDrawMarket,
                        colorGemini = SecondaryText,
                        colorMarket = CyberTeal.copy(alpha = 0.7f),
                        isSelected = selectedIndex == 1,
                        onClick = { selectedIndex = if (selectedIndex == 1) null else 1 }
                    )

                    InteractiveBarColumn(
                        label = "Visitante",
                        geminiPct = probAway,
                        marketPct = impliedAway,
                        scaleGemini = scaleAwayGemini,
                        scaleMarket = scaleAwayMarket,
                        colorGemini = WarningOrange,
                        colorMarket = CyberTeal,
                        isSelected = selectedIndex == 2,
                        onClick = { selectedIndex = if (selectedIndex == 2) null else 2 }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(SportGreen))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Realidade AI", color = LightText, fontSize = 10.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(CyberTeal))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Mercado (Odds)", color = LightText, fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedContent(
                targetState = selectedIndex,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            ) { sel ->
                if (sel != null) {
                    val outcomeTitle = when (sel) {
                        0 -> "VITÓRIA MANDANTE (1) - $homeTeam"
                        1 -> "EMPATE (X)"
                        else -> "VITÓRIA VISITANTE (2) - $awayTeam"
                    }
                    val geminiPct = when (sel) {
                        0 -> probHome
                        1 -> probDraw
                        else -> probAway
                    }
                    val marketPct = when (sel) {
                        0 -> impliedHome
                        1 -> impliedDraw
                        else -> impliedAway
                    }
                    val odd = when (sel) {
                        0 -> homeOdd
                        1 -> drawOdd
                        else -> awayOdd
                    }
                    
                    val valueAdvantage = geminiPct - marketPct
                    val isEVPlus = valueAdvantage > 0

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0F172A), RoundedCornerShape(12.dp))
                            .border(1.dp, if (isEVPlus) SportGreen.copy(alpha = 0.4f) else DarkBorder, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = outcomeTitle,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Surface(
                                    color = if (isEVPlus) SportGreen.copy(alpha = 0.15f) else Color.Red.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = if (isEVPlus) "OFERTA EV+ DETECTADA" else "SEM VALOR ESPERADO",
                                        color = if (isEVPlus) SportGreen else Color.Red,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = "PROBABILIDADE REAL AI", color = SecondaryText, fontSize = 9.sp)
                                    Text(text = "${String.format("%.1f", geminiPct)}%", color = if (sel == 0) SportGreen else if (sel == 2) WarningOrange else LightText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                                Column {
                                    Text(text = "IMPLICADO MERCADO", color = SecondaryText, fontSize = 9.sp)
                                    Text(text = "${String.format("%.1f", marketPct)}%", color = CyberTeal, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "DISTORÇÃO REAL DO VALOR", color = SecondaryText, fontSize = 9.sp)
                                    Text(
                                        text = "${if (valueAdvantage >= 0) "+" else ""}${String.format("%.1f", valueAdvantage)}%",
                                        color = if (isEVPlus) SportGreen else Color.Red,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            val evCalculation = (geminiPct / 100.0) * odd - 1.0
                            val evPercentageStr = String.format("%.1f", evCalculation * 100.0)
                            Text(
                                text = "A modelagem quantitativa calcula uma distorção de ${if (valueAdvantage >= 0) "vantagem" else "desvantagem"} de ${String.format("%.1f", Math.abs(valueAdvantage))}% contra as casas de apostas. O Retorno Esperado Matemático (EV+) de valor esperado nesta seleção é de ${if (evCalculation >= 0) "+" else ""}$evPercentageStr%.",
                                color = LightText,
                                fontSize = 10.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0F172A), RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TouchApp,
                                contentDescription = null,
                                tint = SecondaryText,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "TOQUE NAS COLUNAS PARA EXIBIR O FOOTPRINT QUANTITATIVO (EV+).",
                                color = SecondaryText,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InteractiveBarColumn(
    label: String,
    geminiPct: Double,
    marketPct: Double,
    scaleGemini: Float,
    scaleMarket: Float,
    colorGemini: Color,
    colorMarket: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(84.dp)
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .height(115.dp)
                .fillMaxWidth()
                .background(if (isSelected) Color(0xFF1E293B).copy(alpha = 0.5f) else Color.Transparent, RoundedCornerShape(6.dp))
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(scaleGemini)
                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    .background(colorGemini)
                    .border(
                        width = if (isSelected) 1.5.dp else 0.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(scaleMarket)
                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    .background(colorMarket.copy(alpha = 0.4f))
                    .border(
                        width = 1.dp,
                        color = colorMarket,
                        shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (isSelected) Color.White else SecondaryText,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "${geminiPct.toInt()}% / ${marketPct.toInt()}%",
            color = if (isSelected) colorGemini else SecondaryText.copy(alpha = 0.7f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
