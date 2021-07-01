package com.client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    private WebClient webClient;

    public WebClientService() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    public Mono<String> getString() {
        return webClient.get()
                .uri("/api/server/hello")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Flux<String> getStringList() {
        return webClient.get()
                .uri("/api/server/helloList")
                .retrieve()
                .bodyToFlux(String.class);
    }
}
