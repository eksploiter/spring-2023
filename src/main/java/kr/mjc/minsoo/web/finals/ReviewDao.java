package kr.mjc.minsoo.web.finals;

import kr.mjc.minsoo.web.dao.Limit;
import kr.mjc.minsoo.web.finals.Review;
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
public class ReviewDao {

    private static final String LIST_REVIEWS = """
      select reviewId, contents, grade, name, cdate from review
      order by reviewId desc limit ?,?
      """;

    private static final String GET_REVIEW = """
      select reviewId, contents, grade, userId, name, cdate from review
      where reviewId=?
      """;

    private static final String GET_USER_REVIEW = """
      select reviewId, contents, grade, userId, name, cdate from review
      where reviewId=? and userId=?
      """;

    private static final String ADD_REVIEW = """
      insert review(contents, grade, userId, name)
      values (:contents, :grade, :userId, :name)
      """;

    private static final String UPDATE_REVIEW = """
      update review set contents=:contents, grade=:grade
      where reviewId=:reviewId and userId=:userId
      """;

    private static final String DELETE_REVIEW =
            "delete from review where reviewId=? and userId=?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Review> reviewRowMapper =
            new BeanPropertyRowMapper<>(Review.class);

    public List<Review> listReviews(Limit limit) {
        return jdbcTemplate.query(LIST_REVIEWS, reviewRowMapper,
                limit.getOffset(), limit.getCount());
    }

    public Review getReview(int reviewId) {
        return jdbcTemplate.queryForObject(GET_REVIEW, reviewRowMapper,
                reviewId);
    }

    public Review getUserReview(int reviewId, int userId) {
        return jdbcTemplate.queryForObject(GET_USER_REVIEW, reviewRowMapper,
                reviewId, userId);
    }

    public void addReview(Review review) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(review);
        namedParameterJdbcTemplate.update(ADD_REVIEW, params);
    }

    public int updateReview(Review review) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(review);
        return namedParameterJdbcTemplate.update(UPDATE_REVIEW, params);
    }

    public int deleteReview(int reviewId, int userId) {
        return jdbcTemplate.update(DELETE_REVIEW, reviewId, userId);
    }
}
