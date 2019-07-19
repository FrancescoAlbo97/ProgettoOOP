
package com.project.progettoOOP.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe che implementa il controller utilizzato per mostrare il frontend dell'applicazione all'utente
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String download (Model model) {
        model.addAttribute("test","ciaoooo");
        return "download";
    }

    @GetMapping("/finish")
    public String finish(){
        return "finish";
    }

}