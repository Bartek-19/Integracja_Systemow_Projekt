package org.example;

import javax.xml.ws.Endpoint;

public class Publisher {
    public void start(){
        Endpoint.publish("http://localhost:7777/soap", new WS());
        System.out.println("SOAP service running at http://localhost:7777/soap");
        System.out.println("WSDL: http://localhost:7777/soap?wsdl");
    }
}