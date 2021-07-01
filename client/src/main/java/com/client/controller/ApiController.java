package com.client.controller;

import com.client.service.RestTemplateService;
import com.client.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @GetMapping("/webClientHello")
    public Mono<String> webClientHello() {
        return webClientService.getString();
    }

    @GetMapping("/webClientHelloList")
    public Flux<String> webClientHelloList() {
        return webClientService.getStringList();
    }
}
