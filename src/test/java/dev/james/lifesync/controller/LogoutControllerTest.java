package dev.james.lifesync.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LogoutController.class)
public class LogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRemoveUserSessionRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hlsp/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    public void testRemoveUserSessionComplete() throws Exception {
        // Set up the session with a "user" attribute
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "dummyUser");

        // Verify the session attribute exists
        assertNotNull(session.getAttribute("user"));

        // Perform the logout request
        mockMvc.perform(MockMvcRequestBuilders.get("/hlsp/logout").session(session))
                .andExpect(status().is3xxRedirection());

        // Verify the "user" session attribute no longer exists
        assertNull(session.getAttribute("user"));
    }
}
