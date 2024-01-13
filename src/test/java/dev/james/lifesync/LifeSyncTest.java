package dev.james.lifesync;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LifeSyncTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testMain() {
        // If the application context is not null, the app started
        assertNotNull(applicationContext);

        LifeSync.main(new String[]{});
    }
}