package com.infinitum.monique.controller;


import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.domain.BoardWriter;
import com.infinitum.monique.domain.SingleResult;
import com.infinitum.monique.service.AwsS3Service;
import com.infinitum.monique.service.BoardService;
import com.infinitum.monique.service.ResponseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@org.springframework.web.bind.annotation.RestController

public class RestController {
    private final ResponseService responseService;
    private final BoardService boardService;
    private final AwsS3Service awsS3Service;

    public RestController(ResponseService responseService, BoardService boardService, AwsS3Service awsS3Service) {
        this.responseService = responseService;
        this.boardService = boardService;
        this.awsS3Service = awsS3Service;
    }

    @PostMapping("/boardWrite")//네이버 에디터
    public Map<String, Object> boardWrite(Model model, BoardVo boardVo, @ModelAttribute BoardWriter boardWriter, AttachFile attachFile) throws IOException {
        Map<String, Object> object = new HashMap<String, Object>();

            if(!boardWriter.getUploadFile().isEmpty()) {

                String realName = boardWriter.getUploadFile().getOriginalFilename();
                String contentType = boardWriter.getUploadFile().getContentType();
                String extension = boardWriter.getUploadFile().getOriginalFilename().substring(boardWriter.getUploadFile().getOriginalFilename().lastIndexOf(".") + 1);
                long size = boardWriter.getUploadFile().getSize();

                object = awsS3Service.uploadFile(boardWriter.getUploadFile());

                String path = "https://moniquebucket.s3.ap-northeast-2.amazonaws.com/"+object.get("fileName").toString();

                attachFile.setFilePath(path);
                attachFile.setFileName(object.get("fileName").toString());
                attachFile.setFileContentType(contentType);
                attachFile.setFileRealName(realName);
                attachFile.setFileSize(size);
                attachFile.setFileExtension(extension);

                boardVo.setFile(object.get("fileName").toString());
                boardService.insertAttachFile(attachFile); //셀렉트키는 자동으로 attacFhile에 반환된다. (xml에 파라미터 설정을 했으므로)
                boardVo.setAttachUid(attachFile.getAttachUid());

                object.put("path", path);
            }

            boardVo.setName(boardWriter.getWriter());
            boardVo.setContent(boardWriter.getTxtContent());
            boardVo.setSubject(boardWriter.getTitle());

            boardService.boardWrite(boardVo);

            object.put("path", null);

            return object;
    }

    @PostMapping("/boardWriteSummer") //파일첨부 외
    public  Map<String, Object> boardWrite(BoardVo boardVo, @RequestBody BoardWriter boardWriter, HttpServletRequest request) throws IOException {
        Map<String, Object> object = new HashMap<String, Object>();

        boardVo.setName(boardWriter.getWriter());
        boardVo.setContent(boardWriter.getTxtContent());
        boardVo.setSubject(boardWriter.getTitle());

        BoardVo board = boardService.boardSummerWrite(boardVo);

        object.put("uuid",board.getUuid());

        return object;
    }
    @PostMapping("/boardSummerFileUpload") //uuid와 첨부파일만 받아온다
    public Map<String, Object> boardSummerFileUpload(Model model, BoardVo boardVo, @ModelAttribute BoardWriter boardWriter, AttachFile attachFile) throws IOException {

        Map<String, Object> object = new HashMap<String, Object>();
        System.out.println(boardVo.getUuid() +"uuid");
        System.out.println(boardWriter+"<<<<<<<<<<<<<<<<<<2");
        System.out.println(boardWriter.getUploadFiles());

        List<MultipartFile> mList = boardWriter.getUploadFiles();

        if(!boardWriter.getUploadFiles().isEmpty()) {
            for (MultipartFile list : mList){
                String realName = list.getOriginalFilename();
                String contentType =  list.getContentType();
                String extension = list.getOriginalFilename().substring(list.getOriginalFilename().lastIndexOf(".") + 1);
                long size = list.getSize();

                object = awsS3Service.uploadFile(list);
                System.out.println(object.get("fileName").toString()+" 파일이름 확인");

                String path = "https://moniquebucket.s3.ap-northeast-2.amazonaws.com/"+object.get("fileName").toString();

                attachFile.setFilePath(path);
                attachFile.setFileName(object.get("fileName").toString());
                attachFile.setFileContentType(contentType);
                attachFile.setFileRealName(realName);
                attachFile.setFileSize(size);
                attachFile.setFileExtension(extension);
                attachFile.setUuid(boardVo.getUuid());

                boardService.insertSummerAttachFile(attachFile);

            }
        }
        return object;
    }

