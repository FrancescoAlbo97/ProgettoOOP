package com.project.progettoOOP.utils;

import com.project.progettoOOP.model.Environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Classe che mi permette di fare il parsing del CSV già scaricato
 */
public class ParserCSV {

    final static String COMMA_DELIMITER = ",";

    /**
     * Metodo che parsa il CSV scaricato e ritorna le informazioni attraverso un arrayList
     *
     * @param fileName nome dove è salvato il CSV scaricato
     * @return ritorna la collezione  di oggetti
     */
    public static ArrayList<Environment> parser (String fileName){

        ArrayList<Environment> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                for(int i = 1; i < 7; i++){
                    if (values[i].charAt(0) == '-'){
                        values[i] = null;
                    }
                }
                arrayList.add(new Environment(values[0],values[1],values[2],values[3],values[4],values[5], values[6]));
            }
        } finally {
            return arrayList;
        }
    }

}