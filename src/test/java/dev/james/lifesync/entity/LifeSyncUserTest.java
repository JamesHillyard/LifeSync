package dev.james.lifesync.entity;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LifeSyncUserTest {

    @Test
    public void testCreateUser() {
        LifeSyncUser user = new LifeSyncUser(1, "James", "Hillyard", "james.hillyard@payara.fish", "password");

        assertEquals(1, user.getId());
        assertEquals("James", user.getFirstname());
        assertEquals("Hillyard", user.getLastname());
        assertEquals("james.hillyard@payara.fish", user.getEmail());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testDefaultConstructor() {
        LifeSyncUser user = new LifeSyncUser();

        assertEquals(0, user.getId());
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        LifeSyncUser user = new LifeSyncUser();
        user.setId(2);
        user.setFirstname("James");
        user.setLastname("Hillyard");
        user.setEmail("james.hillyard@payara.fish");
        user.setPassword("newpassword");

        assertEquals(2, user.getId());
        assertEquals("James", user.getFirstname());
        assertEquals("Hillyard", user.getLastname());
        assertEquals("james.hillyard@payara.fish", user.getEmail());
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testEqualsAndHashCode() {
        LifeSyncUser user1 = new LifeSyncUser(1, "James", "Hillyard", "james.hillyard@payara.fish", "password");
        LifeSyncUser user2 = new LifeSyncUser(1, "James", "Hillyard", "james.hillyard@payara.fish", "password");

        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testNotEquals() {
        LifeSyncUser user1 = new LifeSyncUser(1, "James", "Hillyard", "james.hillyard@payara.fish", "password");
        LifeSyncUser user2 = new LifeSyncUser();

        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testEqualsOnSameObject() {
        LifeSyncUser user = new LifeSyncUser(1, "James", "Hillyard", "james.hillyard@payara.fish", "password");

        assertTrue(user.equals(user));
        assertEquals(user.hashCode(), user.hashCode());
    }

    @Test
    public void testEqualsWithDifferentClass() {
        LifeSyncUser user = new LifeSyncUser(1, "James", "Hillyard", "james.hillyard@payara.fish", "password");
        String string = "Hello";

        assertFalse(user.equals(string));
    }

    @Test
    public void testExerciseData() {
        LifeSyncUser user = new LifeSyncUser();
        assertNull(user.getExerciseData());

        List<ExerciseData> exerciseDataList = List.of(
                new ExerciseData(),
                new ExerciseData(Date.valueOf(LocalDate.now()), 150, 235)
        );
        user.setExerciseData(exerciseDataList);

        assertEquals(2, user.getExerciseData().size());
        assertEquals(235, user.getExerciseData().get(1).getCaloriesBurnt());
    }

    @Test
    public void testSleepData() {
        LifeSyncUser user = new LifeSyncUser();
        assertNull(user.getSleepData());

        List<SleepData> sleepDataList = List.of(
                new SleepData(),
                new SleepData(-1, Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plusSeconds(1)))
        );
        user.setSleepData(sleepDataList);

        assertEquals(2, user.getSleepData().size());
        assertTrue(user.getSleepData().get(1).getStarttime().before(user.getSleepData().get(1).getEndtime()));
    }

    @Test
    public void testNutritionData() {
        LifeSyncUser user = new LifeSyncUser();
        assertNull(user.getNutritionData());

        List<NutritionData> nutritionDataList = List.of(
                new NutritionData(),
                new NutritionData(Date.valueOf(LocalDate.now()), 10, 15, 3.5F)
        );
        user.setNutritionData(nutritionDataList);

        assertEquals(2, user.getNutritionData().size());
        assertEquals(3.5, user.getNutritionData().get(1).getSugar());
        assertEquals(10, user.getNutritionData().get(1).getCalories());
    }
}
