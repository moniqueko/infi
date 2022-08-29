package com.infinitum.monique.service;

import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;

import java.util.List;

public interface BoardService {

    BoardVo boardWrite(BoardVo boardVo);

    BoardVo boardSummerWrite(BoardVo boardVo);

    void deleteAttachFile(int attachUid);

    List<BoardVo> listAll();

    List<BoardVo> listAllSummer();
    BoardVo listAllbyNum(int uuid);
    BoardVo view(int uuid);

    BoardVo viewSummer(int uuid);
    List<AttachFile> viewAttachFiles(int uuid);
    void boardUpdate(BoardVo boardVo);

    void insertAttachFile(AttachFile attachFile);

    void updateBoardAttach(int uuid);

    void insertSummerAttachFile(AttachFile attachFile);
}
