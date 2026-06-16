# PRONOELITE (Elite Bet Engine) ⚽🤖

**PRONOELITE** é um motor de inteligência artificial e análise quantitativa desenvolvido para atuar como um departamento completo de trading esportivo profissional. O aplicativo analisa partidas de futebol de elite (como os próximos confrontos reais da **Copa do Mundo FIFA 2026**) cruzando dados avançados, tendências de mercado e modelagem preditiva estatística em 5 camadas de rigor tático/matemático.

O aplicativo é escrito em **moderno Kotlin e Jetpack Compose (Material Design 3)** e adota arquitetura reativa integrada a um banco de dados local **Room** para persistência e sincronização de análises.

---

## 🚀 Principais Recursos (As 5 Camadas de Análise)

Para cada partida catalogada ou adicionada pelo usuário, o motor calcula detalhadamente:

1. **CAMADA 1 – FORMA REAL DAS EQUIPES**
   - Cruzamento dos últimos 5 e 10 jogos (dentro/fora de casa).
   - Verificação de Gols Esperados (**xG**) e Gols Sofridos Esperados (**xGA**).
   - Análise de tendências, escanteios, cartões e finalizações no alvo.
2. **CAMADA 2 – LEITURA DE MERCADO**
   - Identificação de oscilações (drops) de cotações nas últimas horas.
   - Detecção de odds infladas em casas de aposta tradicionais.
   - Sinalização de favoritismo excessivo e possíveis armadilhas/iscas de mercado.
   - Classificação intuitiva de valor: `🟢 Valor Positivo`, `🟡 Neutro` ou `🔴 Sem Valor`.
3. **CAMADA 3 – MODELO PREDITIVO**
   - Simulação estocástica das probabilidades de resultado de partida (1X2).
   - Cálculo das probabilidades matemáticas para linhas de gols: `Over/Under 0.5, 1.5, 2.5 e 3.5`.
   - Projeção de Ambas Marcam (`BTTS`), escanteios e cartões amarelos.
4. **CAMADA 4 – FILTRO DE RISCO & CONTEXTO**
   - Boletim médico atualizado de desfalques/lesões graves.
   - Análise de rotação de elenco pelo calendário intensivo.
   - Fatores geográficos/ambientais (relevo, altitude, clima, torcida e pressão extra).
5. **CAMADA 5 – EXPECTED VALUE (EV+)**
   - Comparação racional entre a probabilidade implícita do mercado (`100 / Odd`) e a probabilidade real projetada pela IA.
   - Alerta visual imediato de rentabilidade se `EV+ > 0%`.

---

## 📊 Módulos do Sistema

O aplicativo é dividido em três abas principais, proporcionando um fluxo intuitivo para investidores:

*   **Dashboard de Análises (Aba 1):** Apresenta a jornada de rodadas. Inclui partidas pré-semeadas reais da primeira rodada da **Copa do Mundo FIFA 2026** (com jogos como *Brasil vs Haiti*, *Escócia vs Marrocos* e *Argentina vs Suécia*) prontas para análise. O usuário pode adicionar novos jogos em tempo real fornecendo as odds da casa.
*   **Gerador de Múltiplas (Aba 2):** Algoritmo que vasculha o banco de dados e monta combinações matemáticas de apostas de forma dinâmica (múltiplas *Seguras*, *Premium*, *Agressivas* e *Extremas*), mostrando as cotações compostas e a probabilidade matemática ponderada de acerto.
*   **Trading Department (Aba 3):** Visão corporativa de portfólio que resume o Expected Value médio das decisões salvas, o número total de jogos mapeados e regras fundamentais de **Gestão de Banca profissional** (como o controle rígido de stakes de no máximo 2.5% por entrada).

---

## 🛠️ Arquitetura e Tecnologia

O projeto adota os padrões modernos de engenharia para o ecossistema Android:

*   **Jetpack Compose:** Layout declarativo fluido estruturado em um belíssimo tema Dark de automobilismo/cyberpunk esportivo com cores neon contrastantes (`SportGreen` / `CyberTeal` / `WarningOrange`).
*   **Room Database:** Persistência reativa offline-first local de todas as análises para garantir rápido acesso aos registros sem travar o dispositivo.
*   **Moshi & Retrofit:** Deserialização robusta de JSONs complexos e arquitetura assíncrona tolerante a falhas na busca de dados.
*   **Gemini API Rest Integration:** O aplicativo conecta-se diretamente ao modelo generativo avançado para rodar a rede síncrona de 5 camadas em tempo real em novas adições sempre que uma Chave de API for configurada.
*   **Graceful Local Fallback Engine:** Caso nenhuma chave esteja associada nas variáveis de ambiente, o sistema ativa uma modelagem local realista que calcula dados táticos precisos de xG baseados nas fórmulas de odds implícitas das casas para manter o app 100% funcional.

---

## 🔑 Configuração da Chave Gemini API (Vincular Inteligência Livre)

Para habilitar a análise em tempo real acionada pela nuvem na hora de cadastrar novas partidas:

1. Obtenha uma chave API gratuita no portal do **Google AI Studio**.
2. Adicione-a de forma segura em suas variáveis de ambiente ou com o arquivo `.env`:
   ```bash
   GEMINI_API_KEY="SUA_CHAVE_AQUI"
   ```
3. O aplicativo injetará a variável automaticamente via `BuildConfig` ocultando banners locais pós-configuração.

---

## 📦 Como Compilar e Executar no GitHub/Android Studio

1. Clone o repositório em sua máquina local:
   ```bash
   git clone https://github.com/seu-usuario/sua-licenca-pronoelite.git
   ```
2. Abra o projeto no **Android Studio** (Koala ou mais recente).
3. Aguarde a sincronização de dependências via Gradle. O projeto já utiliza o catálogo de versões `gradle/libs.versions.toml`.
4. Clique em **Run** (`Shift + F10`) em um dispositivo físico ou emulador moderno.
5. O aplicativo está pronto! Sinta-se livre para empacotar via APK de depuração ou release.
