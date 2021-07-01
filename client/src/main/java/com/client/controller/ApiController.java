package com.client.controller;

import com.client.service.RestTemplateService;
import com.client.service.WebClientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ApiController {

    private final RestTemplateService restTemplateService;
    private final WebClientService webClientService;

    @GetMapping("/restTemplateHello")
    public String restTemplateHello() {
        return restTemplateService.getForEntity();
    }

    @CircuitBreaker(name = "restTemplate", fallbackMethod = "restTemplateFallback")
    @GetMapping("/restTemplateHello/{id}")
    public String restTemplateHelloFail(@PathVariable(name = "id") int id) {
        return restTemplateService.getForEntityFail(id);
    }

    public String restTemplateFallback(int id, Throwable t) {
        log.error("restTemplateFallback : " + t.getMessage());
        return "restTemplateFallback data";
    }

//    -----------------------------------------------

    @GetMapping("/webClientHello")
    public Mono<String> webClientHello() {
        return webClientService.getString();
    }

    @CircuitBreaker(name = "webClient", fallbackMethod = "webClientFallback")
    @GetMapping("/webClientHello/{id}")
    public Mono<String> webClientHelloFail(@PathVariable(name = "id") int id) {
        return webClientService.getStringFail(id);
    }

    public Mono<String> webClientFallback(int id, Throwable t) {
        log.error("webClientFallback : " + t.getMessage());
        return Mono.just("webClientFallback data");
    }

    @GetMapping("/webClientHelloList")
    public Flux<String> webClientHelloList() {
        return webClientService.getStringList();
    }
}
