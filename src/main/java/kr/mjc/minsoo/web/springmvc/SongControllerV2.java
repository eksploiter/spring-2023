package kr.mjc.minsoo.web.springmvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.mjc.minsoo.web.HttpUtils;
import kr.mjc.minsoo.web.dao.Limit;
import kr.mjc.minsoo.web.dao.Song;
import kr.mjc.minsoo.web.dao.SongDao;
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
public class SongControllerV2 {
    private static final String CURRENT_SONG_LIST = "CURRENT_SONG_LIST";
    private final SongDao songDao;

    @GetMapping("/song/songList")
    public void songList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 현재 목록을 세션에 저장
        req.getSession().setAttribute(CURRENT_SONG_LIST,
                HttpUtils.getRequestURLWithQueryString(req));

        Limit limit =
                new Limit(req.getParameter("count"), req.getParameter("page"));
        req.setAttribute("songList", songDao.listSongs(limit));
        req.setAttribute("limit", limit);
        HttpUtils.forward(req, resp);
    }
    @GetMapping("/song/songForm")
    public void mapDefault() {
    }
    @PostMapping("/song/addSong")
    public String addSong(Song song){
        songDao.addSong(song);
        return "redirect:/app/song/songList";
    }
    @GetMapping("/song/song") //한건 보기
    public void song(int songId, Model model){
        model.addAttribute("song", songDao.getSong(songId));
    }
    @GetMapping("/song/songEdit") //글 수정
    public void songEdit(int songId, org.springframework.ui.Model model){
        Song song = getUserSong(songId);
        model.addAttribute("song", song);
    }
    @PostMapping("/song/updateSong")
    public String updateSong(Song song) {
        getUserSong(song.getSongId());
        songDao.updateSong(song);
        return "redirect:/app/song/song?songId=" + song.getSongId();
    }
    @GetMapping("/song/deleteSong")
    public String deleteSong(int songId){
        getUserSong(songId);
        songDao.deleteSong(songId);
        return "redirect:/app/song/songList";
    }
    /**
     * 게시글의 권한 체크
     *
     * @throws ResponseStatusException 권한이 없을 경우
     */
    private Song getUserSong(int songId) {
        try {
            return songDao.getUserSong(songId);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}