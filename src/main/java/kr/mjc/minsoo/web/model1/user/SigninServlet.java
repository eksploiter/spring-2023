package kr.mjc.minsoo.web.model1.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.mjc.minsoo.web.dao.User;
import kr.mjc.minsoo.web.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

/**
 * 로그인
 */
@WebServlet("/servlets/user/signin")
public class SigninServlet extends HttpServlet {

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
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    try {
      User user = userDao.login(email, password);
      // 로그인 성공
      HttpSession session = req.getSession();
      session.setAttribute("me_userId", user.getUserId());
      session.setAttribute("me_name", user.getName());
      session.setAttribute("me_email", user.getEmail());
      resp.sendRedirect(
          req.getContextPath() + "/servlets/user/userList?count=20&page=1");
    } catch (DataAccessException e) {
      // 로그인 실패
      resp.sendRedirect(req.getContextPath() + "/servlets/user/signinForm?mode=FAILURE");
    }
  }
}