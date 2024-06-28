package kr.mjc.minsoo.web.springmvc;

import jakarta.servlet.http.HttpServletRequest;
import kr.mjc.minsoo.web.HttpUtils;
import kr.mjc.minsoo.web.dao.PostDao;
import kr.mjc.minsoo.web.dao.Limit;
import kr.mjc.minsoo.web.dao.Post;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

@Controller
@AllArgsConstructor
public class PostControllerV2 {
    private static final String CURRENT_POST_LIST = "CURRENT_POST_LIST";
    private final PostDao postDao;

    @GetMapping("/post/postList")
    public void postList(HttpServletRequest req, Limit limit, Model model) {
        // 현재 목록을 세션에 저장
        req.getSession().setAttribute(CURRENT_POST_LIST,
                HttpUtils.getRequestURLWithQueryString(req));

        req.setAttribute("postList", postDao.listPosts(limit));
    }

    @GetMapping("/post/postForm")
    public void mapDefault() {
    }

    @PostMapping("/post/addPost")
    public String addPost(Post post,
                          @SessionAttribute("me_userId") int userId,
                          @SessionAttribute("me_name") String name) {
        post.setUserId(userId);
        post.setName(name);
        postDao.addPost(post);
        return "redirect:/app/post/postList";
    }

    @GetMapping("/post/post")
    public void post(int postId, Model model) {
        model.addAttribute("post", postDao.getPost(postId));
    }

    @GetMapping("/post/postEdit")
    public void postEdit(int postId,
                            @SessionAttribute("me_userId") int userId, Model model) {
        Post post = getUserPost(postId, userId);
        model.addAttribute("post", post);
    }

    @PostMapping("/post/updatePost")
    public String updatePost(Post post,
                                @SessionAttribute("me_userId") int userId) {
        getUserPost(post.getPostId(), userId);
        post.setUserId(userId);
        postDao.updatePost(post);
        return "redirect:/app/post/post?postId=" + post.getPostId();
    }

    @GetMapping("/post/deletePost")
    public String deletePost(int postId,
                                @SessionAttribute("me_userId") int userId,
                                @SessionAttribute(CURRENT_POST_LIST) String currentPostList) {
        getUserPost(postId, userId);
        postDao.deletePost(postId, userId);
        return "redirect:"+ currentPostList;
    }
    /**
     * 게시글의 권한 체크
     *
     * @throws ResponseStatusException 권한이 없을 경우
     */
    private Post getUserPost(int postId, int userId) {
        try {
            return postDao.getUserPost(postId, userId);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
