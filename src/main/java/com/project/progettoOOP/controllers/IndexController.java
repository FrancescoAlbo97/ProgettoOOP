package com.project.progettoOOP.controllers;

import com.project.progettoOOP.utils.DownloadCSV;
import com.project.progettoOOP.utils.ParserCSV;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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