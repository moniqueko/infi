package com.infinitum.monique.mapper;

import com.infinitum.monique.domain.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void boardWrite(BoardVo boardWriter);
    List<BoardVo> listAll();
    BoardVo listAllbyNum(String uuid);
}
