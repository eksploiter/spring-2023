package kr.mjc.minsoo.web.examples;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.mjc.minsoo.web.dao.Limit;
import kr.mjc.minsoo.web.dao.User;
import kr.mjc.minsoo.web.dao.UserDao;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;

@WebServlet("/examples/userList")
public class UserListServlet extends HttpServlet {

  private UserDao userDao;

  @Override
  public void init() {
    // for standalone container
    ApplicationContext applicationContext = (ApplicationContext)
        getServletContext().getAttribute(
            "org.springframework.web.context.WebApplicationContext.ROOT");
    userDao = applicationContext.getBean(UserDao.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    List<User> userList = userDao.listUsers(new Limit());

    String html = """
        <!DOCTYPE html>
        <html>
        <body>
        <pre>%s</pre>
        </body>
        </html>
        """.formatted(userList);

    resp.setContentType("text/html");
    resp.getWriter().println(html);
  }
}