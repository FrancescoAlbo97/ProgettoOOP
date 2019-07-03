package com.project.progettoOOP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class ParserCSV {

    final static String COMMA_DELIMITER = ",";
    public static String vector = "ciao ";

    public static void parser (String fileName) throws IOException, ParseException {


        List<List<String>> records = new ArrayList<>();
        Vector<Environment> v = new Vector<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            int conta = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);

                records.add(Arrays.asList(values));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date date = formatter.parse(values[0]);
                v.add(new Environment(date, Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[1]))); //values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3])

                if (conta <80){
                    vector += v.get(conta).toString();
                    conta++;
                }
               //vector += values [0];

            }
            //br.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        }
    }
}