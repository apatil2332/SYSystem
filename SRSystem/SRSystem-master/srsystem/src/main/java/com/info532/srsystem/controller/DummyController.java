package com.info532.srsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DummyController {

    @GetMapping("/dummy")
    public String dummyPage() {
        return "dummy";
    }
}
