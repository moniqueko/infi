package com.infinitum.monique.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AttachFile {

    private int attachUid;           /* 첨부파일 식별번호 */
    private int uuid;
    private String fileName;            /* 첨부파일 이름 */
    private String fileRealName;        /* 첨부파일 본이름 */
    private long fileSize;            /* 첨부크기 */
    private String filePath;            /* 첨부파일 경로 */
    private String fileExtension;       /* 첨부파일 확장자 */
    private String fileContentType;     /* 첨부파일 타입 */
    private Date fileRegiDate;        /* 등록일시 */
    private Date fileEditDate;        /* 수정일시 */

    private String name;
    private String subject;
    private String content;
    private String file;
    private Date editDate;
    private Date regiDate;
    private int inuse;

    public AttachFile() {}

    public AttachFile(int attachUid, int uuid, String fileName, String fileRealName, long fileSize,
                      String filePath, String fileExtension, String fileContentType, Date fileRegiDate, Date fileEditDate) {
        super();
        this.attachUid = attachUid;
        this.uuid = uuid;
        this.fileName = fileName;
        this.fileRealName = fileRealName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.fileContentType = fileContentType;
        this.fileRegiDate = fileRegiDate;
        this.fileEditDate = fileEditDate;
    }

    public AttachFile(int uuid, String fileName, String fileRealName, long fileSize, String filePath, String fileExtension,
                      String fileContentType, Date fileEditDate) {
        super();
        this.uuid = uuid;
        this.fileName = fileName;
        this.fileRealName = fileRealName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.fileContentType = fileContentType;
        this.fileEditDate = fileEditDate;
    }

}