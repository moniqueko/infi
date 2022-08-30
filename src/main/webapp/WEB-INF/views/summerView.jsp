<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Summer List / File upload</title>
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
        <br>
        <h2>Summer Editor Board</h2>
        <br>
        <table class="table">
            <tr>
                <td>제목</td>
                <td>${view.subject}</td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>${view.name}</td>
            </tr>
            <tr>
            <td>등록일</td>
            <td>${view.regiDate}</td>
        </tr>
        <tr>
            <td>내용</td>
            <td>${view.content}</td>
        </tr>
            <tr>
                <td>첨부파일</td>
                    <td>
                        <c:if test="${!attach.isEmpty()}">
                        <c:forEach var="attach" items="${attach}" varStatus="status">
                            ${attach.fileRealName} <a href="${attach.filePath}"> OPEN</a><br>
                        </c:forEach>
                        </c:if>
                    </td>
                </tr>

        </table>
    </div>
    <input type="button" onclick="javascript:history.back();" value="뒤로가기">&nbsp;
    <button><a href="<c:url value='/editSummerEditor/${view.uuid}'/>">수정하기</a></button>
</div>
</body>
</html>
