package com.project.progettoOOP.controllers;

import com.project.progettoOOP.model.EnvironmentCollection;
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
    public String download (Model model) throws Exception {
        DownloadCSV.getCSV();
        EnvironmentCollection environmentCollection = ParserCSV.parser("data.csv");
        model.addAttribute("test", ParserCSV.vector );
        return "download";
    }

    @GetMapping("/finish")
    public String finish(){
        return "finish";
    }

}