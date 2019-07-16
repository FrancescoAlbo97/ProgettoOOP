package com.project.progettoOOP;

import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.utils.DownloadCSV;
import com.project.progettoOOP.utils.ParserCSV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * Classe da cui parte l'esecuzione del programma quando si avvia il Servizio
 */
@SpringBootApplication
public class ProgettoOopApplication {
    /**
     * Funzione che inizializza l'applicazione e la fa partire.Prima di avviare l'applicazione si scarica,se non è presente, il file CSV dal JSON, fornito
     * dalle specifiche del progetto, attraverso la classe DownloadCSV.
     *
     * @param args eventuali parametri passati dal CLI
     * @throws Exception dovuta al metodo statico getCSV
     */
    public static final String FILE_NAME = "Data.csv";

	public static void main(String[] args) throws Exception {
		File file = new File(FILE_NAME);
		if(!file.exists()){
			DownloadCSV.getCSV(FILE_NAME);
		}
		EnvironmentCollection.environments = ParserCSV.parser(FILE_NAME);
		SpringApplication.run(ProgettoOopApplication.class, args);
	}
}
