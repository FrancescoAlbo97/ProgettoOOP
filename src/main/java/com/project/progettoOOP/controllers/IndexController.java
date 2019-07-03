package com.project.progettoOOP.controllers;

import com.project.progettoOOP.DownloadCSV;
import com.project.progettoOOP.ParserCSV;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String download (Model model) throws Exception {
        DownloadCSV.getJson();
        ParserCSV.parser("t1.csv");
        model.addAttribute("json", ParserCSV.vector );
        return "download";
    }

    @GetMapping("/finish")
    public String finish(){
        return "finish";
    }

}