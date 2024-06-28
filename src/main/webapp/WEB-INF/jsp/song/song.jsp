<%--
  Created by IntelliJ IDEA.
  User: minsu
  Date: 2023-06-12
  Time: 오전 3:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>글쓰기</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../../css/default.css">
  <style>
    input[type='text'] {width:90%;}
    textarea {width:90%; height:50px;}
    p.title {border-top:1px solid gray;font-weight:bold;}
    p.info {border-bottom:1px solid gray;}
  </style>
  <script>
    window.onload = () => {
      document.querySelector('#btnDel').onclick = () => confirm('삭제하시겠습니까?');
    }
  </script>
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
  <h3>글보기</h3>
  <p><a href="${sessionScope.CURRENT_SONG_LIST}">노래목록</a>
    <a> | </a>
    <a href="./songEdit?songId=${song.songId}">노래수정</a>
    <a> | </a>
    <a id="btnDel" href="./deleteSong?songId=${song.songId}">노래삭제</a>
  </p>
  <p class="info title">${song.songId}   |   제목: ${song.titleEncoded}   |   가수: ${song.nameEncoded}</p>
</main>
</body>
</html>
