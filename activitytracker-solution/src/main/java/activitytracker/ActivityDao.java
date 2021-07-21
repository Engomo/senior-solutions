package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ActivityDao {

    private EntityManagerFactory entityManagerFactory;

    public ActivityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveActivity(Activity activity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(activity);
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(Activity.class, id);
    }

    public List<Activity> listActivities() {
        EntityManager em = entityManagerFactory.createEntityManager();

        return em.createQuery("select a from Activity a", Activity.class)
                .getResultList();
    }

    public void deleteActivity(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        Activity activity = em.find(Activity.class, id);
        em.remove(activity);
        em.getTransaction().commit();
        em.close();
    }
}
