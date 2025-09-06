package com.ie017.agenticai.agenticai.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Component
public class DocumentIndexer {

    @Value("classpath:/pdfs/EL_ORF_ISSAM.pdf")
    private Resource resource;
    @Value("store.json")
    private String fileStore;
    private SimpleVectorStore vectorStore;

    public DocumentIndexer(SimpleVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Bean
    public SimpleVectorStore indexDocuments() {
        // chercher dans le dossier src et apres chercher dans main, apres resources et apres images
        Path path = Path.of("src", "main", "resources", "stores");
        File file = new File(path.toFile(), fileStore);

        if (file.exists()) {
            // Charger le vector store a partir du fichier json
            vectorStore.load(file);
        }
        // Lire le pdf avec PagePdfDocumentReader
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
        // Obtenir la liste des documents a partir du pdf, chaque page est un document
        List<Document> documents = reader.get();
        // definir le splitter pour decouper les documents en chunks, le splitter utilise les tokens pour decouper les documents
        TextSplitter splitter = new TokenTextSplitter();
        // Decouper les documents en chunks
        List<Document> chunks = splitter.apply(documents);
        // Ajouter les chunks au vector store
        vectorStore.add(chunks);
        // Sauvegarder le vector store dans un fichier json
        vectorStore.save(file);
        return vectorStore;
    }


}
