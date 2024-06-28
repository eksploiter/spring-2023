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
    <form action="./postList" method="get">
        <p>
            페이지 : <input type="number" name="page" min="1" value="${limit.page}"
                         required autofocus/>행 : <input type="number" name="count" min="5"
                                                        step="5" value="${limit.count}" required/>
            <button type="submit">검색</button>
            <a href="./postForm">글쓰기</a>
        </p>
    </form>
    <table class="list">
        <tr>
            <th>번호</th>
            <th>내용</th>
            <th>글쓴이</th>
            <th>등록일시</th>
        </tr>
        <c:forEach var="post" items="${postList}">
            <tr>
                <td>${post.postId}</td>
                <td><a
                        href="./post?postId=${post.postId}">${post.contentsEncoded}</a>
                </td>
                <td><a
                        href="../user/userInfo?userId=${post.userId}">${post.name}</a>
                </td>
                <td>${post.cdate}</td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>
