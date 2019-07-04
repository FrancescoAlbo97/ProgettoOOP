package com.project.progettoOOP.utils;

import com.project.progettoOOP.model.Environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParserCSV {

    final static String COMMA_DELIMITER = ",";
    public static String vector = " ";

    public static void parser (String fileName) throws ParseException {

        List<List<String>> records = new ArrayList<>();
        ArrayList<Environment> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            int conta = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);

                records.add(Arrays.asList(values));

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date date = formatter.parse(values[0]);

                arrayList.add(new Environment(date, Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[6])));
                if (conta < 30){
                    vector += arrayList.get(conta).toString();
                    conta++;

                }
            }
            //br.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        }
    }
}