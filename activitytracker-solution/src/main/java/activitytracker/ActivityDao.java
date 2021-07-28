package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public void updateActivity(long id, String description) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Activity activity = em.find(Activity.class, id);
        em.getTransaction().begin();
        activity.setDescription(description);
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityByIdWithLabels(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();

        Activity activity = em.createQuery("select a from Activity a join fetch a.labels where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Activity activity = em.createQuery("select a from Activity a join fetch a.trackPoints where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

    public List<Coordinate> findTrackPointCoordinatesByDate(LocalDateTime afterThis, int start, int max) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Activity> activities = em.createNamedQuery("trackPointsAfterThisDate", Activity.class)
                .setParameter("startTime", afterThis)
                .setFirstResult(start)
                .setMaxResults(max)
                .getResultList();
        em.close();

        List<Coordinate> coordinates = getCoordinates(activities);
        return coordinates;
    }

    private List<Coordinate> getCoordinates(List<Activity> activities) {
        List<Coordinate> coordinates = new ArrayList<>();
        for(Activity a : activities) {
            for(TrackPoint t : a.getTrackPoints()) {
                coordinates.add(new Coordinate(t.getLat(), t.getLon()));
            }
        }
        return coordinates;
    }

    public List<Object[]> findTrackPointCountByActivity() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Object[]> activites = em.createQuery("select a.description, size(a.trackPoints) from Activity a order by a.startTime", Object[].class)
                .getResultList();
        em.close();
        return activites;
    }
}
