package com.project.progettoOOP.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * E' la classe che permette di recuperare le informazioni da un CSV parsando un JSON .
 */
public class DownloadCSV {
    /**
     * Metodo che scarica un JSON e lo inserisce in un JSON object
     *
     * @param fileName il nome che si assegnerà al file
     * @throws Exception dovuta al metodo getInputStream
     */
    public static void getCSV(String fileName) throws IOException, JSONException {

        URLConnection openConnection = new URL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=jrc-abcis-ig-2016").openConnection();
        InputStream in = openConnection.getInputStream();

        String data = "";
        String line = "";
        try {
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inR);

            while ((line = buf.readLine()) != null) {
                data += line;
            }
        } finally {
            in.close();
        }
        JSONObject obj = new JSONObject(data);
        JSONObject objI = (obj.getJSONObject("result"));
        JSONArray objA = (objI.getJSONArray("resources"));

        for (int i = 0; i < objA.length(); i++) {
            if (objA.getJSONObject(i) instanceof JSONObject) {
                JSONObject o1 = objA.getJSONObject(i);
                String format = (String) o1.get("format");
                String urlD = (String) o1.get("url");
                if (format.contains("CSV")) {
                    downloadFile(urlD, fileName);
                }
            }
        }
    }


    /**
     * Metodo che si connette al URL e scarica effetivamente il JSON nel filename
     *
     * @param url      URL che identifica il JSON
     * @param fileName nome del file dove verrà salvato il JSON
     * @throws Exception dovuta al metodo copy
     */
    private static void downloadFile(String url, String fileName) throws IOException {
        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }

}