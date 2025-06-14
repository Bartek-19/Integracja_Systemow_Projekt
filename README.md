# Integracja_Systemow_Projekt

## Server SOAP - Java
Wersje bibliotek:

javax.jws:javax.jws-api:1.1

javax.xml.ws:jaxws-api:2.3.1

com.sun.xml.ws:rt:2.3.2

## Server REST API - Java
Dostępne endpointy:

### GET
GET http://localhost:8080/api/graduates - pobieranie listy liczby absolwentów w określonych latach

GET http://localhost:8080/api/graduates - pobieranie listy wskaźników inflacji w określonych latach

### POST
POST http://localhost:8080/api/graduates - dodanie nowych danych dotyczących liczby absolwentów (np. body -> {"year":2000, "number":13460}), kod błędu 409 w przypadku, gdy zostały wprowadzone dane na podany rok 

POST http://localhost:8080/api/graduates - dodanie nowych danych dotyczących wskaźnika inflacji (np. body -> {"year":2000, "rate":8.1}), kod błędu 409 w przypadku, gdy zostały wprowadzone dane na podany rok

### DELETE
DELETE http://localhost:8080/api/graduates/{rok} - usuwanie danych z wybranego roku (np. http://localhost:8080/api/graduates/2020), kod błędu 404 w przypadku, gdy nie ma wprowadzonych danych z podanego roku

DELETE http://localhost:8080/api/graduates/{rok} - usuwanie danych z wybranego roku (np. http://localhost:8080/api/graduates/2020), kod błędu 404 w przypadku, gdy nie ma wprowadzonych danych z podanego roku
