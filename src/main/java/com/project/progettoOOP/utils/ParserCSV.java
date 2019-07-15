package com.project.progettoOOP.utils;

import com.project.progettoOOP.model.Environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        //List<List<String>> records = new ArrayList<>();
        ArrayList<Environment> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                //records.add(Arrays.asList(values));
                for(int i = 1; i < 7; i++){
                    if (values[i].contains("-") && !(values[i].contains("E"))){
                        values[i] = null;
                    }
                }
                arrayList.add(new Environment(values[0],values[1],values[2],values[3],values[4],values[5], values[6]));
                /*
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date date = formatter.parse(values[0]);
                arrayList.add(new Environment(date, Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6])));
                */
            }
        } finally {
            return arrayList;
        }
    }

}