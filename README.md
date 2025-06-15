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

GET http://localhost:8080/api/inflations - pobieranie listy wskaźników inflacji w określonych latach

GET http://localhost:8080/api/charts - zwraca tablicę z nazwami wykresów dostępnych w API

GET http://localhost:8080/api/charts/{nazwa}.png - zwraca grafikę z wykresem o określonej nazwie (na chwilę obecną dostępne tylko 'wykres_liniowy' i 'wykres_slupkowy')

GET http://localhost:8080/api/user - zwraca dane użytkownika w oparciu o wprowadzony w nagłówku żądania token JWT ("Authorization":"Bearer {tokenJWT}")

### POST
POST http://localhost:8080/api/graduates - dodanie nowych danych dotyczących liczby absolwentów (np. body -> {"year":2000, "number":13460}), kod błędu 409 w przypadku, gdy zostały wprowadzone dane na podany rok 

POST http://localhost:8080/api/inflations - dodanie nowych danych dotyczących wskaźnika inflacji (np. body -> {"year":2000, "rate":8.1}), kod błędu 409 w przypadku, gdy zostały wprowadzone dane na podany rok

POST http://localhost:8080/api/users - rejestrowanie nowego użytkownika (np. body -> {"login":"userTest", "password":"passTest"})

POST http://localhost:8080/api/auth - logowanie się użytkownika z jego danymi (np. body -> {"login":"userTest", "password":"passTest"}) i otrzymanie Tokena JWT w odpowiedzi na żądanie

### DELETE
DELETE http://localhost:8080/api/graduates/{rok} - usuwanie danych z wybranego roku (np. http://localhost:8080/api/graduates/2020), kod błędu 404 w przypadku, gdy nie ma wprowadzonych danych z podanego roku

DELETE http://localhost:8080/api/inflations/{rok} - usuwanie danych z wybranego roku (np. http://localhost:8080/api/inflations/2020), kod błędu 404 w przypadku, gdy nie ma wprowadzonych danych z podanego roku

### PUT
PUT http://localhost:8080/api/graduates/{rok} - aktualizowanie danych z wybranego roku (np. http://localhost:8080/api/graduates/2020, body -> {"year":2020, "number":12000}), kod błędu 404 w przypadku, gdy nie ma wprowadzonych danych z podanego roku

PUT http://localhost:8080/api/inflations/{rok} - aktualizowanie danych z wybranego roku (np. http://localhost:8080/api/inflations/2020, body -> {"year":2020, "rate":8.5}), kod błędu 404 w przypadku, gdy nie ma wprowadzonych danych z podanego roku

