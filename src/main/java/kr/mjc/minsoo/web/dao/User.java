package kr.mjc.minsoo.web.dao;

import lombok.Data;

@Data
public class User {
  int userId;
  String email;
  String password;
  String name;

  @Override
  public String toString() {
    // 비밀번호를 빼고 앞에 \n을 넣는다.
    return String.format("\nUser{userId=%s, email=%s, name=%s}", userId, email,
        name);
  }
}