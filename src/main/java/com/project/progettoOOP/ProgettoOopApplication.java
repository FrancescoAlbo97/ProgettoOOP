package com.project.progettoOOP;

import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.utils.DownloadCSV;
import com.project.progettoOOP.utils.ParserCSV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ProgettoOopApplication {

	public static void main(String[] args) throws Exception {
		File file = new File("data.csv");
		if(!file.exists()){
			DownloadCSV.getCSV("data.csv");
		}
		EnvironmentCollection.environments = ParserCSV.parser("data.csv");
		SpringApplication.run(ProgettoOopApplication.class, args);
	}
}
