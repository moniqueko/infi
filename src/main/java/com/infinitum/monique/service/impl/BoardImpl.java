package com.infinitum.monique.service.impl;

import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.mapper.BoardMapper;
import com.infinitum.monique.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BoardImpl implements BoardService {

    private BoardService boardService;

    @Autowired
    BoardMapper mapper;

    @Override
    public BoardVo boardWrite(BoardVo boardVo) {
        mapper.boardWrite(boardVo);

        return boardVo;
    }

    @Override
    public BoardVo boardSummerWrite(BoardVo boardVo) {
        mapper.boardSummerWrite(boardVo);
        return boardVo;
    }

    @Override
    public void deleteAttachFile(int attachUid) {
        mapper.deleteAttachFile(attachUid);
        System.out.println("AttachFile 삭제완료");
    }

    @Override
    public List<BoardVo> listAll() {
        List<BoardVo> board = mapper.listAll();
        return board;
    }

    @Override
    public List<BoardVo> listAllSummer() {
        List<BoardVo> board = mapper.listAllSummer();
        return board;
    }

    @Override
    public BoardVo listAllbyNum(int uuid) {
        BoardVo board = mapper.listAllbyNum(uuid);
        return board;
    }

    @Override
    public BoardVo view(int uuid) {
        BoardVo board = mapper.view(uuid);
        return board;
    }

    @Override
    public BoardVo viewSummer(int uuid) {
        BoardVo board = mapper.viewSummer(uuid);
        return board;
    }

    @Override
    public List<AttachFile> viewAttachFiles(int uuid) {
        List<AttachFile> attach = mapper.viewAttachFiles(uuid);
        return attach;
    }

    @Override
    public void boardUpdate(BoardVo boardVo) {
        mapper.updateBoard(boardVo);
    }

    @Override
    public void insertAttachFile(AttachFile attachFile) {
        mapper.insertAttachFile(attachFile);
    }

    @Override
    public void updateBoardAttach(int uuid) { //파일첨부 삭제시

        BoardVo newBoard = new BoardVo(uuid, "", new Date(),0);
        mapper.updateBoardAttach(newBoard);

    }

    @Override
    public void insertSummerAttachFile(AttachFile attachFile) {
        mapper.insertSummerAttachFile(attachFile);
    }

    @Override
    public void deleteSummerAttachFile(int attachUid) {
        mapper.deleteSummerAttachFile(attachUid);
    }

    @Override
    public void updateBoardSummer(BoardVo boardVo) {
        mapper.updateBoardSummer(boardVo);
    }

    @Override
    public AttachFile viewAttachFileByuid(int attachUid) {
        AttachFile attach = mapper.viewAttachFileByuid(attachUid);
        return attach;
    }
}
