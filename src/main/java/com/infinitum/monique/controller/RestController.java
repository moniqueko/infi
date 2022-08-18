package com.infinitum.monique.controller;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.domain.BoardWriter;
import com.infinitum.monique.domain.SingleResult;
import com.infinitum.monique.service.AwsS3Service;
import com.infinitum.monique.service.BoardService;
import com.infinitum.monique.service.ResponseService;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    @PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public SingleResult<?> uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        awsS3Service.uploadImage(multipartFile);

        System.out.println("실행 테스트3");
        return  responseService.getSuccessResult();
    }
}
