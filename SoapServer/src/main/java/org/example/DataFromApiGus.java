package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DataFromApiGus {

    private static final String API_URL = "https://bdl.stat.gov.pl/api/v1/data/by-variable/473986?unit-level=0&year=2003-2023";
    private static File xmlFile;

    public String fetchDataAndConvertToFilteredJson() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Błąd odpowiedzi z API GUS: " + responseCode);
                return null;
            }

            try (InputStream is = conn.getInputStream()) {
                String source = new BufferedReader(new InputStreamReader(is))
                        .lines().collect(Collectors.joining("\n"));

                JSONObject object = new JSONObject(source);
                JSONArray results = object.getJSONArray("results");
                if (results.length() == 0) {
                    System.err.println("Brak danych w sekcji 'results' z API GUS.");
                    return null;
                }

                JSONArray values = results.getJSONObject(0).getJSONArray("values");

                JSONArray filtered = new JSONArray();
                for (int i = 0; i < values.length(); i++) {
                    JSONObject item = values.getJSONObject(i);
                    JSONObject data = new JSONObject();
                    if (item.has("year") && item.has("val")) {
                        data.put("year", item.getString("year"));
                        data.put("val", item.getInt("val"));
                        filtered.put(data);
                    } else {
                        System.err.println("Brak kluczy 'year' lub 'val' w elemencie API: " + item.toString());
                    }
                }
                return filtered.toString(2); // Zwraca sformatowany JSON
            }

        } catch (Exception e) {
            System.err.println("Błąd połączenia z API GUS lub przetwarzania danych: " + e.getMessage());
            return null;
        }
    }
    public String convertJsonToXml(String jsonSource) {
        if (jsonSource == null || jsonSource.isEmpty()) {
            return "<data><error>Brak danych JSON do konwersji.</error></data>";
        }
        try {
            JSONArray array = new JSONArray(jsonSource);
            StringBuilder xml = new StringBuilder();
            xml.append("<data>");
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                xml.append("<entry>");
                xml.append("<year>").append(item.getString("year")).append("</year>");
                xml.append("<val>").append(item.getInt("val")).append("</val>");
                xml.append("</entry>");
            }
            xml.append("</data>");
            return xml.toString();
        } catch (Exception e) {
            System.err.println("Błąd konwersji JSON na XML: " + e.getMessage());
            return "<data><error>Błąd konwersji danych na XML.</error></data>";
        }
    }
    public void saveFileXml(String data){
        final Path pathXML = Paths.get("C:","Users","barte","OneDrive","Pulpit","VI semestr","6.SE.2 Integracja systemów","Laboratorium","Integracja_Systemow_Projekt","DataFiles","GusData.xml");
        final Path dockerPathXML = Paths.get("/app", "DataFiles", "GusData.xml");

        if(Main.isDockerPath()) {
            xmlFile = dockerPathXML.toFile();
        } else {
            xmlFile = pathXML.toFile();
        }

        try (FileWriter writer = new FileWriter(xmlFile)) {
            writer.write(data);
            System.out.println("Plik GusData.xml został zapisany.");
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku GusData.xml: " + e.getMessage());
        }
    }
}