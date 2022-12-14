<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Summer Edit</title>
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
            var uuid = document.getElementById("uuid").value;
            var contents = document.getElementById("summernote").value;

                var data = {
                    'writer' : writer,
                    'title': title,
                    'txtContent': contents,
                    'uuid' : uuid
                };

                $.ajax({
                    type: "POST",
                    url: "/boardEdit",
                    data: JSON.stringify(data),
                    dataType: "JSON",
                    contentType: "application/json",
                    accept: "application/json",
                    success: function(data) {
                        console.log(data.message);
                        alert("????????? ?????????????????????.");
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
                    <td>????????? </td>
                    <td>
                        <input type="text" name="writer" id="writer" style="width:400px;" value="${view.name}" readonly><br>
                        <input type="hidden" name="uuid" id="uuid" value="${view.uuid}">
                    </td>
                 </tr>
                <tr>
                    <td>????????? </td>
                    <td>
                        <input type="text" name="title" id="title" style="width:1000px;" value="${view.subject}"><br>
                    </td>
                </tr>
                <tr>
                    <td>?????? </td>
                    <td>
                        <textarea id="summernote" rows="10" cols="100" style="width:990px;">${view.content}</textarea>
                    </td>
                </tr>
            </table>
        </form>
        <input type="button" onclick="save();" value="??? ??????">
    </div>
</div>

<!-- textarea ?????? script ???????????? -->
<script id="summerEditor" type="text/javascript">
    $(document).ready(function() {

        $('#summernote').summernote({
            height: 350,
            minHeight: null,
            maxHeight: null,
            focus: true,                  // ????????? ????????? ???????????? ????????? ??????
            lang: "ko-KR",
            placeholder: '?????? 2048????????? ??? ??? ????????????',
            callbacks: {
                onImageUpload : function(files) {
                    uploadSummernoteImageFileS3(files[0],this);
                }
            }

        });


        function uploadSummernoteImageFileS3(file, editor) {
            data = new FormData();
            data.append("file", file);
            $.ajax({
                data : data,
                type : "POST",
                url : "/uploadSummernoteImageFileS3",
                contentType : false,
                processData : false,
                success : function(data) {
                    $(editor).summernote('insertImage',  data.path);

                }
            });
        }
    });
</script>

</body>
</html>