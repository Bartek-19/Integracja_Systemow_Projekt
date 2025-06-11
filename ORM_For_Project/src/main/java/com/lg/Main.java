package com.lg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//  Kod przeznaczony do skopiowania do aplikacji serwerowej
//
//  Aby program działał prawidłowo należy oprócz kodu i klas
//  dodać dodatkowo folder META-INF z folderu resources - w
//  pliku persistence.xml znajdują się informacje odnośnie
//  docelowej lokalizacji bazy danych, które trzeba będzie
//  zmodyfikować w wersji implementacyjnej. Konieczne będzie
//  utworzenie bazy danych o podanej nazwie na serwerze MySQL.
//  Dodatkowo, w pliku pom.xml znajdują się zależności
//  odpowiadające za rozszerzenia konieczne do działania programu.
//
//  Program testowany manualnie - działa prawidłowo
//

public class Main {
    public static void main(String[] args) {
        final Path pathXML = Paths.get("C:","Users","barte","OneDrive","Pulpit","VI semestr","6.SE.2 Integracja systemów","Laboratorium","Integracja_Systemow_Projekt","DataFiles","GusData.xml");
        final Path pathJSON = Paths.get("C:","Users","barte","OneDrive","Pulpit","VI semestr","6.SE.2 Integracja systemów","Laboratorium","Integracja_Systemow_Projekt","DataFiles","WorldBankData.json");
        List<Graduates> graduates = new ArrayList<>();
        List<Inflation> inflations = new ArrayList<>();

        try {
            File xmlFile = pathXML.toFile();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("entry");

            for (int i = 0; i < nList.getLength(); i++) {
                Node entryNode = nList.item(i);

                if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element entryElement = (Element) entryNode;
                    int year = Integer.parseInt(entryElement.getElementsByTagName("year").item(0).getTextContent());
                    int number = Integer.parseInt(entryElement.getElementsByTagName("val").item(0).getTextContent());

                    graduates.add(new Graduates(number, year));
                }
            }

            File jsonFile = pathJSON.toFile();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonFile);

            for (JsonNode node : root) {
                int year = Integer.parseInt(node.get("date").asText());
                float rate = (float) node.get("value").asDouble();

                inflations.add(new Inflation(rate, year));
            }

            init_database_with_data(graduates, inflations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init_database_with_data(List<Graduates> graduatesList, List<Inflation> inflationList) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Hibernate_JPA");
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        if(!graduatesList.isEmpty()) {
            for(Graduates grad : graduatesList) {
                em.persist(grad);
            }
        }
        if(!inflationList.isEmpty()) {
            for(Inflation infl : inflationList) {
                em.persist(infl);
            }
        }

        if(em.createQuery("SELECT u FROM User u", User.class).getResultList().isEmpty()) {
            Role roleAdmin, roleUser;

            if(em.createQuery("SELECT r FROM Role r", Role.class).getResultList().isEmpty()) {
                roleUser = new Role("User");
                roleAdmin = new Role("Admin");
                em.persist(roleUser);
                em.persist(roleAdmin);
            } else {
                roleUser = em.createQuery("SELECT r FROM Role r WHERE r.name='User'", Role.class).getSingleResult();
                roleAdmin = em.createQuery("SELECT r FROM Role r WHERE r.name='Admin'", Role.class).getSingleResult();
            }

            User user = new User("user", "user");
            user.addRole(roleUser);
            em.persist(user);

            User admin = new User("admin", "admin");
            admin.addRole(roleAdmin);
            em.persist(admin);
        }

        em.getTransaction().commit();
        em.close();
        factory.close();
    }
}