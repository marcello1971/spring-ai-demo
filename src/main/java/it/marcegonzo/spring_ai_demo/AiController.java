package it.marcegonzo.spring_ai_demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final RestClient restClient = RestClient.create();
    private final String apiKey;
    private final String model;

    public AiController(
            @Value("${spring.ai.google.genai.api-key}") String apiKey,
            @Value("${spring.ai.google.genai.chat.options.model}") String model) {
        this.apiKey = apiKey;
        this.model = model;
    }

    @GetMapping("/chiedi")
    public String chiedi(@RequestParam(defaultValue = "Spiegami in 2 righe cos'è Spring AI") String domanda) {
        String fullUrl = "https://generativelanguage.googleapis.com" + "/v1beta/models/" + model + ":generateContent?key=" + apiKey;


        GeminiResponse response = restClient.post()
                .uri(fullUrl)
                .header("Content-Type", "application/json")
                .body(GeminiRequest.daTesto(domanda))
                .retrieve()
                .body(GeminiResponse.class);

        return response != null ? response.estraiTesto() : "Nessuna risposta ricevuta";
    }
    
}