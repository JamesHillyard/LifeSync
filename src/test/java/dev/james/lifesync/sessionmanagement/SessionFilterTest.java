package dev.james.lifesync.sessionmanagement;

import dev.james.lifesync.model.LifeSyncUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SessionFilterTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(new SessionFilter()).build();
    }

    @Test
    public void testWithUserSession_tryAccessDashboard() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new LifeSyncUser());

        // Mock the absence of HttpSession by setting it to null
        mockMvc.perform(get("/dashboard.jsp")
                        .session(session))
                        .andExpect(status().isOk());
    }

    @Test
    public void testWithUserSession_tryAccessLogin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new LifeSyncUser());

        // Mock the absence of HttpSession by setting it to null
        mockMvc.perform(get("/login.jsp")
                        .session(session))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoUserSession_tryAccessDashboard() throws Exception {
        mockMvc.perform(get("/dashboard.jsp"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testNoUserSession_tryAccessLogin() throws Exception {
        mockMvc.perform(get("/login.jsp"))
                .andExpect(status().isOk());
    }

    @Test
    public void testWithInvalidUserSession_tryAccessLogin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("KeyIsNotUser", new LifeSyncUser());

        // Mock the absence of HttpSession by setting it to null
        mockMvc.perform(get("/login.jsp")
                        .session(session))
                .andExpect(status().isOk());
    }

    @Test
    public void testWithInvalidUserSession_tryAccessDashboard() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("KeyIsNotUser", new LifeSyncUser());

        // Mock the absence of HttpSession by setting it to null
        mockMvc.perform(get("/dashboard.jsp")
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }
}