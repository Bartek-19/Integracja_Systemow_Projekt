package com.lg;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

//  Kod przeznaczony do skopiowania do aplikacji serwerowej
//
//  Aby program działał prawidłowo należy oprócz kodu i klas
//  dodać dodatkowo folder META-INF z folderu resources - w
//  pliku persistence.xml znajdują się informacje odnośnie
//  docelowej lokalizacji bazy danych, które trzeba będzie
//  zmodyfikować w wersji implementacyjnej. Dodatkowo, w
//  pliku pom.xml znajdują się zależności odpowiadające za
//  rozszerzenia konieczne do działania programu.
//
//  UWAGA - Program nie był testowany
//

public class Main {
    public static void main(String[] args) {
        //
    }

    public void init_database_with_data(List<Graduates> graduatesList, List<Inflation> inflationList) {
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
        if(em.createQuery("SELECT u FROM Users u").getResultList().isEmpty()) {
            Role roleAdmin, roleUser;
            if(em.createQuery("SELECT r FROM Roles r").getResultList().isEmpty()) {
                roleAdmin = new Role("Admin");
                roleUser = new Role("User");
            } else {
                roleAdmin = (Role) em.createQuery("SELECT r FROM Roles r WHERE r.name='Admin'").getSingleResult();
                roleUser = (Role) em.createQuery("SELECT r FROM Roles r WHERE r.name='User'").getSingleResult();
            }
            User user = new User("user", "user");
            user.addRole(roleUser);
            User admin = new User("admin", "admin");
            admin.addRole(roleUser);
            admin.addRole(roleAdmin);
        }
    }
}