    @PostMapping("/boardEditFileExist")
    public Map<String, Object> boardEditFileExist(Model model, BoardVo boardVo, @ModelAttribute BoardWriter boardWriter, AttachFile attachFile) throws IOException {
        Map<String, Object> object = new HashMap<String, Object>();

        BoardVo beforeEdit = boardService.view(boardVo.getUuid());
        System.out.println(beforeEdit.getFile()+"file 내용 출력");

            if (beforeEdit.getAttachUid() != 0) {//테이블에 파일주소 이미 존재할때, 기존 데이터 삭제함

                awsS3Service.deleteFile(beforeEdit.getFile()); //S3에서 제거
                boardService.deleteAttachFile(beforeEdit.getAttachUid()); //attach_file tb에서 제거
                boardService.updateBoardAttach(beforeEdit.getUuid()); //board tb 업데이트
                System.out.println("<<<<<<<<<<<<<<<<<<<<<기존데이터 삭제완료");
            }

        String realName = boardWriter.getUploadFile().getOriginalFilename();
        String contentType = boardWriter.getUploadFile().getContentType();
        String extension = boardWriter.getUploadFile().getOriginalFilename().substring(boardWriter.getUploadFile().getOriginalFilename().lastIndexOf(".") + 1);
        long size = boardWriter.getUploadFile().getSize();

        object = awsS3Service.uploadFile(boardWriter.getUploadFile());

        String path = "https://moniquebucket.s3.ap-northeast-2.amazonaws.com/" + object.get("fileName").toString();

        attachFile.setFilePath(path);
        attachFile.setFileName(object.get("fileName").toString());
        attachFile.setFileContentType(contentType);
        attachFile.setFileRealName(realName);
        attachFile.setFileSize(size);
        attachFile.setFileExtension(extension);

        boardVo.setFile(object.get("fileName").toString());
        boardService.insertAttachFile(attachFile); //셀렉트키는 자동으로 attacFhile에 반환된다. (xml에 파라미터 설정을 했으므로)
        boardVo.setAttachUid(attachFile.getAttachUid());
        boardVo.setSubject(boardWriter.getTitle());
        boardVo.setContent(boardWriter.getTxtContent());
        boardVo.setEditDate(new Date());
        boardService.boardUpdate(boardVo);

        object.put("path", path);

        return object;

    }

    @PostMapping("/boardEditFileExist_KeepFile") //기존 첨부파일 있을때
    public Map<String, Object> boardEditFileExist_KeepFile(Model model, BoardVo boardVo, @ModelAttribute BoardWriter boardWriter, AttachFile attachFile) throws IOException {
        Map<String, Object> object = new HashMap<String, Object>();

        boardVo.setSubject(boardWriter.getTitle());
        boardVo.setContent(boardWriter.getTxtContent());
        boardVo.setEditDate(new Date());
        boardService.boardUpdate(boardVo);

        return object;

    }

    @PostMapping("/boardEdit")//첨부파일 없을때 실행(에디터만 수정됐을때)
    public Map<String, Object> boardEdit(Model model, BoardVo boardVo, @ModelAttribute BoardWriter boardWriter, AttachFile attachFile) throws IOException {
        Map<String, Object> object = new HashMap<String, Object>();

            boardVo.setSubject(boardWriter.getTitle());
            boardVo.setContent(boardWriter.getTxtContent());
            boardVo.setEditDate(new Date());
            boardVo.setFile("");
            boardService.boardUpdate(boardVo);

            object.put("path", null);

            return object;
    }


//파일 첨부 수정 전 edit 부분
//    @PostMapping("/boardEdit")
//    public SingleResult<?> boardEdit(BoardVo boardVo, @RequestBody BoardWriter boardWriter, HttpServletRequest request) throws IOException {
//        boardVo.setContent(boardWriter.getTxtContent());
//        boardVo.setSubject(boardWriter.getTitle());
//        boardVo.setUuid(boardWriter.getUuid());
//
//        boardService.boardUpdate(boardVo);
//        return responseService.getSuccessResult();
//    }


//일반 로컬에 업로드
//    @PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
//    @ResponseBody
//    public Object uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//
//        Map<String, Object> object = new HashMap<String, Object>();
//
//        String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
//        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
//
//        String savedFileName = originalFileName;	//저장될 파일 명
//
//        File targetFile = new File(fileRoot + savedFileName);
//
//        try {
//            InputStream fileStream = multipartFile.getInputStream();
//            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
//            object.put("url", "/summernoteImage/"+savedFileName);
//            object.put("responseCode", "success");
//
//        } catch (IOException e) {
//            FileUtils.forceDelete(targetFile);	//저장된 파일 삭제
//            object.put("responseCode", "error");
//            e.printStackTrace();
//        }
//
//        return object;
//    }

    //바로 S3업로드
    @PostMapping(value="/uploadSummernoteImageFileS3", produces = "application/json") //amazon s3
    @ResponseBody
    public Map<String, Object> uploadSummernoteImageFileS3(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        Map<String, Object> object = new HashMap<String, Object>();
        object = awsS3Service.uploadImage(multipartFile);

        return object;
    }

    @PostMapping(value="/deleteFileFromS3", produces = "application/json") //amazon s3
    @ResponseBody
    public Map<String, Object> deleteFileFromS3(@RequestParam("src") String src, @RequestParam("attachUid") int attachUid, @RequestParam("uuid") int uuid) throws IOException {
        Map<String, Object> object = new HashMap<String, Object>();

        String fileName = getFileNameFromURL(src);

        awsS3Service.deleteFile(fileName); //S3에서 제거
        boardService.deleteAttachFile(attachUid); //attach_file tb에서 제거
        boardService.updateBoardAttach(uuid); //board tb 업데이트

        return object;
    }

    public static String getFileNameFromURL(String url) {
        return url.substring(url.lastIndexOf('/') + 1, url.length());

    }

}
