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

    <div style="text-align: right">
        <form:form action="summerList" method="GET">
            <input type="text" name="keyword" id="keyword" placeholder="제목">
            <input type="submit" value="검색">&nbsp;&nbsp;
            <input type="button" value="목록보기" onclick="location.href='../summerList'">
        </form:form>
    </div>

    <div class="row justify-content-md-center">
        <c:if test="${keyword==null}">
            <div class="text-center">
                <ul class="pagination">
                    <!-- 이전prev -->
                    <c:if test="${pm.prev }">
                        <li><a href="/summerList?page=${pm.startPage-1}">[&laquo;]</a></li>
                    </c:if>
                    <!-- 페이지블럭 -->
                    <c:forEach var="idx" begin="${pm.startPage }" end="${pm.endPage }">
                        <!-- 삼항연산자를 사용해서 class로 스타일적용  -->
                        <li ${pm.cri.page == idx? 'class=active':''}>
                            <a href="/summerList?page=${idx }">[&nbsp;${idx}&nbsp;]&nbsp;&nbsp;</a>
                        </li>
                    </c:forEach>
                    <!-- 다음next -->
                    <c:if test="${pm.next && pm.endPage > 0}">
                        <li><a href="/summerList?page=${pm.endPage+1}">[&raquo;]</a></li>
                    </c:if>
                </ul>
            </div>
        </c:if>

        <!-- 키워드 있을 시 -->

        <c:if test="${keyword!=null}">
            <div class="text-center">
                <ul class="pagination">
                    <!-- 이전prev -->
                    <c:if test="${pm.prev }">
                        <li><a href="/summerList?keyword=${keyword}&page=${pm.startPage-1}">[&laquo;]</a></li>
                    </c:if>
                    <!-- 페이지블럭 -->
                    <c:forEach var="idx" begin="${pm.startPage }" end="${pm.endPage }">
                        <!-- 삼항연산자를 사용해서 class로 스타일적용  -->
                        <li ${pm.cri.page == idx? 'class=active':''}>
                            <a href="/summerList?keyword=${keyword}&page=${idx }">[&nbsp;${idx}&nbsp;]&nbsp;&nbsp;</a>
                        </li>
                    </c:forEach>
                    <!-- 다음next -->
                    <c:if test="${pm.next && pm.endPage > 0}">
                        <li><a href="/summerList?keyword=${keyword}&page=${pm.endPage+1}">[&raquo;]</a></li>
                    </c:if>
                </ul>
            </div>
        </c:if>

    </div>
</div>
</body>
</html>
