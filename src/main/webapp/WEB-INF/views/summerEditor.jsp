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
            var contents = document.getElementById("summernote").value; //모든 태그. 이미 이미지 주소 로컬에 생성됨.

            var data = {
                    'writer' : writer,
                    'title': title,
                    'txtContent': contents
                };

                $.ajax({
                    type: "POST",
                    url: "/boardWriteSummer",
                    data: JSON.stringify(data),
                    dataType: "JSON",
                    contentType: "application/json",
                    accept: "application/json",
                    success: function(data) {
                        //리턴값 (uuid)가져와서 여기에 파일첨부등록
                        console.log(data);
                        console.log(data.uuid);
                        var uuid = data.uuid;
                        fileUpload(uuid);

                        alert("글쓰기 성공");
                        location.href="/summerList";

                    },
                    error: function(request, status, error) {
                        console.log("ERROR : "+request.status+"\n"+"message"+request.responseText+"\n"+"error:"+error);

                        alert("fail");
                    }
                });

        }

        function fileUpload(uuid) {

            var form = $('#editorForm')[0];
            var dataForm = new FormData(form);
            dataForm.append('uuid',uuid);

            $.ajax({
                data: dataForm,
                processData: false,
                contentType: false,
                enctype: "multipart/form-data",
                type: "POST",
                url: "/boardSummerFileUpload",

                success: function (data) {
                    alert("글쓰기 성공");
                }
                , error: function (request, status, error, data) {
                    alert("Error");
                }

            });
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

                <tr>
                    <td>파일첨부 </td>
                    <td>
                        <input type="file" name="uploadFiles" id="uploadFiles" multiple="multiple"><br>
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
            height: 350,
            minHeight: null,
            maxHeight: null,
            focus: true,
            lang: "ko-KR",
            placeholder: '최대 2048자까지 쓸 수 있습니다',
            callbacks: {

                onImageUpload: function (files) { //url이동 아닌 업로드시 동작
                    uploadSummernoteImageFileS3(files[0], this);

                },
                onMediaDelete : function(target) {
                    //alert(target[0].src);
                    deleteFileFromS3(target[0].src);
                }
                // },
                // onKeydown: function(e) {
                //     if(e.keyCode === 8){
                //
                //     }
                // }


            }

        });

        // $('#summernote').on('summernote.image.upload', function(we, files) {
        //     if(files[0].onKeydown().keyCode===8){
        //             alert("삭제실행");
        //         }
        //         //deleteFileFromS3(files[0].src);
        //
        //
        // });

        // $('#summernote').summernote({
        //     height: 350,
        //     minHeight: null,
        //     maxHeight: null,
        //     focus: true,
        //     lang: "ko-KR",
        //     placeholder: '최대 2048자까지 쓸 수 있습니다',
        //     callbacks: {
        //         onImageLinkInsert: function(url) {
        //             $img = $('<img>').attr({ src: url })
        //             $summernote.summernote('insertNode', $img[0]);
        //             }
        //
        //         }
        //     });


        // function uploadSummernoteImageFile(file, editor) { //로컬에 저장시
        //     data = new FormData();
        //     data.append("file", file);
        //     $.ajax({
        //         data : data,
        //         type : "POST",
        //         url : "/uploadSummernoteImageFile",
        //         contentType : false,
        //         processData : false,
        //         success : function(data) {
        //             $(editor).summernote('insertImage', data.url); //에디터 안에 그림주소 가져와 붙여넣기
        //
        //         }
        //         ,error:function(request,status,error, data){
        //             alert("Error");
        //         }
        //     });
        // }

        function uploadSummernoteImageFileS3(file, editor) { //AWS S3 이미지업로드
            data = new FormData();
            data.append("file", file);
            $.ajax({
                data : data,
                type : "POST",
                url : "/uploadSummernoteImageFileS3",
                contentType : false,
                processData : false,
                success : function(data) {
                    $(editor).summernote('insertImage', data.path); //에디터 안에 그림주소 가져와 붙여넣기
                }
                ,error:function(request,status,error, data){
                    alert("Error");
                }
            });
        }

        function deleteFileFromS3(src) { //AWS S3파일 삭제
            $.ajax({
                data: {src: src},
                type: "POST",
                url: "/deleteFileFromS3",
                // cache: false,
                success: function (msg) {
                    alert("이미지가 삭제되었습니다");
                }
                , error: function (request, status, error, data) {
                    alert("Error");
                }

            });
        }


    });
</script>

</body>
</html>