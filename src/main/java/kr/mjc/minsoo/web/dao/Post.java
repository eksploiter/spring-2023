package kr.mjc.minsoo.web.dao;

import lombok.Data;
import org.owasp.encoder.Encode;

@Data
public class Post {
    int postId;
    String contents;
    int userId;
    String name;
    String cdate;

    public String getContentsEncoded() {
        return Encode.forHtml(contents);
    }

    @Override
    public String toString() {
        return String.format(
                "\nPost{postId=%d, contents=%s, userId=%d, name=%s, cdate=%s}",
                postId, contents, userId, name, cdate);
    }
}
