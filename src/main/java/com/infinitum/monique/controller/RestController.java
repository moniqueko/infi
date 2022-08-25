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


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/boardWrite")
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

    @PostMapping("/boardWriteSummer")
    public SingleResult<?> boardWrite(BoardVo boardVo, @RequestBody BoardWriter boardWriter, HttpServletRequest request) throws IOException {

        boardVo.setName(boardWriter.getWriter());
        boardVo.setContent(boardWriter.getTxtContent());
        boardVo.setSubject(boardWriter.getTitle());

        boardService.boardWrite(boardVo);
        return responseService.getSuccessResult();
    }

    @PostMapping("/boardEdit")
    public SingleResult<?> boardEdit(BoardVo boardVo, @RequestBody BoardWriter boardWriter, HttpServletRequest request) throws IOException {
        boardVo.setContent(boardWriter.getTxtContent());
        boardVo.setSubject(boardWriter.getTitle());
        boardVo.setUuid(boardWriter.getUuid());

        boardService.boardUpdate(boardVo);
        return responseService.getSuccessResult();
    }


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
