package kr.mjc.minsoo.web.springmvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.mjc.minsoo.web.HttpUtils;
import kr.mjc.minsoo.web.dao.Limit;
import kr.mjc.minsoo.web.dao.Movie;
import kr.mjc.minsoo.web.dao.MovieDao;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class MovieControllerV2 {
    private static final String CURRENT_MOVIE_LIST = "CURRENT_MOVIE_LIST";
    private final MovieDao movieDao;

    @GetMapping("/movie/movieList")
    public void movieList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 현재 목록을 세션에 저장
        req.getSession().setAttribute(CURRENT_MOVIE_LIST,
                HttpUtils.getRequestURLWithQueryString(req));

        Limit limit =
                new Limit(req.getParameter("count"), req.getParameter("page"));
        req.setAttribute("movieList", movieDao.listMovies(limit));
        req.setAttribute("limit", limit);
        HttpUtils.forward(req, resp);
    }
    @GetMapping("/movie/movieForm")
    public void mapDefault() {
    }
    @PostMapping("/movie/addMovie") //글 추가
    public String addMovie(Movie movie){
        movieDao.addMovie(movie);
        return "redirect:/app/movie/movieList";
    }
    @GetMapping("/movie/movie") //한건 보기
    public void movie(int movieId, Model model){
        model.addAttribute("movie", movieDao.getMovie(movieId));
    }
    @GetMapping("/movie/movieEdit") //글 수정
    public void movieEdit(int movieId, org.springframework.ui.Model model){
        Movie movie = getUserMovie(movieId);
        model.addAttribute("movie", movie);
    }
    @PostMapping("/movie/updateMovie")
    public String updateMovie(Movie movie) {
        getUserMovie(movie.getMovieId());
        movieDao.updateMovie(movie);
        return "redirect:/app/movie/movie?movieId=" + movie.getMovieId();
    }
    @GetMapping("/movie/deleteMovie")
    public String deleteMovie(int movieId){
        getUserMovie(movieId);
        movieDao.deleteMovie(movieId);
        return "redirect:/app/movie/movieList";
    }
    /**
     * 게시글의 권한 체크
     *
     * @throws ResponseStatusException 권한이 없을 경우
     */
    private Movie getUserMovie(int movieId) {
        try {
            return movieDao.getUserMovie(movieId);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}