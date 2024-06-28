package kr.mjc.minsoo.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class HttpUtils {
  /**
   * queryString을 포함한 requestURL
   */
  public static String getRequestURLWithQueryString(
          HttpServletRequest request) {
    StringBuffer requestURL = request.getRequestURL();
    String queryString = request.getQueryString();
    return queryString == null ? requestURL.toString() :
            requestURL.append("?").append(queryString).toString();
  }

  /**
   * /WEB-INF/jsp/ 디렉터리의 jsp 파일로 forward
   */
  public static void forward(HttpServletRequest req, HttpServletResponse resp,
                             String path) throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/jsp%s.jsp".formatted(path))
            .forward(req, resp);
  }

  /**
   * path가 없을 경우 req.getPathInfo()로 forward
   */
  public static void forward(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    forward(req, resp, req.getPathInfo());
  }

  /**
   * response.sendRedirect
   */
  public static void redirect(HttpServletRequest req, HttpServletResponse resp,
                              String path) throws IOException {
    resp.sendRedirect(req.getContextPath() + path);
  }

  /**
   * encode Url
   */
  public static String encodeUrl(String url) {
    return URLEncoder.encode(url, Charset.defaultCharset());
  }
}