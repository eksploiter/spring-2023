<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시글목록</title>
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
    <h3>게시글 목록</h3>
    <form action="./articleList" method="get">
        <p>
            페이지 : <input type="number" name="page" min="1" value="${limit.page}"
                         required autofocus/>행 : <input type="number" name="count" min="5"
                                                        step="5" value="${limit.count}" required/>
            <button type="submit">검색</button>
            <a href="./articleForm">글쓰기</a>
        </p>
    </form>
    <table class="list">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>글쓴이</th>
            <th>등록일시</th>
        </tr>
        <c:forEach var="article" items="${articleList}">
            <tr>
                <td>${article.articleId}</td>
                <td><a
                        href="./article?articleId=${article.articleId}">${article.titleEncoded}</a>
                </td>
                <td><a
                        href="../user/userInfo?userId=${article.userId}">${article.name}</a>
                </td>
                <td>${article.cdate}</td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>