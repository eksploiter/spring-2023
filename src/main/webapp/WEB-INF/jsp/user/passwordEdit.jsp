<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>비밀번호변경</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../../css/default.css">
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
  <h3>비밀번호 변경</h3>
  <form action="updatePassword" method="post">
    <ul class="list">
      <li><input type="password" name="currentPassword" placeholder="현재 비밀번호"
                 required autofocus/></li>
      <li><input type="password" name="newPassword" placeholder="새 비밀번호"
                 required/>
      </li>
    </ul>
    <p>
      <button type="submit">저장</button>
    </p>
  </form>
  <c:if test="${param.mode=='FAILURE'}">
    <p class="warn">비밀번호 변경 실패입니다.</p>
  </c:if>
</main>
</body>
</html>