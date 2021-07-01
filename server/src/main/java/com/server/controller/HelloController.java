package com.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/server")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Server";
    }

    @GetMapping("/helloList")
    public List<String> helloList() {
        List<String> arr = new ArrayList<>();
        arr.add("hello");
        arr.add("server");
        arr.add("bye");
        return arr;
    }
}
