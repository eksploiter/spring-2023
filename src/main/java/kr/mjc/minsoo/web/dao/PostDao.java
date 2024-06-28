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
public class PostDao {
    private static final String LIST_POSTS = """
      select postId, contents, userId, name, cdate from post
      order by postId desc limit ?,?
      """;

    private static final String GET_POST = """
      select postId, contents, userId, name, cdate from post
      where postId=?
      """;

    private static final String GET_USER_POST = """
      select postId, contents, userId, name, cdate from post
      where postId=? and userId=?
      """;

    private static final String ADD_POST = """
      insert post(contents, userId, name)
      values (:contents, :userId, :name)
      """;

    private static final String UPDATE_POST= """
      update post set contents=:contents
      where postId=:postId and userId=:userId
      """;

    private static final String DELETE_POST =
            "delete from post where postId=? and userId=?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Post> postRowMapper =
            new BeanPropertyRowMapper<>(Post.class);

    public List<Post> listPosts(Limit limit) {
        return jdbcTemplate.query(LIST_POSTS, postRowMapper,
                limit.getOffset(), limit.getCount());
    }

    public Post getPost(int postId) {
        return jdbcTemplate.queryForObject(GET_POST, postRowMapper,
                postId);
    }

    public Post getUserPost(int postId, int userId) {
        return jdbcTemplate.queryForObject(GET_USER_POST, postRowMapper,
                postId, userId);
    }

    public void addPost(Post post) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        namedParameterJdbcTemplate.update(ADD_POST, params);
    }

    public int updatePost(Post post) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        return namedParameterJdbcTemplate.update(UPDATE_POST, params);
    }

    public int deletePost(int postId, int userId) {
        return jdbcTemplate.update(DELETE_POST, postId, userId);
    }
}
