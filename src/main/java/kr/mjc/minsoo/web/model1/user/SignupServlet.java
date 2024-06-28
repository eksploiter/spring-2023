package kr.mjc.minsoo.web.model1.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.mjc.minsoo.web.dao.User;
import kr.mjc.minsoo.web.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

/**
 * 회원 등록
 */
@WebServlet("/servlets/user/signup")
@Slf4j
public class SignupServlet extends HttpServlet {

  private UserDao userDao;

  @Override
  public void init() {
    // for standalone container
    ApplicationContext applicationContext =
        WebApplicationContextUtils.getRequiredWebApplicationContext(
            getServletContext());
    userDao = applicationContext.getBean(UserDao.class);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    User user = new User();
    user.setEmail(req.getParameter("email"));
    user.setPassword(req.getParameter("password"));
    user.setName(req.getParameter("name"));

    try {
      userDao.addUser(user);
      // 등록 성공
      HttpSession session = req.getSession();
      session.setAttribute("me.userId", user.getUserId());
      session.setAttribute("me.name", user.getName());
      session.setAttribute("me.email", user.getEmail());
      resp.sendRedirect(
          req.getContextPath() + "/servlets/user/userList?count=20&page=1");
    } catch (DataAccessException e) { // 등록 실패
      log.error(e.getCause().toString());
      resp.sendRedirect(req.getContextPath() + "/servlets/user/signupForm?mode=FAILURE");
    }
  }
}