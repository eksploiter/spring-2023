package kr.mjc.minsoo.web.model1.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 그냥 forward
 */
@WebServlet({"/servlets/user/signinForm", "/servlets/user/signupForm",
    "/servlets/user/myInfo", "/servlets/user/passwordEdit"})
@Slf4j
public class JustForwardServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String path = req.getServletPath().replace("/servlets/", "/");
    log.debug("path={}", path);
    req.getRequestDispatcher("/WEB-INF/jsp%s.jsp".formatted(path))
        .forward(req, resp);
  }
}