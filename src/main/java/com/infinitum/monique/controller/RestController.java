package com.infinitum.monique.controller;


import com.amazonaws.services.s3.model.PutObjectRequest;
import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.domain.BoardWriter;
import com.infinitum.monique.domain.SingleResult;
import com.infinitum.monique.service.AwsS3Service;
import com.infinitum.monique.service.BoardService;
import com.infinitum.monique.service.ResponseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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
    public SingleResult<?> boardWrite(BoardVo boardVo, @RequestBody BoardWriter boardWriter, HttpServletRequest request, AttachFile attachFile) throws IOException {

        //Map<String, Object> object = new HashMap<String, Object>();
        System.out.println("<<<<<<<<<<<<<<<<<<<출력 테스트"+ boardVo);
        System.out.println("<<<<<<<<<<<<<<<<<<<출력 테스트"+ boardWriter);

        //파일이름 생성/////////////
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar dateTime = Calendar.getInstance();
        String uniqueId = sdf.format(dateTime.getTime()) + "."
                + boardWriter.getUploadFile().getOriginalFilename().substring(boardWriter.getUploadFile().getOriginalFilename().lastIndexOf(".") + 1);
        ///////////////////////////

        System.out.println("제목"+ boardWriter.getTitle());
        System.out.println("파일이름"+ uniqueId);
        System.out.println("첨부파일 본이름"+ boardWriter.getUploadFile().getOriginalFilename());
        System.out.println("첨부파일 컨텐트 타입"+ boardWriter.getUploadFile().getContentType());
        System.out.println("첨부파일 크기"+ boardWriter.getUploadFile().getSize());
        System.out.println("첨부파일 바이트"+ boardWriter.getUploadFile().getBytes());
        System.out.println("확장자"+boardWriter.getUploadFile().getOriginalFilename().substring(boardWriter.getUploadFile().getOriginalFilename().lastIndexOf(".") + 1));

        String realName = boardWriter.getUploadFile().getOriginalFilename();
        String contentType = boardWriter.getUploadFile().getContentType();
        String extension = boardWriter.getUploadFile().getOriginalFilename().substring(boardWriter.getUploadFile().getOriginalFilename().lastIndexOf(".") + 1);
        long size = boardWriter.getUploadFile().getSize();

        attachFile.setFileContentType(contentType);
        attachFile.setFileName(uniqueId);
        attachFile.setFileRealName(realName);
        attachFile.setFileSize(size);
        attachFile.setFileExtension(extension);

        boardVo.setName(boardWriter.getWriter());
        boardVo.setContent(boardWriter.getTxtContent());
        boardVo.setSubject(boardWriter.getTitle());
        boardVo.setFile(boardWriter.getUploadFile());

        boardService.boardWrite(boardVo);


        String path = awsS3Service.uploadFile(boardWriter.getUploadFile());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>실행 테스트");

        attachFile.setFilePath(path);//아마존 주소?
        boardService.attachFile(attachFile);

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
}
