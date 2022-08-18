<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Summer Editor</title>
    <script type="text/javascript" src="/libs/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <script src="/libs/summernote/summernote-lite.js"></script>
    <script src="/libs/summernote/lang/summernote-ko-KR.js"></script>

    <link rel="stylesheet" href="/libs/summernote/summernote-lite.css">

    <script>
        function save(){
            var writer = document.getElementById("writer").value;
            var title = document.getElementById("title").value;
            var contents = document.getElementById("summernote").value;

                var data = {
                    'writer' : writer,
                    'title': title,
                    'txtContent': contents
                };

                $.ajax({
                    type: "POST",
                    url: "/boardWrite",
                    data: JSON.stringify(data),
                    dataType: "JSON",
                    contentType: "application/json",
                    accept: "application/json",
                    success: function(data) {
                        alert("글쓰기 성공");
                        location.href="/list";

                    },
                    error: function(request, status, error) {
                        console.log("ERROR : "+request.status+"\n"+"message"+request.responseText+"\n"+"error:"+error);

                        alert("fail");
                    }
                });

                $("#editorForm").submit();

        }

    </script>
</head>
<body>

<div class="container">
    <div class="row justify-content-md-center">
        <form id="editorForm" name="editorForm">
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
                        <textarea id="summernote" rows="10" cols="100" style="width:990px;"></textarea>
                    </td>
                </tr>
            </table>
            <input type="button" onclick="save();" value="글 작성"> &nbsp; <input type="button" onclick="javascript:history.back();" value="뒤로가기">
        </form>
    </div>
</div>
<!-- textarea 밑에 script 작성하기 -->
<script id="summerEditor" type="text/javascript">
    $(document).ready(function() {

        $('#summernote').summernote({
            height: 350,                 // 에디터 높이
            minHeight: null,             // 최소 높이
            maxHeight: null,             // 최대 높이
            focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
            lang: "ko-KR",					// 한글 설정
            placeholder: '최대 2048자까지 쓸 수 있습니다', //placeholder 설정
            callbacks: {	             //여기 부분이 이미지를 첨부하는 부분
                onImageUpload : function(files) {
                    uploadSummernoteImageFile(files[0],this);
                }
            }

        });

        function uploadSummernoteImageFile(file, editor) {
            data = new FormData();
            data.append("file", file);
            $.ajax({
                data : data,
                type : "POST",
                url : "/uploadSummernoteImageFile",
                contentType : false,
                processData : false,
                success : function(data) {
                    //항상 업로드된 파일의 url이 있어야 한다.
                    $(editor).summernote('insertImage', data.url);

                }
                ,error:function(request,status,error, data){
                    alert("Error");
                }
            });
        }


    });
</script>

</body>
</html>