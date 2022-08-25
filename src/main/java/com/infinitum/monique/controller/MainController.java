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
    @GetMapping("/editor")// 네이버 에디터
    public String naverEditor(){
        return "editor";
    }

    @GetMapping("/summerEditor")//썸머노트 에디터
    public String summerEditor(){
        return "summerEditor";
    }

    @GetMapping("/list")
    public String boardList(Model model){
        List<BoardVo> board = boardService.listAll();
        model.addAttribute("board", board);

        return "list";
    }
    @GetMapping("/boardList")
    public String boardListNaver(Model model){
        List<BoardVo> board = boardService.listAll();
        model.addAttribute("board", board);

        return "boardList";
    }

    @GetMapping("/view/{uuid}")
    public String selectBoard(Model model, @PathVariable("uuid") int uuid){
        BoardVo view = boardService.view(uuid);
        model.addAttribute("view", view);

        return "view";
    }

    @GetMapping("/view/naver/{uuid}")
    public String viewBoard(Model model, @PathVariable("uuid") int uuid){
        BoardVo view = boardService.view(uuid);

        model.addAttribute("view", view);

        return "boardView";
    }

//    @GetMapping("/edit/{uuid}") //네이버 에디터 수정
//    public String editBoard(Model model, @PathVariable("uuid") String uuid){
//        BoardVo view = boardService.listAllbyNum(uuid);
//
//        model.addAttribute("view", view);
//
//        return "edit";
//    }

    @GetMapping("/edit/{uuid}")
    public String editBoardNaver(Model model, @PathVariable("uuid") int uuid){
        BoardVo view = boardService.view(uuid);
        model.addAttribute("view", view);

        return "edit";
    }

    @GetMapping("/editSummer/{uuid}")
    public String editBoardSummer(Model model, @PathVariable("uuid") int uuid){
        BoardVo view = boardService.view(uuid);
        model.addAttribute("view", view);

        return "summerEdit";
    }




}
