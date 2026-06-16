package com.example.data.local

import com.example.data.local.AnalyzedMatch

object PreseededMatchesData {
    val sampleMatches = listOf(
        AnalyzedMatch(
            id = 1,
            homeTeam = "Escócia",
            awayTeam = "Marrocos",
            league = "Copa do Mundo FIFA 2026 - Grupo C",
            matchDate = "17/06/2026",
            homeOdd = 2.70,
            drawOdd = 3.10,
            awayOdd = 2.80,
            isSample = true,
            rawAnalysisJson = """
            {
              "confidenceScore": 86,
              "recommendedSingleBet": {
                "market": "Dupla Chance: Empate ou Marrocos (X2)",
                "odd": 1.48,
                "probability": 76.0,
                "ev": 11.5,
                "confidence": "Excelente",
                "justification": "Marrocos mantém a espinha dorsal semifinalista do Catar (Hakimi, Amrabat) combinada com a lapidação de Brahim Díaz. A Escócia tem um plano tático pragmático, mas defensivamente oscila sob forte pressão de transição."
              },
              "layer1": {
                "homeRecentRecentG": ["E - Finlândia (2-2)", "V - Gibraltar (2-0)", "D - Holanda (0-4)", "D - Irlanda do Norte (0-1)", "D - Noruega (3-3)"],
                "awayRecentRecentG": ["V - Angola (1-0)", "E - Mauritânia (0-0)", "V - Tanzânia (2-0)", "D - África do Sul (0-2)", "E - RD Congo (1-1)"],
                "homeXG": 1.28,
                "awayXG": 1.74,
                "homeXGA": 1.45,
                "awayXGA": 0.92,
                "trend": "Marrocos impõe grande volume dinâmico com ultrapassagens de Hakimi. A Escócia joga em bloco mudo com saídas longas para McTominay penetrar.",
                "completions": "10/3 - Escócia, 14/5 - Marrocos",
                "cornersAndCards": "Cantos aproximados: 9.0 / Cartões estimados no jogo: 4"
              },
              "layer2": {
                "classification": "🟢 Valor Positivo",
                "odDecline": "Odd do Marrocos caiu de 2.95 para 2.80 após a divulgação do boletim médico que confirmou força máxima tática.",
                "inflatedOdds": "A cotação de Empate ou Marrocos a 1.48 carrega margem esticada pelas casas, que previam equilíbrio excessivo em MetLife.",
                "biasFavoritism": "O mercado europeu sobrestima a força física escocesa ignorando o repertório de drible marroquino nos flancos."
              },
              "layer3": {
                "probWinHome": 31.0,
                "probDraw": 29.0,
                "probWinAway": 40.0,
                "probOver05": 92.0,
                "probOver15": 74.0,
                "probOver25": 46.0,
                "probOver35": 22.0,
                "probBTTS": 51.0,
                "estCorners": "8.8 escanteios estimados (4.1 Escócia, 4.7 Marrocos)",
                "estCards": "3.8 cartões (2.0 Escócia, 1.8 Marrocos)"
              },
              "layer4": {
                "riskIndex": 38,
                "injuries": "Escócia: Aaron Hickey (Dúvida leve por resfriado). Marrocos: Sofyan Amrabat totalmente recuperado das dores no joelho.",
                "rosterRotation": "Rodada de estreia do Grupo C, ambas as seleções entram com o melhor contingente de atletas fardados.",
                "environmentalFactors": "Clima ideal em New Jersey (19°C), gramado natural de MetLife em estado espetacular de conservação.",
                "pressureAndMotivation": "Estreia em Copa do Mundo. Quem vencer aqui encaminha matematicamente a luta pela segunda vaga ao lado do favorito Brasil."
              },
              "layer5": {
                "realProbability": 76.0,
                "impliedProbability": 67.5,
                "expectedValue": 8.5,
                "isEvPositive": true
              },
              "conclusaoExecutiva": "Este duelo na arena MetLife coloca frente a frente dois sistemas contrastantes de estilo. A Escócia apostará no contato físico e cruzamento vertical, mas a zaga marroquina de Aguerd e Saïss é dominante no jogo aéreo. No meio de campo, a qualidade técnica de Marrocos ditará o ritmo da bola. Nosso modelo quantitativo aponta que a dupla chance a favor da seleção do norte da África (Empate ou Marrocos) está subprecificada nas casas de aposta, sendo uma excelente recomendação institucional com EV+ de 8.5%."
            }
            """.trimIndent()
        ),
        AnalyzedMatch(
            id = 2,
            homeTeam = "Brasil",
            awayTeam = "Haiti",
            league = "Copa do Mundo FIFA 2026 - Grupo C",
            matchDate = "17/06/2026",
            homeOdd = 1.05,
            drawOdd = 15.00,
            awayOdd = 26.00,
            isSample = true,
            rawAnalysisJson = """
            {
              "confidenceScore": 95,
              "recommendedSingleBet": {
                "market": "Brasil Handicap Asiático -2.5",
                "odd": 1.50,
                "probability": 73.0,
                "ev": 6.3,
                "confidence": "Elite",
                "justification": "O fosso técnico e físico entre as equipes em Miami é gigantesco. Brasil com todo o poder de fogo de Vinicius, Rodrygo e Endrick diante de uma das defesas menos consolidadas das Américas."
              },
              "layer1": {
                "homeRecentRecentG": ["V - Inglaterra (1-0)", "E - Espanha (3-3)", "V - México (3-2)", "D - EUA (1-1)", "V - Equador (2-0)"],
                "awayRecentRecentG": ["E - Guiana Francesa (1-1)", "V - Santa Lúcia (2-1)", "V - Barbados (3-1)", "D - Porto Rico (1-2)", "E - Jamaica (0-0)"],
                "homeXG": 2.95,
                "awayXG": 0.85,
                "homeXGA": 0.80,
                "awayXGA": 1.95,
                "trend": "Brasil domina a posse (78% de média projetada) com pressão alta imediata e infiltrações na área haitiana desde o primeiro apito.",
                "completions": "24/11 - Brasil, 5/1 - Haiti",
                "cornersAndCards": "Cantos: ~11.5 / Cartões estimados no jogo: 2.5 (pouco contato tático esperado)"
              },
              "layer2": {
                "classification": "🟢 Valor Positivo",
                "odDecline": "Odd da vitória direta do Brasil é irrisória (1.05), porém a linha de handicap esticou com forte volume institucional em -2.5.",
                "inflatedOdds": "As cotações de Over 3.5 gols (1.80) estão sob forte flutuação e apresentam leve distorção de valor.",
                "biasFavoritism": "O extremo favoritismo da seleção brasileira é amparado matematicamente pelas métricas absolutas de xG tático."
              },
              "layer3": {
                "probWinHome": 92.0,
                "probDraw": 6.0,
                "probWinAway": 2.0,
                "probOver05": 99.0,
                "probOver15": 94.0,
                "probOver25": 81.0,
                "probOver35": 62.0,
                "probBTTS": 38.0,
                "estCorners": "11.0 cantos projetados (9 Brasil, 2 Haiti)",
                "estCards": "2.8 cartões estimados (0.8 Brasil, 2.0 Haiti)"
              },
              "layer4": {
                "riskIndex": 20,
                "injuries": "Brasil: Gabriel Magalhães poupado preventivamente de início. Haiti: Completo para o jogo histórico do país.",
                "rosterRotation": "Brasil vai com força total para resolver a estreia de Copa do Mundo precoce e dar confiança à torcida.",
                "environmentalFactors": "Miami (Hard Rock Stadium) com alta umidade relativa do ar (82%), calor forte de 28°C no apito inicial.",
                "pressureAndMotivation": "Motivação brasileira máxima sob a reestreia do sonho da sexta estrela. Haiti joga com a motivação de fazer história."
              },
              "layer5": {
                "realProbability": 73.0,
                "impliedProbability": 66.7,
                "expectedValue": 6.3,
                "isEvPositive": true
              },
              "conclusaoExecutiva": "Este confronto representará um ataque de ondas constantes do Brasil. Em Miami, sob as arquibancadas repletas de brasileiros, a equipe canarinho buscará uma vitória contundente na estreia para estabelecer seu prestígio. O Haiti tentará formar um bloco defensivo hiper compactado de cinco defensores, mas a velocidade e o drible curto dos extremos de Dorival Júnior quebrarão as linhas rapidamente. O handicap asiático de -2.5 a favor do Brasil entrega valor matemático seguro para investimento de médio risco."
            }
            """.trimIndent()
        ),
        AnalyzedMatch(
            id = 3,
            homeTeam = "Argentina",
            awayTeam = "Suécia",
            league = "Copa do Mundo FIFA 2026 - Grupo H",
            matchDate = "18/06/2026",
            homeOdd = 1.45,
            drawOdd = 4.20,
            awayOdd = 7.50,
            isSample = true,
            rawAnalysisJson = """
            {
              "confidenceScore": 89,
              "recommendedSingleBet": {
                "market": "Vitória da Argentina",
                "odd": 1.45,
                "probability": 73.0,
                "ev": 4.0,
                "confidence": "Excelente",
                "justification": "A Argentina detém superioridade criativa indiscutível com a liderança de Lionel Messi, além de solidez com Mac Allister e Enzo Fernández. A Suécia é disciplinada, mas carece de velocidade para parar as infiltrações rápidas no meio."
              },
              "layer1": {
                "homeRecentRecentG": ["V - Equador (1-0)", "V - Guatemala (4-1)", "V - Canadá (2-0)", "V - Chile (1-0)", "V - Peru (2-0)"],
                "awayRecentRecentG": ["V - Albânia (1-0)", "D - Dinamarca (1-2)", "D - Portugal (2-5)", "E - Estônia (1-1)", "V - Moldávia (3-0)"],
                "homeXG": 2.10,
                "awayXG": 1.25,
                "homeXGA": 0.70,
                "awayXGA": 1.40,
                "trend": "Argentina opera em paciência absoluta, rodando passes no terço final até encontrar Messi ou os avanços rápidos de Lautaro/Álvarez.",
                "completions": "15/6 - Argentina, 9/3 - Suécia",
                "cornersAndCards": "Média de 9.5 escanteios / 4.5 cartões projetados no jogo"
              },
              "layer2": {
                "classification": "🟢 Valor Positivo",
                "odDecline": "A odd da Argentina caiu ligeiramente de 1.50 para 1.45 após confirmação do treino tático limpo da Albiceleste.",
                "inflatedOdds": "A odd do mercado de Resultado Final para a Argentina está ajustada de forma muito realista, nos limites da margem segura de EV.",
                "biasFavoritism": "Existe respeito ao pragmatismo sueco pelo histórico físico, gerando cotações atrativas para a vitória sul-americana direta."
              },
              "layer3": {
                "probWinHome": 69.0,
                "probDraw": 21.0,
                "probWinAway": 10.0,
                "probOver05": 94.0,
                "probOver15": 78.0,
                "probOver25": 49.0,
                "probOver35": 23.0,
                "probBTTS": 48.0,
                "estCorners": "9.2 cantos estimados totais (6.0 Argentina, 3.2 Suécia)",
                "estCards": "4.0 cartões estimados (1.5 Argentina, 2.5 Suécia)"
              },
              "layer4": {
                "riskIndex": 30,
                "injuries": "Argentina: Sem problemas médicos no elenco titular. Suécia: Isak confirmado de volta mas sem ritmo físico de 90 minutos.",
                "rosterRotation": "Nenhum desgaste nos elencos na estreia do Grupo H. Ambas entrarão com força máxima absoluta.",
                "environmentalFactors": "Estádio Mercedes-Benz em Atlanta, com temperatura de 22°C (ar condicionado ativo sob teto fechado). Gramado excelente.",
                "pressureAndMotivation": "Argentina inicia a defesa de seu título mundial de 2022 com a pressão da torcida local, que lotará o estádio."
              },
              "layer5": {
                "realProbability": 73.0,
                "impliedProbability": 69.0,
                "expectedValue": 4.0,
                "isEvPositive": true
              },
              "conclusaoExecutiva": "Sob a maestria de Scaloni e o brilho eterno de Messi, a Argentina é dominante nos indicadores de dinâmica de campo com 2.10 de xG ofensivo médio. A Suécia, sob os moldes táticos europeus clássicos de bloco rígido, tentará conter a progressão argentina pela lateral, mas cederá espaços para os chutes de média distância de Mac Allister. A odd direta de 1.45 para a vitória albiceleste é sustentável e fornece um porto seguro de rentabilidade de portfólio."
            }
            """.trimIndent()
        ),
        AnalyzedMatch(
            id = 4,
            homeTeam = "Inglaterra",
            awayTeam = "Camarões",
            league = "Copa do Mundo FIFA 2026 - Grupo D",
            matchDate = "19/06/2026",
            homeOdd = 1.30,
            drawOdd = 5.20,
            awayOdd = 11.00,
            isSample = true,
            rawAnalysisJson = """
            {
              "confidenceScore": 84,
              "recommendedSingleBet": {
                "market": "Inglaterra vence e Over 1.5 Gols",
                "odd": 1.52,
                "probability": 75.0,
                "ev": 9.2,
                "confidence": "Excelente",
                "justification": "A Inglaterra chega com uma das maiores frotas ofensivas (Kane, Bellingham, Saka) com xG médio de 2.25. Camarões tem força física, mas sua zaga cede espaço em bolas aéreas e velocidade."
              },
              "layer1": {
                "homeRecentRecentG": ["V - Bósnia (3-0)", "D - Islândia (0-1)", "V - Sérvia (1-0)", "E - Dinamarca (1-1)", "E - Eslovênia (0-0)"],
                "awayRecentRecentG": ["E - Guiné (1-1)", "D - Senegal (1-3)", "V - Gâmbia (3-2)", "D - Nigéria (0-2)", "V - Cabo Verde (4-1)"],
                "homeXG": 2.25,
                "awayXG": 1.15,
                "homeXGA": 0.85,
                "awayXGA": 1.45,
                "trend": "Inglaterra mantém posse intensa em ritmo pausado, buscando cruzamentos para Harry Kane ou desdobramentos de Jude Bellingham na área.",
                "completions": "16/5 - Inglaterra, 8/2 - Camarões",
                "cornersAndCards": "Cantos médios projetados: 10.0 / Cartões projetados no jogo: 3.5"
              },
              "layer2": {
                "classification": "🟢 Valor Positivo",
                "odDecline": "A odd da Inglaterra caiu ligeiramente nas últimas 12 horas, atraindo forte volume de apostas agrupadas europeias.",
                "inflatedOdds": "A linha de vitória integrada a gols (Inglaterra para vencer + Over 1.5) recebeu impulso para 1.52, superando nossa projeção de 1.40.",
                "biasFavoritism": "O mercado ignora o retrospecto recente competitivo das equipes africanas em estreias carregando odds esticadas de valor."
              },
              "layer3": {
                "probWinHome": 75.0,
                "probDraw": 16.0,
                "probWinAway": 9.0,
                "probOver05": 96.0,
                "probOver15": 81.0,
                "probOver25": 52.0,
                "probOver35": 28.0,
                "probBTTS": 49.0,
                "estCorners": "9.5 escanteios estimados (6.0 Inglaterra, 3.5 Camarões)",
                "estCards": "3.2 cartões estimados no total (1.2 Inglaterra, 2.0 Camarões)"
              },
              "layer4": {
                "riskIndex": 35,
                "injuries": "Inglaterra: Bukayo Saka recuperado do cansaço tático muscular. Camarões: Rigobert Song com elenco integral.",
                "rosterRotation": "Força máxima tática, se tratando da estreia do Grupo D na ensolarada Los Angeles.",
                "environmentalFactors": "SoFi Stadium em Los Angeles. Temperatura interna controlada refrescante (21°C). Sem vento. Campo impecável.",
                "pressureAndMotivation": "Inglaterra entra com o peso da expectativa histórica por glória internacional. Camarões joga como desafiante audaz."
              },
              "layer5": {
                "realProbability": 75.0,
                "impliedProbability": 65.8,
                "expectedValue": 9.2,
                "isEvPositive": true
              },
              "conclusaoExecutiva": "Duelo físico de alto nível no SoFi Stadium. A Inglaterra possui talentos táticos incomparáveis no último terço de campo com Kane e Bellingham. Embora Camarões ofereça um bloco de marcação forte com Anguissa no meio, a velocidade na transição rápida de Saka e Foden criará problemas pelas beiradas. O mercado combinado Inglaterra sela o vitorioso e Over 1.5 gols no jogo oferece uma margem matemática excelente (EV+ de 9.2%), adequada para apostas táticas."
            }
            """.trimIndent()
        )
    )
}
