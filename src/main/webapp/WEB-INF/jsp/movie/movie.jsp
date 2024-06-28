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
    <p><a href="${sessionScope.CURRENT_MOVIE_LIST}">영화목록</a>
        <a> | </a>
        <a href="./movieEdit?movieId=${movie.movieId}">영화수정</a>
        <a> | </a>
        <a id="btnDel" href="./deleteMovie?movieId=${movie.movieId}">영화삭제</a>
    </p>
    <p class="info title">${movie.movieId}   |   제목: ${movie.titleEncoded}   |   감독: ${movie.directorEncoded}</p>
</main>
</body>
</html>
