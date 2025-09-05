package com.ie017.agenticai.agenticai.tools;


import com.ie017.agenticai.agenticai.output.Permis;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class AgentTools {

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
}



