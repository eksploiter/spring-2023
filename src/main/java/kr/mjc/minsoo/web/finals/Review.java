package kr.mjc.minsoo.web.finals;

import lombok.Data;
import org.owasp.encoder.Encode;

@Data
public class Review {
    int reviewId;
    String contents;
    String grade;
    int userId;
    String name;
    String cdate;

    public String getContentsEncoded() {
        return Encode.forHtml(contents);
    }

    public String getGradeEncoded() {
        return Encode.forHtml(grade);
    }

    /**
     * new line을 <br> 태그로 변환
     */
    public String getGradeHtml() {
        return Encode.forHtml(grade).replace("\n", "<br/>\n");
    }

    @Override
    public String toString() {
        return String.format(
                "\nReview{reviewId=%d, contents=%s, grade=%s, userId=%d, name=%s, cdate=%s}",
                reviewId, contents, grade, userId, name, cdate);
    }
}
