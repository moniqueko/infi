package com.infinitum.monique.mapper;

import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void boardWrite(BoardVo boardVo);
    void boardSummerWrite(BoardVo boardVo);

    void deleteAttachFile(int attachUid);
    void updateBoardAttach(BoardVo boardVo);
    List<BoardVo> listAll();

    List<AttachFile> viewAttachFiles(int uuid);
    List<BoardVo> listAllSummer();
    BoardVo listAllbyNum(int uuid);
    BoardVo view(int uuid);
    BoardVo viewSummer(int uuid);

    void updateBoard(BoardVo boardVo);
    void insertAttachFile(AttachFile attachFile);
    void insertSummerAttachFile(AttachFile attachFile);

}
