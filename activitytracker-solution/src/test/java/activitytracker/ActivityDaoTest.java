package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityDaoTest {

    private ActivityDao activityDao;

    @BeforeEach
    void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(factory);

        Activity activity1 = new Activity(LocalDateTime.of(2021, 1, 21,11,2,35), "Biciklizés az Alföldön", ActivityType.BIKING);
        Activity activity2 = new Activity(LocalDateTime.of(2021, 5, 21,11,2,35), "Futás az Alföldön", ActivityType.RUNNING);
        Activity activity3 = new Activity(LocalDateTime.of(2021, 7, 21,11,2,35), "Kosárlabdázás a pályán", ActivityType.BASKETBALL);

        activityDao.saveActivity(activity1);
        activityDao.saveActivity(activity2);
        activityDao.saveActivity(activity3);
    }

    @Test
    public void testSaveThenFindId() {
        assertEquals(ActivityType.RUNNING, activityDao.findActivityById(2L).getType());
    }

    @Test
    public void testListActivities() {
        assertEquals(3, activityDao.listActivities().size());
    }

    @Test
    public void testDeleteActivity() {
        activityDao.deleteActivity(2L);
        assertEquals(2, activityDao.listActivities().size());
    }

    @Test
    public void testUpdateActivity() {
        activityDao.updateActivity(1L, "Biciklizés a homokban");
        assertEquals("Biciklizés a homokban", activityDao.findActivityById(1L).getDescription());
    }

    @Test
    public void testFindActivityByIdWithLabels() {
        Activity activity = new Activity(LocalDateTime.of(2021, 7, 23, 15, 15), "Kosár a haverokkal", ActivityType.BASKETBALL);
        activity.setLabels(Arrays.asList("1-1", "volt sör"));
        activityDao.saveActivity(activity);

        assertEquals(2, activityDao.findActivityByIdWithLabels(activity.getId()).getLabels().size());
    }

    @Test
    void testFindActivityByIdWithTrackPoints() {
        Activity activity = new Activity(LocalDateTime.of(2021, 7, 23, 15, 15), "Kosár a haverokkal", ActivityType.BASKETBALL);

        activity.addTrackPoint(new TrackPoint(LocalDate.of(2021, 7, 26), 15.15, 25.25));
        activity.addTrackPoint(new TrackPoint(LocalDate.of(2021, 7, 26), 15.55, 25.25));
        activity.addTrackPoint(new TrackPoint(LocalDate.of(2021, 7, 26), 15.95, 25.25));

        activityDao.saveActivity(activity);

        assertEquals(3, activityDao.findActivityByIdWithTrackPoints(activity.getId()).getTrackPoints().size());
    }
}
