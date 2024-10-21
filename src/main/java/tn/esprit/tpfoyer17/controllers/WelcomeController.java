package tn.esprit.tpfoyer17.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WelcomeController {


    @RequestMapping("/")
    @ResponseBody
    public String welcome() { return "From Docker Container: Bonjour, Bienvenue à l'application de test des Web Services REST."; }


}

