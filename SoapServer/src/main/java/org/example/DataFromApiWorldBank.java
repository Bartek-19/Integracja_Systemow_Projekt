package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class DataFromApiWorldBank {
    private static final String API_URL = "https://api.worldbank.org/v2/country/PL/indicator/FP.CPI.TOTL.ZG?format=json&date=2003:2023";
    public String fetchDataAndConvertToFilteredJson() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Błąd odpowiedzi z API World Bank: " + responseCode);
                return null;
            }

            try (InputStream is = conn.getInputStream()) {
                String source = new BufferedReader(new InputStreamReader(is))
                        .lines().collect(Collectors.joining("\n"));

                JSONArray array = new JSONArray(source);
                JSONArray results = array.getJSONArray(1);

                if (results.length() == 0) {
                    System.err.println("Brak danych w sekcji 'wb:data' z API GUS.");
                    return null;
                }

//                JSONArray values = results.getJSONObject(0).getJSONArray("date");

                JSONArray filtered = new JSONArray();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    JSONObject data = new JSONObject();
                    if (item.has("date") && item.has("value")) {
                        data.put("date", item.getString("date"));
                        data.put("value", item.getDouble("value"));
                        filtered.put(data);
                    } else {
                        System.err.println("Brak kluczy 'date' lub 'value' w elemencie API: " + item.toString());
                    }
                }
                return filtered.toString(2); // Zwraca sformatowany JSON
            }

        } catch (Exception e) {
            System.err.println("Błąd połączenia z API WorldBank lub przetwarzania danych: " + e.getMessage());
            return null;
        }
    }
    public void saveFileJSON(String data){
        try (FileWriter writer = new FileWriter("WorldBankData.json")) {
            writer.write(data);
            System.out.println("Plik WorldBankData.json został zapisany.");
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku WorldBankData.json: " + e.getMessage());
        }
    }
}
