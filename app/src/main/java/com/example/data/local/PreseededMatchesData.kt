package com.example.data.local

import com.example.data.local.AnalyzedMatch

object PreseededMatchesData {
    val sampleMatches = listOf(
        AnalyzedMatch(
            id = 1,
            homeTeam = "Manchester City",
            awayTeam = "Arsenal",
            league = "Premier League",
            matchDate = "21/06/2026",
            homeOdd = 1.95,
            drawOdd = 3.60,
            awayOdd = 3.80,
            isSample = true,
            rawAnalysisJson = """
            {
              "confidenceScore": 92,
              "recommendedSingleBet": {
                "market": "Dupla Chance: Manchester City ou Empate / Over 1.5 Gols",
                "odd": 1.55,
                "probability": 84.0,
                "ev": 13.0,
                "confidence": "Elite",
                "justification": "City mantém invencibilidade de 24 jogos no Etihad Stadium com média de 2.2 gols por partida, enquanto o Arsenal demonstra queda física em viagens sucessivas."
              },
              "layer1": {
                "homeRecentRecentG": ["V - Aston Villa (3-1)", "V - Real Madrid (2-1)", "E - Chelsea (2-2)", "V - West Ham (4-0)", "V - Bournemouth (3-0)"],
                "awayRecentRecentG": ["E - Arsenal (1-1)", "V - Tottenham (1-0)", "D - Bayern (0-1)", "E - Everton (0-0)", "V - Wolves (2-0)"],
                "homeXG": 2.25,
                "awayXG": 1.65,
                "homeXGA": 0.95,
                "awayXGA": 1.15,
                "trend": "Tendência de forte volume ofensivo do mandante inicial e bloco defensivo médio-baixo do Arsenal nos primeiros 30 minutos.",
                "completions": "16/7 - City, 11/4 - Arsenal",
                "cornersAndCards": "Cantos: ~10.5 / Cartões: Média de 3.2 cartões no jogo"
              },
              "layer2": {
                "classification": "🟢 Valor Positivo",
                "odDecline": "Odd do City caiu de 2.05 para 1.95 nas últimas 48 horas devido a confirmação do retorno de Kevin de Bruyne.",
                "inflatedOdds": "Odd do empate (3.60) está levemente inflada considerando o histórico equilibrado entre Guardiola e Arteta.",
                "biasFavoritism": "O mercado sobrestima a capacidade defensiva recente do Arsenal fora de casa sob pressão total."
              },
              "layer3": {
                "probWinHome": 51.0,
                "probDraw": 28.0,
                "probWinAway": 21.0,
                "probOver05": 97.0,
                "probOver15": 85.0,
                "probOver25": 61.0,
                "probOver35": 38.0,
                "probBTTS": 58.0,
                "estCorners": "11 escanteios estimados (6 City, 5 Arsenal)",
                "estCards": "4 cartões estimados (2 para cada time)"
              },
              "layer4": {
                "riskIndex": 35,
                "injuries": "City: Rodri (Dúvida leve). Arsenal: Odegaard (Fora por estiramento muscular).",
                "rosterRotation": "City deve usar força máxima; Arsenal vem de sequência cansativa de Champions no meio da semana.",
                "environmentalFactors": "Clima nublado com relva rápida (17°C). Sem vento relevante. Pressão total do Etihad.",
                "pressureAndMotivation": "Briga direta pela liderança da liga. City joga para retomar o topo antes da pausa."
              },
              "layer5": {
                "realProbability": 84.0,
                "impliedProbability": 64.5,
                "expectedValue": 19.5,
                "isEvPositive": true
              },
              "conclusaoExecutiva": "Manchester City apresenta uma estabilidade tática superior no Etihad Stadium acoplada ao desfalque do principal armador do Arsenal, Martin Odegaard. A análise quantitativa acusa uma distorção considerável nas odds de gols (Over 1.5) e dupla hipótese do mandante. A entrada sugerida City ou Empate + Over 1.5 fornece alto valor esperado (+19.5% EV) e segurança estatística excepcional de longo prazo para investidores esportivos."
            }
            """.trimIndent()
        ),
        AnalyzedMatch(
            id = 2,
            homeTeam = "Real Madrid",
            awayTeam = "Barcelona",
            league = "La Liga",
            matchDate = "22/06/2026",
            homeOdd = 1.85,
            drawOdd = 3.90,
            awayOdd = 3.75,
            isSample = true,
            rawAnalysisJson = """
            {
              "confidenceScore": 88,
              "recommendedSingleBet": {
                "market": "Ambas Marcam (BTTS) - Sim",
                "odd": 1.62,
                "probability": 78.0,
                "ev": 12.3,
                "confidence": "Excelente",
                "justification": "Com os ataques em pico de eficiência (xG conjunto > 3.8) e histórico de clássicos dominados por transições rápidas, a probabilidade de ambos balançarem as redes é altíssima."
              },
              "layer1": {
                "homeRecentRecentG": ["V - Mallorca (2-0)", "E - Man City (1-1)", "V - Bilbao (3-0)", "V - Cadiz (1-0)", "V - Leipzig (2-1)"],
                "awayRecentRecentG": ["V - PSG (3-2)", "V - Valencia (4-2)", "V - Getafe (2-0)", "E - Athletic (1-1)", "V - Las Palmas (3-1)"],
                "homeXG": 2.10,
                "awayXG": 1.95,
                "homeXGA": 1.10,
                "awayXGA": 1.30,
                "trend": "Pico de aceleração ofensiva por parte das duas equipes. Real Madrid extremamente vertical com Vinicius Jr. e Mbappe.",
                "completions": "15/6 - Real, 14/5 - Barca",
                "cornersAndCards": "Cantos: ~9.8 / Cartões: Clássico tenso, média superior a 5.0 cartões"
              },
              "layer2": {
                "classification": "🟢 Valor Positivo",
                "odDecline": "Odd do Real Madrid subiu ligeiramente de 1.80 para 1.85 devido a ajustes no mercado asiático.",
                "inflatedOdds": "Odd de Ambas Marcam (1.62) está subprecificada pelo viés defensivo que o mercado costuma dar a jogos de ida.",
                "biasFavoritism": "O favoritismo puro do Real Madrid é legítimo, mas a odd de 1.85 desvaloriza as escapadas em velocidade do Barcelona."
              },
              "layer3": {
                "probWinHome": 54.0,
                "probDraw": 23.0,
                "probWinAway": 23.0,
                "probOver05": 99.0,
                "probOver15": 88.0,
                "probOver25": 68.0,
                "probOver35": 42.0,
                "probBTTS": 78.0,
                "estCorners": "9.5 escanteios estimados",
                "estCards": "5.5 cartões estimados no total"
              },
              "layer4": {
                "riskIndex": 42,
                "injuries": "Real Madrid: Courtois (Dúvida no gol). Barcelona: Gavi (Transição física).",
                "rosterRotation": "Nenhuma das equipes deve rodar peças cruciais. El Clásico dita os rumos da liga.",
                "environmentalFactors": "Sem previsão de chuva no Bernabéu. Teto retrátil pode estar fechado (22°C estável).",
                "pressureAndMotivation": "Pressão máxima. Quem vencer assume a liderança moral e matemática de La Liga."
              },
              "layer5": {
                "realProbability": 78.0,
                "impliedProbability": 61.7,
                "expectedValue": 16.3,
                "isEvPositive": true
              },
              "conclusaoExecutiva": "O clássico espanhol encontra ambas as equipes no auge de sua performance ofensiva em termos de gols esperados (xG). A defesa do Barcelona, embora resiliente nas estatísticas domésticas, sofre em transições rápidas - especialidade do Real Madrid. Da mesma forma, Mbappe e Vinicius devem agredir a linha alta catalã, mas Lewandowski está em momento excelente. Projetamos um clássico aberto e propício ao Ambas Marcam, com um valor esperado positivo (EV) e segurança média para apostadores institucionais."
            }
            """.trimIndent()
        ),
        AnalyzedMatch(
            id = 3,
            homeTeam = "Palmeiras",
            awayTeam = "Flamengo",
            league = "Brasileirão",
            matchDate = "23/06/2026",
            homeOdd = 2.40,
            drawOdd = 3.20,
            awayOdd = 3.00,
            isSample = true,
            rawAnalysisJson = """
            {
              "confidenceScore": 82,
              "recommendedSingleBet": {
                "market": "Under 2.5 Gols",
                "odd": 1.75,
                "probability": 65.0,
                "ev": 13.7,
                "confidence": "Excelente",
                "justification": "Duelo tático de extrema rigidez defensiva entre Abel Ferreira e o técnico adversário. Historicamente, confrontos diretos eliminatórios ou de topo de tabela no Allianz são pautados por forte marcação."
              },
              "layer1": {
                "homeRecentRecentG": ["V - Coritiba (1-0)", "E - São Paulo (0-0)", "V - San Lorenzo (2-0)", "D - Botafogo (0-1)", "V - Bahia (2-1)"],
                "awayRecentRecentG": ["V - Botafogo (2-1)", "E - Fluminense (1-1)", "V - Peñarol (1-0)", "V - Grêmio (3-0)", "D - Cruzeiro (1-2)"],
                "homeXG": 1.35,
                "awayXG": 1.45,
                "homeXGA": 0.85,
                "awayXGA": 1.05,
                "trend": "Fase de forte consolidação defensiva do Palmeiras jogando com linha de 3 zagueiros sob comando de Abel nos jogos grandes.",
                "completions": "10/3 - Palmeiras, 11/4 - Flamengo",
                "cornersAndCards": "Cantos: ~11.2 / Cartões: Alta probabilidade de cartões amarelados (méd. 6.2)"
              },
              "layer2": {
                "classification": "🟢 Valor Positivo",
                "odDecline": "Odd do Palmeiras subiu de 2.25 para 2.40 refletindo o favoritismo diluído e respeito ao elenco rubro-negro.",
                "inflatedOdds": "A odd do Under 2.5 (1.75) oferece um valor excelente, pois a projeção inicial calculava a probabilidade real em torno de 1.62.",
                "biasFavoritism": "O mercado assume que o poder de fogo de ambos os ataques exigirá um festival de gols, ignorando o retrospecto ultra-defensivo nos últimos 4 duelos diretos."
              },
              "layer3": {
                "probWinHome": 40.0,
                "probDraw": 31.0,
                "probWinAway": 29.0,
                "probOver05": 90.0,
                "probOver15": 70.0,
                "probOver25": 41.0,
                "probOver35": 20.0,
                "probBTTS": 48.0,
                "estCorners": "11 escanteios",
                "estCards": "6 cartões amarelos estimados"
              },
              "layer4": {
                "riskIndex": 50,
                "injuries": "Palmeiras: Gomez (Suspenso). Flamengo: Arrascaeta (Retornando de lesão grave, inicia no banco).",
                "rosterRotation": "Palmeiras com força máxima disponível, exceto zaga. Flamengo deve rodar os laterais devido ao desgaste de viagem.",
                "environmentalFactors": "Allianz Parque (Grama sintética rápida). Sem chuva, temperatura amena (20°C). Estádio lotado.",
                "pressureAndMotivation": "Encontro clássico que pode desenhar o campeão do primeiro turno. Rivalidade histórica acirrada."
              },
              "layer5": {
                "realProbability": 65.0,
                "impliedProbability": 57.1,
                "expectedValue": 13.8,
                "isEvPositive": true
              },
              "conclusaoExecutiva": "Palmeiras e Flamengo possuem dois dos plantéis mais qualificados das Américas, mas os confrontos diretos mostram um controle pragmático agressivo. Abel Ferreira deve congestionar o meio de campo neutralizando a transição do Flamengo. Do outro lado, o Flamengo tem bloco defensivo sob liderança experiente. O valor puro está no mercado de contenção de gols (Under 2.5), onde as casas pagam 1.75 para uma probabilidade real avaliada por nós em 65% (odd de valor real 1.54)."
            }
            """.trimIndent()
        ),
        AnalyzedMatch(
            id = 4,
            homeTeam = "Inter Milan",
            awayTeam = "AC Milan",
            league = "Serie A",
            matchDate = "24/06/2026",
            homeOdd = 2.00,
            drawOdd = 3.40,
            awayOdd = 3.60,
            isSample = true,
            rawAnalysisJson = """
            {
              "confidenceScore": 79,
              "recommendedSingleBet": {
                "market": "Empate ou Vitória do Inter Milão + Over 1.5 Gols",
                "odd": 1.60,
                "probability": 74.0,
                "ev": 18.4,
                "confidence": "Boa",
                "justification": "O Inter venceu os últimos 5 Derby della Madonnina em todas as competições e demonstra uma superioridade coletiva consolidada sobre o rival Milan."
              },
              "layer1": {
                "homeRecentRecentG": ["V - Lazio (2-0)", "V - Napoli (3-1)", "E - Juventus (1-1)", "V - Genoa (2-0)", "V - Fiorentina (1-0)"],
                "awayRecentRecentG": ["E - Roma (2-2)", "D - Atalanta (1-2)", "V - Lecce (3-1)", "V - Torino (2-1)", "E - Bologna (1-1)"],
                "homeXG": 1.90,
                "awayXG": 1.50,
                "homeXGA": 0.80,
                "awayXGA": 1.40,
                "trend": "Inter mantém altíssimo rigor defensivo e letalidade no ataque rápido operado pela dupla Lautaro-Thuram.",
                "completions": "13/5 - Inter, 12/4 - Milan",
                "cornersAndCards": "Cantos: ~10.0 / Cartões: Derby de forte contato, cartões frequentes"
              },
              "layer2": {
                "classification": "🟢 Valor Positivo",
                "odDecline": "Odd do Inter Milão diminuiu sutilmente devido ao volume massivo de apostas domésticas a favor dos nerazzurri.",
                "inflatedOdds": "A odd para vitória direta do Inter (2.00) está ligeiramente subprecificada, estimamos o valor correto em 1.85.",
                "biasFavoritism": "O Milan é tradicional, mas a transição tática do time atual gera espaços severos no corredor central que o Inter explora perfeitamente."
              },
              "layer3": {
                "probWinHome": 54.0,
                "probDraw": 26.0,
                "probWinAway": 20.0,
                "probOver05": 96.0,
                "probOver15": 81.0,
                "probOver25": 54.0,
                "probOver35": 30.0,
                "probBTTS": 55.0,
                "estCorners": "10escanteios médios",
                "estCards": "5 cartões médios"
              },
              "layer4": {
                "riskIndex": 46,
                "injuries": "Inter: Acerbi (Dúvida leve). Milan: Maignan (Fora por problemas nas costas).",
                "rosterRotation": "Nenhum técnico vai abdicar de titulares. Duelo chave de San Siro.",
                "environmentalFactors": "Clima fresco em Milão, relva em excelente estado (15°C).",
                "pressureAndMotivation": "Derby de altíssima tensão emocional. Briga direta pelo topo da tabela e hegemonia da cidade."
              },
              "layer5": {
                "realProbability": 74.0,
                "impliedProbability": 62.5,
                "expectedValue": 18.4,
                "isEvPositive": true
              },
              "conclusaoExecutiva": "O Derby della Madonnina de amanhã apresenta claro favoritismo coletivo e tático para a Inter de Inzaghi. A ausência de Mike Maignan no gol do Milan desestabiliza consideravelmente a segurança defensiva do time, que já exibe tendências preocupantes de conceder contra-ataques rápidos. Lautaro Martinez e Thuram devem aproveitar estes espaços. A entrada de segurança com dupla chance da Inter + Over 1.5 gols é amparada por 74% de probabilidade real calculada em nosso motor, carregando um EV de +18.4%."
            }
            """.trimIndent()
        )
    )
}
