package com.gaviria.transaction.interfaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/")
    public String redirectToUi() {
        return "redirect:/swagger-ui/index.html";
    }
}