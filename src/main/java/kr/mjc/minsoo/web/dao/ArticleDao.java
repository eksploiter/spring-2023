package kr.mjc.minsoo.web.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ArticleDao {

  private static final String LIST_ARTICLES = """
      select articleId, title, userId, name, cdate, udate from article
      order by articleId desc limit ?,?
      """;

  private static final String GET_ARTICLE = """
      select articleId, title, content, userId, name, cdate, udate from article
      where articleId=?
      """;

  private static final String GET_USER_ARTICLE = """
      select articleId, title, content, userId, name, cdate, udate from article
      where articleId=? and userId=?
      """;

  private static final String ADD_ARTICLE = """
      insert article(title, content, userId, name)
      values (:title, :content, :userId, :name)
      """;

  private static final String UPDATE_ARTICLE = """
      update article set title=:title, content=:content
      where articleId=:articleId and userId=:userId
      """;

  private static final String DELETE_ARTICLE =
          "delete from article where articleId=? and userId=?";

  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  /**
   * resultSet을 article 오브젝트로 자동 매핑하는 매퍼
   */
  private final RowMapper<Article> articleRowMapper =
          new BeanPropertyRowMapper<>(Article.class);

  public List<Article> listArticles(Limit limit) {
    return jdbcTemplate.query(LIST_ARTICLES, articleRowMapper,
            limit.getOffset(), limit.getCount());
  }

  public Article getArticle(int articleId) {
    return jdbcTemplate.queryForObject(GET_ARTICLE, articleRowMapper,
            articleId);
  }

  public Article getUserArticle(int articleId, int userId) {
    return jdbcTemplate.queryForObject(GET_USER_ARTICLE, articleRowMapper,
            articleId, userId);
  }

  public void addArticle(Article article) {
    SqlParameterSource params = new BeanPropertySqlParameterSource(article);
    namedParameterJdbcTemplate.update(ADD_ARTICLE, params);
  }

  public int updateArticle(Article article) {
    SqlParameterSource params = new BeanPropertySqlParameterSource(article);
    return namedParameterJdbcTemplate.update(UPDATE_ARTICLE, params);
  }

  public int deleteArticle(int articleId, int userId) {
    return jdbcTemplate.update(DELETE_ARTICLE, articleId, userId);
  }
}