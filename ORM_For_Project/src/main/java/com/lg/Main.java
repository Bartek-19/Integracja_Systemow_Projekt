package com.lg;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        List<Graduates> graduates = new ArrayList<>();
        List<Inflation> inflations = new ArrayList<>();

        graduates.add(new Graduates(2000, 2024));
        graduates.add(new Graduates(2100, 2023));
        graduates.add(new Graduates(2300, 2022));

        inflations.add(new Inflation(8.7F, 2024));
        inflations.add(new Inflation(6.5F, 2023));
        inflations.add(new Inflation(18.4F, 2022));

        init_database_with_data(graduates, inflations);
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