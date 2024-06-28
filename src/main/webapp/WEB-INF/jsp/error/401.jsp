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
            <th>메시지</th>
            <td>권한이 없습니다.</td>
        </tr>
        <tr>
            <th>경로</th>
            <td>${path}</td>
        </tr>
    </table>
    <p><a href="${sessionScope.CURRENT_ARTICLE_LIST}">게시글 목록으로 돌아가기</a>
        <a href="${sessionScope.CURRENT_MOVIE_LIST}">영화 목록으로 돌아가기</a>
        <a href="${sessionScope.CURRENT_SONG_LIST}">노래 목록으로 돌아가기</a>
        <a href="${sessionScope.CURRENT_POST_LIST}">내용 목록으로 돌아가기</a>
    </p>
</main>
</body>
</html>