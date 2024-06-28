package kr.mjc.minsoo.web.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Data
@Slf4j
public class Limit {
  int count = 20;
  int page = 1;

  public Limit(String count, String page) {
    try {
      if (count != null)
        this.count = Integer.parseInt(count);
      if (page != null)
        this.page = Integer.parseInt(page);
    } catch (NumberFormatException e) {
      log.error(e.toString());
    }
  }

  public int getOffset() {
    return (page - 1) * count;
  }
}