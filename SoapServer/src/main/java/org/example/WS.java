package org.example;

import javax.jws.WebService;

@WebService(endpointInterface = "org.example.SOAPInterface")
public class WS implements SOAPInterface {

    private String cachedXmlData;

    public WS() {
        initializeData();
    }

    private void initializeData() {
        DataFromApiWorldBank dataWorldBankFetcher = new DataFromApiWorldBank();
        String filteredJsonWorldBank = dataWorldBankFetcher.fetchDataAndConvertToFilteredJson();
        dataWorldBankFetcher.saveFileJSON(filteredJsonWorldBank);

        DataFromApiGus dataGusFetcher = new DataFromApiGus();
        String filteredJsonGUS = dataGusFetcher.fetchDataAndConvertToFilteredJson();

        if (filteredJsonGUS != null) {
            this.cachedXmlData = dataGusFetcher.convertJsonToXml(filteredJsonGUS);
            System.out.println("Dane z API GUS zostały pomyślnie załadowane i skonwertowane do XML.");
        } else {
            System.err.println("Nie udało się pobrać i przetworzyć danych z API GUS. Serwis będzie zwracał domyślny komunikat o błędzie.");
            this.cachedXmlData = "<data><error>Nie udało się załadować danych z API GUS.</error></data>";
        }
        dataGusFetcher.saveFileXml(cachedXmlData);
    }
    @Override
    public String getData() {
        return cachedXmlData;
    }
}