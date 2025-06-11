package org.example;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SOAPInterface {
    @WebMethod
    String getData();
}