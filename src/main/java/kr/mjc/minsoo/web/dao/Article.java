package kr.mjc.minsoo.web.dao;

import lombok.Data;
import org.owasp.encoder.Encode;

@Data
public class Article {
  int articleId;
  String title;
  String content;
  int userId;
  String name;
  String cdate;
  String udate;

  public String getTitleEncoded() {
    return Encode.forHtml(title);
  }

  public String getContentEncoded() {
    return Encode.forHtml(content);
  }

  /**
   * new line을 <br> 태그로 변환
   */
  public String getContentHtml() {
    return Encode.forHtml(content).replace("\n", "<br/>\n");
  }

  @Override
  public String toString() {
    return String.format(
            "\nArticle{articleId=%d, title=%s, content=%s, userId=%d, name=%s, cdate=%s, udate=%s}",
            articleId, title, content, userId, name, cdate, udate);
  }
}



