package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void testFindTrackPointCoordinatesByDate() {
        Activity activity1 = new Activity(LocalDateTime.of(2021,7,28,8,50), "Reggeli kosár", ActivityType.BASKETBALL);
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021,7,28), 15.15,15.15));
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021,7,28), 15.14,15.15));
//        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021,7,28), 15.15,15.16));

        activityDao.saveActivity(activity1);

        Activity activity2 = new Activity(LocalDateTime.of(2020,7,28,8,50), "Reggeli kosár", ActivityType.BASKETBALL);
        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2020,7,28), 15.15,15.15));
        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2020,7,28), 15.14,15.15));
        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2020,7,28), 15.15,15.16));

        activityDao.saveActivity(activity2);

        List<Coordinate> coordinates = activityDao.findTrackPointCoordinatesByDate(LocalDateTime.of(2021,1,1, 12, 0), 0, 20);

        assertEquals(2, coordinates.size());
    }

    @Test
    void testFindTrackPointCountByActivity() {
        Activity activity1 = new Activity(LocalDateTime.of(2019,7,28,8,50), "Reggeli kosár", ActivityType.BASKETBALL);
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021,7,28), 15.15,15.15));
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021,7,28), 15.14,15.15));
//        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021,7,28), 15.15,15.16));

        activityDao.saveActivity(activity1);

        Activity activity2 = new Activity(LocalDateTime.of(2019,8,28,8,50), "Reggeli kosár", ActivityType.BASKETBALL);
        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2020,7,28), 15.15,15.15));
        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2020,7,28), 15.14,15.15));
        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2020,7,28), 15.15,15.16));

        activityDao.saveActivity(activity2);

        List<Object[]> result = activityDao.findTrackPointCountByActivity();

        assertEquals(2, result.get(0)[1]);
        assertEquals(3, result.get(1)[1]);
    }
}
