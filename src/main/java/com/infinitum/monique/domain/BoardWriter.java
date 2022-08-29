package com.infinitum.monique.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriter implements Serializable {
    private String writer;
    private String title;
    private String txtContent;
    private int uuid;
    private MultipartFile uploadFile;
    private List<MultipartFile> uploadFiles;

    private int attachUid;           /* 첨부파일 식별번호 */
    private String fileName;            /* 첨부파일 이름 */
    private String fileRealName;        /* 첨부파일 본이름 */
    private long fileSize;            /* 첨부크기 */
    private String filePath;            /* 첨부파일 경로 */
    private String fileExtension;       /* 첨부파일 확장자 */
    private String fileContentType;     /* 첨부파일 타입 */
    private Date fileRegiDate;        /* 등록일시 */
    private Date fileEditDate;        /* 수정일시 */

}
