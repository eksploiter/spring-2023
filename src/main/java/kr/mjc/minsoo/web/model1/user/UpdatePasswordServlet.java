package kr.mjc.minsoo.web.model1.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.mjc.minsoo.web.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

/**
 * 비빌번호 변경
 */
@WebServlet("/servlets/user/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {

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
    int userId = (int) req.getSession().getAttribute("me.userId");
    String currentPassword = req.getParameter("currentPassword");
    String newPassword = req.getParameter("newPassword");

    int updatedRows =
        userDao.updatePassword(userId, currentPassword, newPassword);
    if (updatedRows >= 1) // 업데이트 성공
      resp.sendRedirect(req.getContextPath() + "/servlets/user/myInfo");
    else  // 업데이트 실패
      resp.sendRedirect(req.getContextPath() + "/servlets/user/passwordEdit?mode=FAILURE");
  }
}