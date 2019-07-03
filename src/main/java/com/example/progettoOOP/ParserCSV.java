package com.example.progettoOOP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class ParserCSV {

    final static String COMMA_DELIMITER = ",";
    public static String vector = " ";

    public static void parser (String fileName) throws IOException {


        List<List<String>> records = new ArrayList<>();
        Vector<Environment> v = new Vector<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);

                records.add(Arrays.asList(values));
                v.add(new Environment(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]), Float.parseFloat(values[5]), Float.parseFloat(values[1]))); //values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3])
                //System.out.println(v.toString());
                //vector += values [0];
            }
            //br.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        }
    }
}