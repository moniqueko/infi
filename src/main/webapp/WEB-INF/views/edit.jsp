<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Editor</title>
    <script type="text/javascript" src="/libs/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <style>

    </style>
    <script>
        function save(){
            oEditors.getById["txtContent"].exec("UPDATE_CONTENTS_FIELD", []);

            var writer = document.getElementById("writer").value;
            var title = document.getElementById("title").value;
            var uuid = document.getElementById("uuid").value;
            var contents = document.getElementById("txtContent").value;

                //var form = $('#editorForm')[0];
                //var data = new FormData(form);

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
                        alert("수정이 완료되었습니다.");
                        location.href="/list";

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
        <form id="editorForm" name="editorForm">
            <table class="table">
                <tr>
                    <td>작성자 </td>
                    <td>
                        <input type="text" name="writer" id="writer" style="width:400px;" value="${view.name}" readonly><br>
                        <input type="hidden" name="uuid" id="uuid" value="${view.uuid}">
                    </td>
                 </tr>
                <tr>
                    <td>글제목 </td>
                    <td>
                        <input type="text" name="title" id="title" style="width:1000px;" value="${view.subject}"><br>
                    </td>
                </tr>
                <tr>
                    <td>내용 </td>
                    <td>
                        <textarea id="txtContent" rows="10" cols="100" style="width:990px;">${view.content}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>파일첨부 </td>
                    <td>
                        <input type="file" name="uploadFile" id="uploadFile"><br><br>
                        <div id="afterDel"><span>${view.fileRealName}</span><input type="button" onclick="del();" value="첨부파일 삭제">
                        <input type="hidden" value="${view.filePath}" id="filePath">
                            <input type="hidden" value="${view.attachUid}" id="attachUid">
                        </div>
                    </td>
                </tr>
            </table>
        </form>
        <input type="button" onclick="save();" value="글 수정">
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

<script>
    function del() {

        if (!confirm("삭제후엔 되돌릴수 없습니다. 삭제하시겠습니까?")) {
        return;

        } else {
            var src = document.getElementById("filePath").value;
            var uuid = document.getElementById("uuid").value;
            var attachUid = document.getElementById("attachUid").value;
            var afterDel = document.getElementById("afterDel");

            $.ajax({
                data: {
                    "src": src,
                    "attachUid": attachUid,
                    "uuid": uuid

                },
                type: "POST",
                url: "/deleteFileFromS3",
                // cache: false,
                success: function (msg) {

                    alert("파일이 삭제되었습니다.");
                    afterDel.remove();
                }
                , error: function (request, status, error, data) {
                    alert("Error");
                }

            });

        }
    }
</script>

</body>
</html>