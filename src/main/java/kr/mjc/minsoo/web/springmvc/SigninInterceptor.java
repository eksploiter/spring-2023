package kr.mjc.minsoo.web.springmvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.mjc.minsoo.web.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 로그인이 필요한 요청을 먼저 처리하는 인터셉터
 */
@Slf4j
public class SigninInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
      Object handler) throws Exception {
    HttpSession session = req.getSession();
    Object userId = session.getAttribute("me_userId");
    if (userId != null) // 로그인 한 경우 그대로 진행
      return true;

    // 로그인 안한 경우 로그인 화면으로
    String redirectUrl = HttpUtils.getRequestURLWithQueryString(req);
    log.debug("redirectUrl={}", redirectUrl);

    HttpUtils.redirect(req, resp,
        "/app/user/signinForm?redirectUrl=" + HttpUtils.encodeUrl(redirectUrl));
    return false;
  }
}