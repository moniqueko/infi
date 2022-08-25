package com.infinitum.monique.service;

import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;

import java.util.List;

public interface BoardService {

    void boardWrite(BoardVo boardVo);

    void deleteAttachFile(int attachUid);

    List<BoardVo> listAll();
    BoardVo listAllbyNum(int uuid);
    BoardVo view(int uuid);

    void boardUpdate(BoardVo boardVo);

    void insertAttachFile(AttachFile attachFile);

    void updateBoardAttach(int uuid);
}
