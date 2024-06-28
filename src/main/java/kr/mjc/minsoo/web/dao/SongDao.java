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
public class SongDao {
    private static final String LIST_SONGS = """
      select songId, title, name from song
      order by songId desc limit ?,?
      """;

    private static final String GET_SONG = """
      select songId, title, name from song
      where songId=?
      """;

    private static final String GET_USER_SONG = """
      select songId, title, name from song
      where songId=?
      """;

    private static final String ADD_SONG = """
      insert song(title, name)
      values (:title, :name)
      """;

    private static final String UPDATE_SONG = """
      update song set title=:title, name=:name
      where songId=:songId
      """;

    private static final String DELETE_SONG =
            "delete from song where songId=?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * resultSet을 article 오브젝트로 자동 매핑하는 매퍼
     */
    private final RowMapper<Song> songRowMapper =
            new BeanPropertyRowMapper<>(Song.class);

    public List<Song> listSongs(Limit limit) {
        return jdbcTemplate.query(LIST_SONGS, songRowMapper,
                limit.getOffset(), limit.getCount());
    }

    public Song getSong(int songId) {
        return jdbcTemplate.queryForObject(GET_SONG, songRowMapper,
                songId);
    }

    public Song getUserSong(int songId) {
        return jdbcTemplate.queryForObject(GET_USER_SONG, songRowMapper,
                songId);
    }

    public void addSong(Song song) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(song);
        namedParameterJdbcTemplate.update(ADD_SONG, params);
    }

    public int updateSong(Song song) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(song);
        return namedParameterJdbcTemplate.update(UPDATE_SONG, params);
    }

    public int deleteSong(int songId) {
        return jdbcTemplate.update(DELETE_SONG, songId);
    }
}
