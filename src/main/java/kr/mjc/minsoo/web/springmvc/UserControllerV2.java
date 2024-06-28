package kr.mjc.minsoo.web.springmvc;

import jakarta.servlet.http.HttpSession;
import kr.mjc.minsoo.web.HttpUtils;
import kr.mjc.minsoo.web.dao.Limit;
import kr.mjc.minsoo.web.dao.User;
import kr.mjc.minsoo.web.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * Servlet API를 사용하지 않는 컨트롤러
 */
@Controller
@AllArgsConstructor
@Slf4j
public class UserControllerV2 {

  private final UserDao userDao;

  /**
   * just forward
   */
  @GetMapping({"/user/signinForm", "/user/signupForm", "/user/myInfo",
          "/user/passwordEdit"})
  public void mapDefault() {
    // do nothing
  }

  /**
   * 회원목록
   */
  @GetMapping("/user/userList")
  public void userList(Limit limit, Model model) {
    model.addAttribute("userList", userDao.listUsers(limit));
  }

  /**
   * 회원정보
   */
  @GetMapping("/user/userInfo")
  public void userInfo(int userId, Model model) {
    model.addAttribute("user", userDao.getUser(userId));
  }

  /**
   * 회원가입
   */
  @PostMapping("/user/signup")
  public String signup(User user, String redirectUrl, HttpSession session) {
    try {
      userDao.addUser(user);
      // 등록 성공
      return signin(user.getEmail(), user.getPassword(), "", session);
    } catch (DataAccessException e) { // 등록 실패
      log.error(e.getCause().toString());
      return "redirect:/app/user/signupForm?mode=FAILURE";
    }
  }

  /**
   * 로그인
   */
  @PostMapping("/user/signin")
  public String signin(String email, String password, String redirectUrl,
                       HttpSession session) {
    redirectUrl = StringUtils.defaultIfEmpty(redirectUrl, "/app/user/userList");
    log.debug("redirectUrl=" + redirectUrl);
    try {
      User user = userDao.login(email, password);
      // 로그인 성공
      session.setAttribute("me_userId", user.getUserId());
      session.setAttribute("me_name", user.getName());
      session.setAttribute("me_email", user.getEmail());
      return "redirect:" + redirectUrl;
    } catch (DataAccessException e) {// 로그인 실패
      return "redirect:/app/user/signinForm?mode=FAILURE&redirectUrl=" +
              HttpUtils.encodeUrl(redirectUrl);
    }
  }

  /**
   * 비밀번호변경
   */
  @PostMapping("/user/updatePassword")
  public String updatePassword(@SessionAttribute("me_userId") int userId,
                               String currentPassword, String newPassword) {
    int updatedRows =
            userDao.updatePassword(userId, currentPassword, newPassword);
    if (updatedRows >= 1) // 업데이트 성공
      return "redirect:/app/user/myInfo";
    else  // 업데이트 실패
      return "redirect:/app/user/passwordEdit?mode=FAILURE";
  }

  /**
   * 로그아웃
   */
  @GetMapping("/user/signout")
  public String signout(HttpSession session) {
    session.invalidate();
    return "redirect:/app/user/userList";
  }
}