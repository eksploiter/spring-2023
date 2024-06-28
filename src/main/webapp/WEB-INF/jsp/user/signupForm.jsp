<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/default.css">
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
    <h3>회원 가입</h3>
    <form action="signup" method="post">
        <ul class="list">
            <li><input type="email" name="email" placeholder="이메일" required
                       autofocus/>
            </li>
            <li><input type="password" name="password" placeholder="비밀번호" required/>
            </li>
            <li><input type="text" name="name" placeholder="이름" required/></li>
        </ul>
        <p>
            <button type="submit">등록</button>
        </p>
    </form>
    <c:if test="${param.mode=='FAILURE'}">
        <p class="warn">회원가입 실패입니다.</p>
    </c:if>
</main>
</body>
</html>