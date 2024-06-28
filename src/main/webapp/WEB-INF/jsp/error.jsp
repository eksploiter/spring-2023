<%--
  Created by IntelliJ IDEA.
  User: minsu
  Date: 2023-05-30
  Time: 오후 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>에러 페이지</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../../css/error.css">
</head>
<body>
<main>
  <h3>Error Page</h3>
  <table class="info">
    <tr>
      <th>상태코드</th>
      <td>${status}</td>
    </tr>
    <tr>
      <th>에러</th>
      <td>${error}</td>
    </tr>
    <tr>
      <th>경로</th>
      <td>${path}</td>
    </tr>
  </table>
</main>
</body>
</html>
