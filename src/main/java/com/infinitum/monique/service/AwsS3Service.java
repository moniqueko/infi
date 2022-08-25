package com.infinitum.monique.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private static String bucket   = "moniquebucket";
    private final AmazonS3Client amazonS3Client;

//    public void upload (File file){
//
//        if (amazonS3 !=null){
//            try {
//                PutObjectRequest putObjectRequest =
//                        new PutObjectRequest(bucket+"/img/", file.getName(), file);
//                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
//                amazonS3.putObject(putObjectRequest);
//
//            }catch (AmazonServiceException ase){
//                ase.printStackTrace();
//            }finally {
//                amazonS3 = null;
//            }
//        }
//
//    }
    public Map<String, Object> uploadImage(MultipartFile file) {
        Map<String, Object> object = new HashMap<String, Object>();

//      AmazonS3 s3Client = new AmazonS3Client(credentials);

        AmazonS3 s3Client = amazonS3Client;

        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        String path = "https://moniquebucket.s3.ap-northeast-2.amazonaws.com/"+fileName;

        try(InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
        }
        object.put("path",path);

        return object;
    }

    public Map<String, Object> uploadFile(MultipartFile file) {
        Map<String, Object> object = new HashMap<String, Object>();

//      AmazonS3 s3Client = new AmazonS3Client(credentials);
        AmazonS3 s3Client = amazonS3Client;

        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            System.out.println("아마존 업로드 완료");

            object.put("fileName",fileName);

        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
        
        return object; //파일이름 반환
    }

    public void deleteImage(String fileName) {
        AmazonS3 s3Client = amazonS3Client;
        s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
//        //다운로드
//        public ResponseEntity<byte[]> getObject(String storedFileName) throws IOException{
//            S3Object o = amazonS3.getObject(new GetObjectRequest(bucket, storedFileName));
//            S3ObjectInputStream objectInputStream = o.getObjectContent();
//            byte[] bytes = IOUtils.toByteArray(objectInputStream);
//
//            String fileName = URLEncoder.encode(storedFileName, "UTF-8").replaceAll("\\+", "%20");
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            httpHeaders.setContentLength(bytes.length);
//            httpHeaders.setContentDispositionFormData("attachment", fileName);
//
//            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
//
//        }



}
