package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ActivityTrackerMain {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");

        EntityManager em = factory.createEntityManager();

        insert(factory, em);
    }

    private static void insert(EntityManagerFactory factory, EntityManager em) {
        em.getTransaction().begin();

        em.persist(new Activity(LocalDateTime.of(2021, 01, 21,11,02,35), "Biciklizés az Alföldön", ActivityType.BIKING));
        em.persist(new Activity(LocalDateTime.of(2021, 05, 21,11,02,35), "Futás az Alföldön", ActivityType.RUNNING));
        em.persist(new Activity(LocalDateTime.of(2021, 07, 21,11,02,35), "Kosárlabdázás a pályán", ActivityType.BASKETBALL));

        em.getTransaction().commit();
        em.close();
        factory.close();
    }
}
