# AgenticAI101

Projet Spring Boot démontrant les concepts d'**IA Agentique** avec le framework **Spring AI**.

---

## 📚 Grands titres traités dans ce repo

### 1. 🤖 Zero-Shot Prompting
Interaction directe avec un LLM en fournissant uniquement des instructions système (*system prompt*), sans exemples préalables.

- Endpoint : `GET /agenticai/ZeroShotPromptingChat?query=...`
- Le modèle répond selon les instructions système uniquement.

### 2. 🎯 Few-Shot Prompting
Interaction avec un LLM en fournissant des **exemples de paires question/réponse** avant la vraie question, pour guider le comportement du modèle.

- Endpoint : `GET /agenticai/FewShotPromptingChat?query=...`
- Des messages exemples sont injectés dans le prompt pour orienter la réponse.

### 3. 🧠 Gestion de la Mémoire de Chat (Chat Memory)
L'IA est **sans état (stateless) par défaut**. Ce projet utilise `MessageChatMemoryAdvisor` de Spring AI pour maintenir un historique de conversation (jusqu'à 20 messages), rendant l'agent capable de se souvenir du contexte.

### 4. 🖼️ Génération d'Images avec DALL-E
Génération d'images par IA via **OpenAI DALL-E-3**, à partir d'une description textuelle.

- Endpoint : `GET /generatorimageagenticai/generate?query=...`
- Qualité HD, modèle `dall-e-3`.

### 5. 🔌 Abstraction Multi-LLM avec Spring AI
Utilisation de l'interface `ChatClient` de Spring AI pour **communiquer avec n'importe quel LLM** (OpenAI, Ollama, etc.) de manière transparente, sans changer le code métier.

### 6. 📖 Documentation API avec Swagger / OpenAPI
L'API REST est documentée automatiquement via **SpringDoc OpenAPI (Swagger UI)**.

- Accessible sur : `http://localhost:9090/swagger-ui/index.html`

---

## 🛠️ Stack Technique

| Technologie        | Version  | Rôle                              |
|--------------------|----------|-----------------------------------|
| Java               | 21       | Langage principal                 |
| Spring Boot        | 3.5.5    | Framework applicatif              |
| Spring AI          | 1.0.1    | Abstraction LLM                   |
| OpenAI (GPT-4.1)   | -        | Modèle de langage (LLM)           |
| DALL-E-3           | -        | Génération d'images               |
| Ollama             | -        | LLM local (commenté, optionnel)   |
| H2 Database        | -        | Base de données en mémoire        |
| Lombok             | -        | Génération de code boilerplate    |
| SpringDoc OpenAPI  | 2.8.10   | Documentation Swagger UI          |

---

## 🚀 Démarrage

1. Configurer la clé API OpenAI dans `src/main/resources/application.properties` :
   ```properties
   spring.ai.openai.api-key=YOUR_OPENAI_API_KEY
   ```

2. Lancer l'application :
   ```bash
   ./mvnw spring-boot:run
   ```

3. L'API est disponible sur : `http://localhost:9090`

---

## 📂 Structure du Projet

```
src/
└── main/
    └── java/com/ie017/agenticai/
        ├── AgenticaiApplication.java                         # Point d'entrée Spring Boot
        └── agenticai/controller/
            ├── AgenticAIController.java                      # Endpoints Zero-Shot & Few-Shot
            └── GeneratorImageAgenticAIController.java        # Endpoint génération d'images
```
