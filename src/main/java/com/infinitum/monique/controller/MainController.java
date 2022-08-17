package com.infinitum.monique.controller;

import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.service.BoardService;
import com.infinitum.monique.service.ResponseService;
import com.infinitum.monique.service.Schedule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MainController {

    private final Schedule schedule;
    private final ResponseService responseService;
    private final BoardService boardService;


    public MainController(Schedule schedule, ResponseService responseService, BoardService boardService) {
        this.schedule = schedule;
        this.responseService = responseService;
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/schedule")
    public String test(){
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        schedule.scheduled();

        return "schedule";
    }
    @GetMapping("/editor")//글쓰기화면
    public String naverEditor(){
        return "editor";
    }

    @GetMapping("/list")
    public String boardList(Model model){
        List<BoardVo> board = boardService.listAll();
        model.addAttribute("board", board);

        return "list";
    }
    @GetMapping("/view/{uuid}")
    public String selectBoard(Model model, @PathVariable("uuid") String uuid){
        BoardVo view = boardService.listAllbyNum(uuid);

        model.addAttribute("view", view);

        return "view";
    }

    @GetMapping("/edit/{uuid}")
    public String editBoard(Model model, @PathVariable("uuid") String uuid){
        BoardVo view = boardService.listAllbyNum(uuid);

        model.addAttribute("view", view);

        return "edit";
    }




}
