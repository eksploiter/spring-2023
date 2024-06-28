package kr.mjc.minsoo.web.springmvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.mjc.minsoo.web.HttpUtils;
import kr.mjc.minsoo.web.dao.Article;
import kr.mjc.minsoo.web.dao.ArticleDao;
import kr.mjc.minsoo.web.dao.Limit;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;


@AllArgsConstructor
public class ArticleControllerV1 {
  private static final String CURRENT_ARTICLE_LIST = "CURRENT_ARTICLE_LIST";
  private final ArticleDao articleDao;

  @GetMapping("/article/articleList")
  public void articleList(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // 현재 목록을 세션에 저장
    req.getSession().setAttribute(CURRENT_ARTICLE_LIST,
            HttpUtils.getRequestURLWithQueryString(req));

    Limit limit =
            new Limit(req.getParameter("count"), req.getParameter("page"));
    req.setAttribute("articleList", articleDao.listArticles(limit));
    req.setAttribute("limit", limit);
    HttpUtils.forward(req, resp);
  }

  @GetMapping("/article/articleForm")
  public void mapDefault(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    HttpUtils.forward(req, resp);
  }

  @PostMapping("/article/addArticle")
  public void addArticle(HttpServletRequest req, HttpServletResponse resp)
          throws IOException {
    HttpSession session = req.getSession();
    Article article = new Article();
    article.setTitle(req.getParameter("title"));
    article.setContent(req.getParameter("content"));
    article.setUserId((int) session.getAttribute("me_userId"));
    article.setName((String) session.getAttribute("me_name"));
    articleDao.addArticle(article);
    HttpUtils.redirect(req, resp, "/app/article/articleList");
  }

  @GetMapping("/article/article")
  public void article(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    int articleId = Integer.parseInt(req.getParameter("articleId"));
    req.setAttribute("article", articleDao.getArticle(articleId));
    HttpUtils.forward(req, resp);
  }

  @GetMapping("/article/articleEdit")
  public void articleEdit(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    Article article = getUserArticle(req);
    req.setAttribute("article", article);
    HttpUtils.forward(req, resp);
  }

  @PostMapping("/article/updateArticle")
  public void updateArticle(HttpServletRequest req, HttpServletResponse resp)
          throws IOException {
    Article article = getUserArticle(req);
    article.setTitle(req.getParameter("title"));
    article.setContent(req.getParameter("content"));
    articleDao.updateArticle(article);
    HttpUtils.redirect(req, resp,
            "/app/article/article?articleId=" + article.getArticleId());
  }

  @GetMapping("/article/deleteArticle")
  public void deleteArticle(HttpServletRequest req, HttpServletResponse resp)
          throws IOException {
    Article article = getUserArticle(req);
    articleDao.deleteArticle(article.getArticleId(), article.getUserId());
    resp.sendRedirect(
            (String) req.getSession().getAttribute(CURRENT_ARTICLE_LIST));
  }

  /**
   * 게시글의 권한 체크
   *
   * @throws ResponseStatusException 권한이 없을 경우
   */
  private Article getUserArticle(HttpServletRequest req)
          throws ResponseStatusException {
    int articleId = Integer.parseInt(req.getParameter("articleId"));
    int userId = (int) req.getSession().getAttribute("me_userId");
    try {
      return articleDao.getUserArticle(articleId, userId);
    } catch (DataAccessException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
  }
}