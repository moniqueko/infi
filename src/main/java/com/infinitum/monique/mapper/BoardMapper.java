package com.infinitum.monique.mapper;

import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.domain.Criteria;
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
    List<BoardVo> listAllSummer(Criteria cri);

    int selectCount();

    BoardVo listAllbyNum(int uuid);
    BoardVo view(int uuid);
    BoardVo viewSummer(int uuid);

    void updateBoard(BoardVo boardVo);
    void insertAttachFile(AttachFile attachFile);
    void insertSummerAttachFile(AttachFile attachFile);
    void deleteSummerAttachFile(int attachUid);
    void updateBoardSummer(BoardVo boardVo);

    AttachFile viewAttachFileByuid(int attachUid);

    int selectCountPaging(String keyword);

}
