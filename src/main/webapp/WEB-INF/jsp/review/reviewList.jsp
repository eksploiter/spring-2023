<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/default.css">
    <style>
        input[type='number'] {
            width:50px;
            text-align:center;
        }
        input[type='text'] {width:90%; height:200px;}
        textarea {width:10%;}
    </style>
</head>
<body>
<%@include file="/WEB-INF/jsp/header.jsp" %>
<main>
    <h3>리뷰</h3>
    <form action="./addReview" method="post">
        <p><input type="text" name="contents" placeholder="500자 이내" required autofocus/></p>
        <p>평점 <textarea name="grade" placeholder="점수" required></textarea> <button type="submit">등록</button></p>

    </form>
    <form action="./reviewList" method="get">
        <p>
            페이지 : <input type="number" name="page" min="1" value="${limit.page}"
                         required autofocus/>행 : <input type="number" name="count" min="5"
                                                        step="5" value="${limit.count}" required/>
            <button type="submit">검색</button>
            <a href="./reviewForm">글쓰기</a>
        </p>
    </form>
    <table class="list">

        <c:forEach var="review" items="${reviewList}">
            <tr>
                <td>
                        No.${review.reviewId}, <a
                        href="../user/userInfo?userId=${review.userId}">${review.name}</a>, ${review.cdate}, 평점:${review.grade}
                    <br><a${review.reviewId}">${review.contentsEncoded}</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>
