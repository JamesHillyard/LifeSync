package dev.james.lifesync.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LifeSyncUserTest {

    @Test
    public void testCreateUser() {
        LifeSyncUser user = new LifeSyncUser(1, "James", "Hillyard", "jhillyard", "password");

        assertEquals(1, user.getId());
        assertEquals("James", user.getFirstname());
        assertEquals("Hillyard", user.getLastname());
        assertEquals("jhillyard", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testDefaultConstructor() {
        LifeSyncUser user = new LifeSyncUser();

        assertEquals(0, user.getId());
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        LifeSyncUser user = new LifeSyncUser();
        user.setId(2);
        user.setFirstname("James");
        user.setLastname("Hillyard");
        user.setUsername("jhillyard");
        user.setPassword("newpassword");

        assertEquals(2, user.getId());
        assertEquals("James", user.getFirstname());
        assertEquals("Hillyard", user.getLastname());
        assertEquals("jhillyard", user.getUsername());
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testEqualsAndHashCode() {
        LifeSyncUser user1 = new LifeSyncUser(1, "James", "Hillyard", "jhillyard", "password");
        LifeSyncUser user2 = new LifeSyncUser(1, "James", "Hillyard", "jhillyard", "password");

        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testNotEquals() {
        LifeSyncUser user1 = new LifeSyncUser(1, "James", "Hillyard", "jhillyard", "password");
        LifeSyncUser user2 = new LifeSyncUser();

        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
