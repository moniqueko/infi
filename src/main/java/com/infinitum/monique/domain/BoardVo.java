package com.infinitum.monique.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
public class BoardVo {
    private int uuid;
    private String name;
    private String subject;
    private String content;
    private String file;
    private Date editDate;
    private Date regiDate;
    private int inuse;
    private String fileRealName;
    private String filePath;
    private int attachUid;

    public BoardVo(int uuid, String file, Date editDate, int attachUid) {
        this.uuid = uuid;
        this.file = file;
        this.editDate = editDate;
        this.attachUid = attachUid;
    }
}
