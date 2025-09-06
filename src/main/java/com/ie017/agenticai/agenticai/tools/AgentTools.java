package com.ie017.agenticai.agenticai.tools;


import com.ie017.agenticai.agenticai.output.Permis;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgentTools {

    private VectorStore vectorStore;

    public AgentTools(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Tool(description = "Cet outil est utilisé pour extraire les informations des citoyen")
    public Permis getPermisInfos() {
        return new Permis(
                "1111",
                "ISSAM",
                "EL ORF",
                "01-01-2000",
                "Biougra",
                "KE45213",
                "20-02-2025"
        );
    }

    @Tool(description = "Cet outil est utilisé pour extraire le contexte pertinent pour une question donnée")
    public List<String> getContext(String query) {
        // Faire une recherche de similarité dans le vector store
        return this.vectorStore.similaritySearch(
                // Construire la requete de recherche
                SearchRequest.builder()
                        .query(query)
                        // Nombre de documents similaires a retourner
                        .topK(5)
                .build())
                .stream()
                // Extraire le texte de chaque document
                .map(Document::getText)
                .toList();
    }
}



