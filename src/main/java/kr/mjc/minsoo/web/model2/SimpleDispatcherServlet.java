package kr.mjc.minsoo.web.model2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

@WebServlet("/mvc/*")
@Slf4j
public class SimpleDispatcherServlet extends HttpServlet {

  private UserController userController;

  @Override
  public void init() {
    ApplicationContext applicationContext =
        WebApplicationContextUtils.getRequiredWebApplicationContext(
            getServletContext());
    userController = applicationContext.getBean(UserController.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String pathInfo = req.getPathInfo();
    log.debug("pathInfo={}", pathInfo);
    switch (pathInfo) {
      case "/user/userList" -> userController.userList(req, resp);
      case "/user/signout" -> userController.signout(req, resp);
      case "/user/signinForm", "/user/signupForm", "/user/myInfo", "/user/passwordEdit" ->
          userController.mapDefault(req, resp);
      default -> resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String pathInfo = req.getPathInfo();
    log.debug("pathInfo={}", pathInfo);
    switch (pathInfo) {
      case "/user/signin" -> userController.signin(req, resp);
      case "/user/signup" -> userController.signup(req, resp);
      case "/user/updatePassword" -> userController.updatePassword(req, resp);
    }
  }
}



//count=20&page=1 없어도 된디. jsp 의 단일화
//http://localhost:8080/web2023/servlets/user/userList?