package pl.sda.jpa.starter.basic;

import javax.persistence.*;
import javax.swing.text.html.parser.Entity;
import java.util.List;

public class JpaBasic {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            /**
             * Tworzymy nowy obiekt EntityManagerFactory z konfiguracją Persistence Unit o nazwie: "pl.sda.jpa.starter"
             */
            entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
            /**
              * tworzymy nową instancję EntityManager
              */
            entityManager = entityManagerFactory.createEntityManager();

            /**
             * Do pracy z bazą danych potrzebujemy transakcji
             */
            EntityTransaction transaction = entityManager.getTransaction();
            /**
             * Zaczynamy nową transakcję, każda operacja na bazie danych musi być "otoczona" transakcją
             */
            transaction.begin();

            /**
             * Zapisujemy encję w bazie danych
             */
            CoachEntity coachEntity = new CoachEntity("Vlad Mihalcea");
            CoachEntity coachEntity1 = new CoachEntity("Vlad Mihalcea2");
            CoachEntity coachEntity2 = new CoachEntity("Vlad Mihalcea3");
            CoachEntity coachEntity3 = new CoachEntity("Vlad Mihalcea4");
            CoachEntity coachEntity4= new CoachEntity("Vlad Mihalcea5");
//            entityManager.persist(coachEntity1);
//            entityManager.persist(coachEntity2);
//            entityManager.persist(coachEntity3);
//            entityManager.persist(coachEntity4);
//            CoachEntity em = entityManager.find(CoachEntity.class, 4);
//            entityManager.remove(em);

            StudentEntity studentEntity = new StudentEntity("Arek","2011-11-05");
            StudentEntity studentEntity2 = new StudentEntity("Tomek","2012-04-22");
            StudentEntity studentEntity3 = new StudentEntity("Ola","2013-08-11");
            entityManager.persist(studentEntity);
            entityManager.persist(studentEntity2);
            entityManager.persist(studentEntity3);
//            StudentEntity stu = entityManager.find(StudentEntity.class,2);
//            entityManager.remove(stu);


            /**
             * Wyciągamy wszystkie encje zapisane w bazie danych
             */
            TypedQuery<CoachEntity> query = entityManager.createQuery("from CoachEntity", CoachEntity.class);
            List<CoachEntity> coaches = query.getResultList();
            System.out.println("coaches = " + coaches);

            TypedQuery<StudentEntity> query2 = entityManager.createQuery("from StudentEntity", StudentEntity.class);
            List<StudentEntity> students = query2.getResultList();
            System.out.println("students = " + students);

            /**
             * Kończymy (commitujemy) transakcję - wszystkie dane powinny być zapisane w bazie
             */
            transaction.commit();
        } finally {
            /**
             * Kończymy pracę z entityManager, zamykamy go i tym samym zamykamy Persistence Context z nim związany
             * Czemu EntityManager nie implementuje AutoClosable? https://github.com/javaee/jpa-spec/issues/77
             */

            if (entityManager != null) {
                entityManager.close();
                entityManagerFactory.close();
            }
        }
    }
}
