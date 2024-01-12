package dev.james.lifesync.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;


@SpringBootTest
public class LogoutControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
    }

//    @Test
//    public void testLogoutServlet() throws IOException {
//        when(request.getSession()).thenReturn(session);
//        when(session.getAttribute("user")).thenReturn(new LifeSyncUser(-1, "test", "test", "test", "test"));
//
//        // Inject the mock objects into the servlet
//        LogoutServlet logoutServlet = new LogoutServlet();
//        logoutServlet.doGet(request, response);
//
//        // Verify that the session.invalidate() method was called
//        verify(session).invalidate();
//    }

}
