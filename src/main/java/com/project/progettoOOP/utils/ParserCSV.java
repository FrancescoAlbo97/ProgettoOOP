package com.project.progettoOOP.utils;

import com.project.progettoOOP.model.Environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ParserCSV {

    final static String COMMA_DELIMITER = ",";

    public static ArrayList<Environment> parser (String fileName){

        List<List<String>> records = new ArrayList<>();
        ArrayList<Environment> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
                arrayList.add(new Environment(values[0],values[1],values[2],values[3],values[4],values[5], values[6]));
                /*
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date date = formatter.parse(values[0]);
                arrayList.add(new Environment(date, Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6])));
                */
            }
        }
        finally {
            return arrayList;
        }
    }
}