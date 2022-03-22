package com.fshop.fashionshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestClass {
    @GetMapping
    public String f() {
        return "test";
    }
}
