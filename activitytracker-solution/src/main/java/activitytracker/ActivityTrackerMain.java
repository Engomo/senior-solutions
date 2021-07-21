package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityTrackerMain {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");

        EntityManager em = factory.createEntityManager();

        insert(em);

        Activity activity = em.find(Activity.class, 2L);
        System.out.println(activity);

        em.getTransaction().begin();
        activity = em.find(Activity.class, 2L);
        activity.setDescription("Futás a tó körül");
        em.getTransaction().commit();

        System.out.println(activity);

        List<Activity> activities = em.createQuery("select a from Activity a", Activity.class)
                .getResultList();
        System.out.println(activities);

        em.getTransaction().begin();
        activity = em.find(Activity.class, 2L);
        em.remove(activity);
        em.getTransaction().commit();

        List<Activity> activitiesUpdated = em.createQuery("select a from Activity a", Activity.class)
                .getResultList();
        System.out.println(activitiesUpdated);

        em.close();
        factory.close();
    }

    private static void insert(EntityManager em) {
        em.getTransaction().begin();

        em.persist(new Activity(LocalDateTime.of(2021, 1, 21,11,02,35), "Biciklizés az Alföldön", ActivityType.BIKING));
        em.persist(new Activity(LocalDateTime.of(2021, 5, 21,11,02,35), "Futás az Alföldön", ActivityType.RUNNING));
        em.persist(new Activity(LocalDateTime.of(2021, 7, 21,11,02,35), "Kosárlabdázás a pályán", ActivityType.BASKETBALL));

        em.getTransaction().commit();


    }
}
