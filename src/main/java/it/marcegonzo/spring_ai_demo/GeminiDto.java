package it.marcegonzo.spring_ai_demo;

import java.util.List;

// Strutture per la RICHIESTA verso Gemini
record GeminiRequest(List<Content> contents) {
    // Metodo di fabbrica comodo per creare una richiesta da una semplice stringa
    static GeminiRequest daTesto(String testo) {
        return new GeminiRequest(List.of(new Content(List.of(new Part(testo)))));
    }
}

// Strutture per la RISPOSTA da Gemini
record GeminiResponse(List<Candidate> candidates) {
    // Metodo comodo per estrarre subito il testo della prima risposta
    String estraiTesto() {
        return candidates.getFirst().content().parts().getFirst().text();
    }
}

// Elementi comuni condivisi tra richiesta e risposta
record Candidate(Content content) {}
record Content(List<Part> parts) {}
record Part(String text) {}