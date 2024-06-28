<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/default.css">
    <style>
        input[type='number'] {width:50px; text-align:center;}
    </style>
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
    <h3>회원 목록</h3>
    <form action="./userList" method="get">
        <p>
            페이지 : <input type="number" name="page" min="1" value="${param.page}"
                         required autofocus/>
            행 : <input type="number" name="count" min="5" step="5"
                       value="${param.count}" required/>,
            <button type="submit">검색</button>
        </p>
    </form>
    <table class="list">
        <tr>
            <th>번호</th>
            <th>이메일</th>
            <th>이름</th>
        </tr>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.email}</td>
                <td>${user.name}</td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>
