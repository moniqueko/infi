<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Summer Board List</title>
    <script type="text/javascript" src="/libs/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <div class="row justify-content-md-center">
        <h2>Summer Editor Board</h2>
        <table class="table">
            <tr>
                <td style="width:10%; text-align: center;">번호</td>
                <td style="width:60%; text-align: center;">제목</td>
                <td style="width:10%; text-align: center;">작성자</td>
                <td style="width:20%; text-align: center;">등록일</td>
            </tr>
            <c:forEach var="board" items="${board}" varStatus="status">
            <tr>
                <td style="width:10%; text-align: center;">${board.uuid}</td>
                <td style="width:60%; text-align: center;"><a href="<c:url value='/view/summer/${board.uuid}'/>">${board.subject}</a></td>
                <td style="width:10%; text-align: center;">${board.name}</td>
                <td style="width:20%; text-align: center;">
                    <fmt:formatDate value="${board.regiDate}" pattern="yyyy-MM-dd HH:mm"/></td>
            </tr>
        </c:forEach>
    </table>
        <button><a href="<c:url value='/summerEditor'/>">글쓰기</a></button>
    </div>
</div>
</body>
</html>
