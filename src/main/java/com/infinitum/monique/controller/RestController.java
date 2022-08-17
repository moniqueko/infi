package com.infinitum.monique.controller;

import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.domain.BoardWriter;
import com.infinitum.monique.domain.SingleResult;
import com.infinitum.monique.service.BoardService;
import com.infinitum.monique.service.ResponseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@org.springframework.web.bind.annotation.RestController

public class RestController {
    private final ResponseService responseService;
    private final BoardService boardService;

    public RestController(ResponseService responseService, BoardService boardService) {
        this.responseService = responseService;
        this.boardService = boardService;
    }

    @PostMapping("/boardWrite")
    public SingleResult<?> boardWrite(BoardVo boardVo, @RequestBody BoardWriter boardWriter, HttpServletRequest request) throws IOException {
        System.out.println("<<<<<<<<<<폼에서 받아온 정보");
        System.out.println(boardWriter);
        boardVo.setName(boardWriter.getWriter());
        boardVo.setContent(boardWriter.getTxtContent());
        boardVo.setSubject(boardWriter.getTitle());

        boardService.boardWrite(boardVo);
        System.out.println("<<<<<<<<<<실행완료");

        return responseService.getSuccessResult();
    }
}
