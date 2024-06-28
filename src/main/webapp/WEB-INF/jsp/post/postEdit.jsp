<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>글 수정</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/default.css">
    <style>
        input[type='text'] {width:90%;}
        textarea {width:90%; height:200px;}
    </style>
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
    <h3>글 수정</h3>
    <form action="./updatePost" method="post">
        <p><input type="text" name="contents" value="${post.contentsEncoded}"
                  placeholder="내용" required autofocus/></p>
        <p>
            <button type="submit">저장</button>
        </p>
        <input type="hidden" name="postId" value="${post.postId}"/>
    </form>
</main>
</body>
</html>