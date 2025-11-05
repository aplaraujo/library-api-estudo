package io.github.aplaraujo.library_api_estudo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Controller - anotação para uso de páginas web
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }
}
