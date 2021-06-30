package com.client.service;

import com.client.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class RestTemplateService {

    private final String SERVER_DOMAIN_AND_PORT = "http://localhost:8080";

    public String getForObject() {
        URI uri = UriComponentsBuilder
                .fromUriString(SERVER_DOMAIN_AND_PORT)
                .path("/api/server/hello")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    public String getForEntity() {
        URI uri = UriComponentsBuilder
                .fromUriString(SERVER_DOMAIN_AND_PORT)
                .path("/api/server/hello")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        return result.getBody();
    }

    public void getEntity(){
        URI uri = UriComponentsBuilder
                .fromUriString(SERVER_DOMAIN_AND_PORT)
                .path("/api")
                .queryParam("name","steve")
                .queryParam("age",10)
                .encode()
                .build()
                .toUri();
        log.info("uri : {}",uri);

        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(uri, User.class);
        log.info("user : {}", user);
    }

    public void postForObject(){
        URI uri = UriComponentsBuilder
                .fromUriString(SERVER_DOMAIN_AND_PORT)
                .path("/api/{path}")
                .encode()
                .build()
                .expand("user")
                .toUri();
        log.info("uri : {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        User user = new User();
        user.setName("홍길동");
        user.setAge(10);
        User response = restTemplate.postForObject(uri, user, User.class);
        log.info("response : {}", response);
    }

    public void postForEntity(){
        URI uri = UriComponentsBuilder
                .fromUriString(SERVER_DOMAIN_AND_PORT)
                .path("/api/{path}")
                .encode()
                .build()
                .expand("user")
                .toUri();
        log.info("uri : {}", uri);

        User user = new User();
        user.setName("홍길동");
        user.setAge(10);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.postForEntity(uri, user, User.class);
        log.info("{}",response.getStatusCode());
        log.info("{}",response.getHeaders());
        log.info("{}",response.getBody());
    }

    public void exchange(){
        URI uri = UriComponentsBuilder
                .fromUriString(SERVER_DOMAIN_AND_PORT)
                .path("/api/{path}/header")
                .encode()
                .build()
                .expand("user")
                .toUri();
        log.info("uri : {}", uri);

        User user = new User();
        user.setName("홍길동");
        user.setAge(10);

        RequestEntity<User> req = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization","my-header")
                .body(user);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(req, new ParameterizedTypeReference<User>(){});
        log.info("{}",response.getStatusCode());
        log.info("{}",response.getHeaders());
        log.info("{}",response.getBody());
    }

    public void naver(){
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query","%EC%A3%BC%EC%8B%9D")
                .queryParam("display","10")
                .queryParam("start","1")
                .queryParam("sort","random")
                .encode()
                .build()
                .toUri();
        log.info("uri : {}", uri);

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id","[본인 ID]")
                .header("X-Naver-Client-Secret","[본인 코드]]")
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(req, new ParameterizedTypeReference<String>(){});
        log.info("{}",response.getStatusCode());
        log.info("{}",response.getHeaders());
        log.info("{}",response.getBody());
    }
}
