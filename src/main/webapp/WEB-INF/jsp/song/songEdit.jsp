<%--
  Created by IntelliJ IDEA.
  User: minsu
  Date: 2023-06-12
  Time: 오전 3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>글 수정</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../../css/default.css">
  <style>
    input[type='text'] {width:50%;}
    textarea {width:50%;}
  </style>
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
  <h3>글 수정</h3>
  <form action="./updateSong" method="post">
    <p><input type="text" name="title" value="${song.titleEncoded}"
              placeholder="노래제목" required autofocus/></p>
    <p><textarea name="name" placeholder="가수"
                 required>${song.nameEncoded}</textarea></p>
    <p>
      <button type="submit">저장</button>
    </p>
    <input type="hidden" name="songId" value="${song.songId}"/>
  </form>
</main>
</body>
</html>
