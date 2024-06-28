<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>영화 목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/default.css">
    <style>
        input[type='number'] {
            width:50px;
            text-align:center;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
    <h3>영화 목록</h3>
    <form action="./movieList" method="get">
        <p>
            페이지 : <input type="number" name="page" min="1" value="${limit.page}"
                         required autofocus/>행 : <input type="number" name="count" min="5"
                                                        step="5" value="${limit.count}" required/>
            <button type="submit">검색</button>
            <a href="./movieForm">글쓰기</a>
        </p>
    </form>
    <table class="list">
        <tr>
            <th>번호</th>
            <th>영화제목</th>
            <th>감독</th>

        </tr>
        <c:forEach var="movie" items="${movieList}">
            <tr>
                <td>${movie.movieId}</td>
                <td><a
                        href="./movie?movieId=${movie.movieId}">${movie.titleEncoded}</a>
                </td>
                <td><a${movie.movieId}">${movie.director}</a></td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>
