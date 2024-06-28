package kr.mjc.minsoo.web.model1.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.mjc.minsoo.web.dao.Limit;
import kr.mjc.minsoo.web.dao.User;
import kr.mjc.minsoo.web.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("/servlets/user/userList")
@Slf4j
public class  UserListServlet extends HttpServlet {

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
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Limit limit =
        new Limit(req.getParameter("count"), req.getParameter("page"));
    req.setAttribute("limit", limit);
    List<User> userList = userDao.listUsers(limit);
    req.setAttribute("userList", userList);
    req.getRequestDispatcher("/WEB-INF/jsp/user/userList.jsp")
        .forward(req, resp);
  }
}