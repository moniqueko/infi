package com.infinitum.monique.service.impl;

import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.mapper.BoardMapper;
import com.infinitum.monique.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardImpl implements BoardService {
    @Autowired
    BoardMapper mapper;

    @Override
    public void boardWrite(BoardVo boardVo) {
    mapper.boardWrite(boardVo);
    }

    @Override
    public List<BoardVo> listAll() {
        List<BoardVo> board = mapper.listAll();
        return board;
    }

    @Override
    public BoardVo listAllbyNum(String uuid) {
        BoardVo board = mapper.listAllbyNum(uuid);
        return board;
    }
}
