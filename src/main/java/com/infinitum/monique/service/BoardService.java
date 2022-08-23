package com.infinitum.monique.service;

import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;

import java.util.List;

public interface BoardService {

    void boardWrite(BoardVo boardVo);
    List<BoardVo> listAll();
    BoardVo listAllbyNum(String uuid);

    void boardUpdate(BoardVo boardVo);

    void attachFile(AttachFile attachFile);
}
