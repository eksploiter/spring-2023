package kr.mjc.minsoo.web.finals;

import jakarta.servlet.http.HttpServletRequest;
import kr.mjc.minsoo.web.HttpUtils;
import kr.mjc.minsoo.web.dao.*;
import kr.mjc.minsoo.web.finals.Review;
import kr.mjc.minsoo.web.finals.ReviewDao;
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
public class ReviewControllerV2 {

    private static final String CURRENT_REVIEW_LIST = "CURRENT_REVIEW_LIST";
    private final ReviewDao reviewDao;

    @GetMapping("/review/reviewList")
    public void reviewList(HttpServletRequest req, Limit limit, Model model) {
        // 현재 목록을 세션에 저장
        req.getSession().setAttribute(CURRENT_REVIEW_LIST,
                HttpUtils.getRequestURLWithQueryString(req));

        req.setAttribute("reviewList", reviewDao.listReviews(limit));
    }

    @GetMapping("/review/reviewForm")
    public void mapDefault() {
    }

    @PostMapping("/review/addReview")
    public String addReview(Review review,
                            @SessionAttribute("me_userId") int userId,
                            @SessionAttribute("me_name") String name) {
        review.setUserId(userId);
        review.setName(name);
        reviewDao.addReview(review);
        return "redirect:/app/review/reviewList";
    }

    @GetMapping("/review/review")
    public void review(int reviewId, Model model) {
        model.addAttribute("review", reviewDao.getReview(reviewId));
    }

    @GetMapping("/review/reviewEdit")
    public void reviewEdit(int reviewId,
                         @SessionAttribute("me_userId") int userId, Model model) {
        Review review = getUserReview(reviewId, userId);
        model.addAttribute("post", review);
    }

    @PostMapping("/review/updateReview")
    public String updateReview(Review review,
                             @SessionAttribute("me_userId") int userId) {
        getUserReview(review.getReviewId(), userId);
        review.setUserId(userId);
        reviewDao.updateReview(review);
        return "redirect:/app/review/review?reviewId=" + review.getReviewId();
    }

    @GetMapping("/review/deleteReview")
    public String deleteReview(int reviewId,
                             @SessionAttribute("me_userId") int userId,
                             @SessionAttribute(CURRENT_REVIEW_LIST) String currentReviewList) {
        getUserReview(reviewId, userId);
        reviewDao.deleteReview(reviewId, userId);
        return "redirect:"+ currentReviewList;
    }
    /**
     * 게시글의 권한 체크
     *
     * @throws ResponseStatusException 권한이 없을 경우
     */
    private Review getUserReview(int reviewId, int userId) {
        try {
            return reviewDao.getUserReview(reviewId, userId);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
