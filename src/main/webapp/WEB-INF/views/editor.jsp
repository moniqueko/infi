<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Naver Editor</title>

    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="/libs/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <script>
       function save(){

            oEditors.getById["txtContent"].exec("UPDATE_CONTENTS_FIELD", []);

            var writer = document.getElementById("writer").value;
            var title = document.getElementById("title").value;
            var txtContent = document.getElementById("txtContent").value;
            var uploadFile = document.getElementById("uploadFile");

             // var data = {
             //     'writer' : writer,
             //     'title': title,
             //     'txtContent': txtContent
             // };

            var form = $('#editorForm')[0];
            var dataForm = new FormData(form);
            dataForm.append('txtContent',txtContent); //데이터 폼에다 txtContent내용 추가해서 보내기

           $.ajax({
               type: "POST",
               url: "/boardWrite",
               data: dataForm,
               dataType: "text",
               //contentType: "application/json",
               contentType: false,
               processData: false,
               success: function(data) {
                   alert("글쓰기 성공");
                   location.href="/boardList";

               },
               error: function(request, status, error) {
                   console.log("ERROR : "+request.status+"\n"+"message"+request.responseText+"\n"+"error:"+error);
                   alert("fail");
               }
           });



        }
    </script>
</head>
<body>

<div class="container">
    <div class="row justify-content-md-center">
        <form enctype="multipart/form-data" id="editorForm" name="editorForm">
            <table class="table">
                <tr>
                    <td>작성자 </td>
                    <td>
                        <input type="text" name="writer" id="writer" style="width:400px;"><br>
                    </td>
                </tr>
                <tr>
                    <td>글제목 </td>
                    <td>
                        <input type="text" name="title" id="title" style="width:1000px;"><br>
                    </td>
                </tr>
                <tr>
                    <td>내용 </td>
                    <td>
                        <textarea id="txtContent" rows="10" cols="100" style="width:990px;"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>파일첨부1 </td>
                    <td>
                        <input type="file" name="uploadFile" id="uploadFile" style="width:1000px;"><br>
                    </td>
                </tr>
            </table>
            <input type="button" onclick="save();" value="글 작성"> &nbsp; <input type="button" onclick="javascript:history.back();" value="뒤로가기">
        </form>
    </div>
</div>
<!-- textarea 밑에 script 작성하기 -->
<script id="smartEditor" type="text/javascript">
    var oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "txtContent",  //textarea ID 입력
        sSkinURI: "/libs/smarteditor/SmartEditor2Skin.html",
        fCreator: "createSEditor2",
        htParams : {
            bUseToolbar : true,
            bUseVerticalResizer : true,
            bUseModeChanger : true
        }
    });
</script>

</body>
</html>