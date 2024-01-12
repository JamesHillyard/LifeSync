package dev.james.lifesync.entity;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExerciseDataTest {

    @Test
    public void testCreateExerciseData() {
        ExerciseData exerciseData = new ExerciseData(-1, "Running", Date.valueOf(LocalDate.now()), 30, 400);

        assertEquals(0, exerciseData.getId());
        assertEquals(-1, exerciseData.getUserid());
        assertEquals("Running", exerciseData.getActivityName());
        assertEquals(Date.valueOf(LocalDate.now()), exerciseData.getDate());
        assertEquals(30, exerciseData.getDuration());
        assertEquals(400, exerciseData.getCaloriesBurnt());
    }

    @Test
    public void testMinimalExerciseDataConstructor() {
        ExerciseData exerciseData = new ExerciseData(Date.valueOf(LocalDate.now()), 30, 400);

        assertEquals(0, exerciseData.getId());
        assertEquals(0, exerciseData.getUserid());
        assertEquals("", exerciseData.getActivityName());
        assertEquals(Date.valueOf(LocalDate.now()), exerciseData.getDate());
        assertEquals(30, exerciseData.getDuration());
        assertEquals(400, exerciseData.getCaloriesBurnt());
    }

    @Test
    public void testNoArgsConstructor() {
        ExerciseData exerciseData = new ExerciseData();

        assertEquals(0, exerciseData.getId());
        assertEquals(0, exerciseData.getUserid());
        assertEquals("", exerciseData.getActivityName());
        assertNull(exerciseData.getDate());
        assertEquals(0, exerciseData.getDuration());
        assertEquals(0, exerciseData.getCaloriesBurnt());
    }

    @Test
    public void testGettersAndSetters() {
        ExerciseData exerciseData = new ExerciseData();
        exerciseData.setId(1);
        exerciseData.setUserid(2);
        exerciseData.setActivityName("skiing");
        exerciseData.setDate(Date.valueOf(LocalDate.now()));
        exerciseData.setDuration(180);
        exerciseData.setCaloriesBurnt(1500);

        assertEquals(1, exerciseData.getId());
        assertEquals(2, exerciseData.getUserid());
        assertEquals("Skiing", exerciseData.getActivityName());
        assertEquals(Date.valueOf(LocalDate.now()), exerciseData.getDate());
        assertEquals(180, exerciseData.getDuration());
        assertEquals(1500, exerciseData.getCaloriesBurnt());
    }

    @Test
    void testEqualsAndHashCode() {
        ExerciseData exerciseData1 = new ExerciseData(Date.valueOf(LocalDate.now()), 30, 400);
        ExerciseData exerciseData2 = new ExerciseData(Date.valueOf(LocalDate.now()), 30, 400);

        assertEquals(exerciseData1, exerciseData2);
        assertTrue(exerciseData2.equals(exerciseData1));
        assertTrue(exerciseData1.equals(exerciseData2));
        assertEquals(exerciseData1.hashCode(), exerciseData2.hashCode());
    }

    @Test
    public void testNotEquals() {
        ExerciseData exerciseData1 = new ExerciseData(Date.valueOf(LocalDate.now()), 30, 401);
        ExerciseData exerciseData2 = new ExerciseData(Date.valueOf(LocalDate.now()), 30, 400);

        assertNotEquals(exerciseData1, exerciseData2);
        assertFalse(exerciseData2.equals(exerciseData1));
        assertFalse(exerciseData1.equals(exerciseData2));
        assertNotEquals(exerciseData1.hashCode(), exerciseData2.hashCode());
    }

    @Test
    public void testNotEqualsWithNull() {
        ExerciseData exerciseData1 = null;
        ExerciseData exerciseData2 = new ExerciseData(Date.valueOf(LocalDate.now()), 30, 400);

        assertNotEquals(exerciseData1, exerciseData2);
        assertFalse(exerciseData2.equals(exerciseData1));
    }

    @Test
    public void testNotEqualsWithDifferentClass() {
        String text = "Hello";
        ExerciseData exerciseData2 = new ExerciseData(Date.valueOf(LocalDate.now()), 30, 400);

        assertNotEquals(text, exerciseData2);
        assertFalse(exerciseData2.equals(text));

    }

    @Test
    public void testEqualsOnSameObject() {
        ExerciseData exerciseData = new ExerciseData(Date.valueOf(LocalDate.now()), 30, 400);

        assertTrue(exerciseData.equals(exerciseData));
    }

    @Test
    public void testGetActivityNameCapitalisesFirstLetter() {
        ExerciseData exerciseData = new ExerciseData();

        // Test lowercase
        exerciseData.setActivityName("walking");
        assertEquals("Walking", exerciseData.getActivityName());

        // Test already capitalised
        exerciseData.setActivityName("Running");
        assertEquals("Running", exerciseData.getActivityName());

        // Test all caps
        exerciseData.setActivityName("SKIING");
        assertEquals("Skiing", exerciseData.getActivityName());

        // Test all caps first letter lowercase
        exerciseData.setActivityName("cANOEING");
        assertEquals("Canoeing", exerciseData.getActivityName());
    }

    @Test
    public void getDurationInHoursAndMinutes() {
        ExerciseData exerciseData = new ExerciseData();

        exerciseData.setDuration(183);
        assertEquals(3.05, exerciseData.getDurationInHoursAndMinutes());

        exerciseData.setDuration(60);
        assertEquals(1.0, exerciseData.getDurationInHoursAndMinutes());
    }

    @Test
    public void getDurationInHoursAndMinutesOnEmptyObject() {
        ExerciseData exerciseData = new ExerciseData();

        assertEquals(0.0, exerciseData.getDurationInHoursAndMinutes());
    }

    @Test
    public void getDurationInHoursAndMinutesHumanReadable() {
        ExerciseData exerciseData = new ExerciseData();

        exerciseData.setDuration(185);
        assertEquals("3 Hours 5 Minutes", exerciseData.getDurationInHoursAndMinutesHumanReadable());

        exerciseData.setDuration(60);
        assertEquals("1 Hours 0 Minutes", exerciseData.getDurationInHoursAndMinutesHumanReadable());

        exerciseData.setDuration(46);
        assertEquals("0 Hours 46 Minutes", exerciseData.getDurationInHoursAndMinutesHumanReadable());
    }

    @Test
    public void getDurationInHoursAndMinutesHumanReadableOnEmptyObject() {
        ExerciseData exerciseData = new ExerciseData();
        assertNull(exerciseData.getDurationInHoursAndMinutesHumanReadable());
    }
}