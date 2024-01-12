package dev.james.lifesync.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SleepDataTest {

    @Test
    public void testCreateSleepData() {
        long baselineTime = System.currentTimeMillis();

        SleepData sleepData = new SleepData(2, new Timestamp(baselineTime), new Timestamp(baselineTime + 3600000));
        assertNotNull(sleepData);

        assertEquals(2, sleepData.getUserid());
        assertEquals(new Timestamp(baselineTime), sleepData.getStarttime());
        assertEquals(new Timestamp(baselineTime + 3600000), sleepData.getEndtime());
    }

    @Test
    public void testNoArgsConstructor() {
        SleepData sleepData = new SleepData();
        assertNotNull(sleepData);

        assertEquals(0, sleepData.getSleepid());
        assertEquals(0, sleepData.getUserid());
        assertNull(sleepData.getStarttime());
        assertNull(sleepData.getEndtime());
    }

    @Test
    public void testGettersAndSetters() {
        SleepData sleepData = new SleepData();
        sleepData.setSleepid(1);
        sleepData.setUserid(2);
        sleepData.setStarttime(new Timestamp(System.currentTimeMillis()));
        sleepData.setEndtime(new Timestamp(System.currentTimeMillis() + 3600000));

        assertEquals(1, sleepData.getSleepid());
        assertEquals(2, sleepData.getUserid());
        assertNotNull(sleepData.getStarttime());
        assertNotNull(sleepData.getEndtime());
    }

    @Test
    public void testEqualsAndHashCode() {
        SleepData sleepData1 = new SleepData(2, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));
        SleepData sleepData2 = new SleepData(2, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));

        assertEquals(sleepData1, sleepData2);
        assertTrue(sleepData2.equals(sleepData1));
        assertTrue(sleepData1.equals(sleepData2));
        assertEquals(sleepData1.hashCode(), sleepData2.hashCode());
    }

    @Test
    public void testNotEquals() {
        SleepData sleepData1 = new SleepData(2, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));
        SleepData sleepData2 = new SleepData(4, new Timestamp(System.currentTimeMillis() - 3600000), new Timestamp(System.currentTimeMillis()));

        assertNotEquals(sleepData1, sleepData2);
        assertFalse(sleepData1.equals(sleepData2));
        assertFalse(sleepData2.equals(sleepData1));
        assertNotEquals(sleepData1.hashCode(), sleepData2.hashCode());
    }

    @Test
    public void testNotEqualsWithNull() {
        SleepData sleepData1 = null;
        SleepData sleepData2 = new SleepData(4, new Timestamp(System.currentTimeMillis() - 3600000), new Timestamp(System.currentTimeMillis()));

        assertNotEquals(sleepData1, sleepData2);
        assertFalse(sleepData2.equals(sleepData1));
    }

    @Test
    public void testEqualsOnSameObject() {
        SleepData sleepData = new SleepData(4, new Timestamp(System.currentTimeMillis() - 3600000), new Timestamp(System.currentTimeMillis()));
        assertTrue(sleepData.equals(sleepData));
    }

    @Test
    public void testGetDurationInHoursAndMinutesSameDay() {
        SleepData sleepData = new SleepData();
        sleepData.setStarttime(Timestamp.valueOf(LocalDateTime.of(2023, 10, 28, 2, 30)));
        sleepData.setEndtime(Timestamp.valueOf(LocalDateTime.of(2023, 10, 28, 8, 0)));

        assertEquals(5.5, sleepData.getDurationInHoursAndMinutes());
        assertEquals("5 Hours 30 Minutes", sleepData.getDurationInHoursAndMinutesHumanReadable());
    }

    @Test
    public void testGetDurationInHoursAndMinutesDifferentDay() {
        SleepData sleepData = new SleepData();
        sleepData.setStarttime(Timestamp.valueOf(LocalDateTime.of(2023, 10, 27, 23, 45)));
        sleepData.setEndtime(Timestamp.valueOf(LocalDateTime.of(2023, 10, 28, 8, 0)));

        assertEquals(8.25, sleepData.getDurationInHoursAndMinutes());
        assertEquals("8 Hours 15 Minutes", sleepData.getDurationInHoursAndMinutesHumanReadable());
    }

    @Test
    public void testGetDurationInHoursAndMinutesNullValues() {
        SleepData sleepData = new SleepData();

        assertEquals(0.0, sleepData.getDurationInHoursAndMinutes());
        assertNull(sleepData.getDurationInHoursAndMinutesHumanReadable());
    }

}