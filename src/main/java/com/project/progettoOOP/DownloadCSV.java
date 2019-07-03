package com.project.progettoOOP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadCSV {

    public static void getCSV() throws Exception {

        URLConnection openConnection = new URL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=jrc-abcis-ig-2016").openConnection();
        //openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        InputStream in = openConnection.getInputStream();

        String data = "";
        String line = "";
        try {
            InputStreamReader inR = new InputStreamReader( in );
            BufferedReader buf = new BufferedReader( inR );

            while ( ( line = buf.readLine() ) != null ) {
                data += line;
            }
        } finally {
            in.close();
        }
        JSONObject obj = new JSONObject(data);
        JSONObject objI = (obj.getJSONObject("result"));
        JSONArray objA = (objI.getJSONArray("resources"));

        for(int i=0; i < objA.length(); i++) {
            if (objA.getJSONObject(i) instanceof JSONObject) {
                JSONObject o1 = objA.getJSONObject(i);
                String format = (String) o1.get("format");
                String urlD = (String) o1.get("url");
                if (format.contains("CSV")) {
                    downloadFile(urlD, "data.csv");
                }
            }
        }
    }

    private static void downloadFile(String url, String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists()){
            try (InputStream in = URI.create(url).toURL().openStream()) {
                Files.copy(in, Paths.get(fileName));
            }
        }
    }

}
    /*throws Exception {
        URL url = new URL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=jrc-abcis-ig-2016");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

        int responsecode = connection.getResponseCode();
        if(responsecode != 200) throw new RuntimeException("HttpResponseCode: " +responsecode);
        else
        {
            Scanner sc = new Scanner(url.openStream());

            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            sc.close();
        }

        JSONObject obj = (JSONObject) JSONValue.parseWithException(inline);
        JSONObject objI = (JSONObject) (obj.get("result"));
        JSONArray objA = (JSONArray) (objI.get("resources"));

        for(int i=0; i < objA.length(); i++) {
            if (objA.get(i) instanceof JSONObject) {
                JSONObject o1 = (JSONObject) objA.get(i);
                String format = (String) o1.get("format");
                String urlD = (String) o1.get("url");
                if (format.equals("CSV")) {
                    downloadFile(urlD, "t1.csv");
                }
            }
        }
}*/