<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>내정보</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/default.css">
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
    <h3>내 정보</h3>
    <table class="info">
        <tr>
            <th>회원번호</th>
            <td>${sessionScope.me_userId}</td>
        </tr>
        <tr>
            <th>이메일</th>
            <td>${sessionScope.me_email}</td>
        </tr>
        <tr>
            <th>이름</th>
            <td>${sessionScope.me_name}</td>
        </tr>
    </table>
    <p><a href="passwordEdit">비밀번호 변경</a></p>
</main>
</body>
</html>