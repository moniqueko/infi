package com.infinitum.monique.mapper;

import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void boardWrite(BoardVo boardVo);
    List<BoardVo> listAll();
    BoardVo listAllbyNum(String uuid);
    BoardVo view(String uuid);
    void updateBoard(BoardVo boardVo);
    void insertAttachFile(AttachFile attachFile);

}
