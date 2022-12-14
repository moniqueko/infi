package com.infinitum.monique.controller;

import com.infinitum.monique.domain.AttachFile;
import com.infinitum.monique.domain.BoardVo;
import com.infinitum.monique.domain.Criteria;
import com.infinitum.monique.domain.PageMaker;
import com.infinitum.monique.service.BoardService;
import com.infinitum.monique.service.ResponseService;
import com.infinitum.monique.service.Schedule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/summerList")//여기에만 페이징, 검색 적용
    public String summerList(@RequestParam(value="keyword",required = false) String keyword, Model model, Criteria cri) {
        if (keyword == null) {

            List<BoardVo> board = boardService.listAllSummer(cri);
            model.addAttribute("board", board);

            PageMaker pm = new PageMaker();
            pm.setCri(cri);
            pm.setTotalCount(boardService.selectCount());

            model.addAttribute("pm", pm);

            return "summerList";

        } else if (keyword != null) {
            model.addAttribute("board", boardService.listAllSummer(cri)); //criteria에 keyword 포함.

            PageMaker pm = new PageMaker();
            pm.setCri(cri);
            pm.setKeyword(keyword);
            pm.setTotalCount(boardService.selectCountPaging(keyword));

            model.addAttribute("pm", pm);
            model.addAttribute("keyword", keyword);

            return "summerList";
        }
        return "summerList";
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

    @GetMapping("/view/summer/{uuid}")
    public String selectSummerBoard(Model model, @PathVariable("uuid") int uuid){
        BoardVo view = boardService.viewSummer(uuid);
        model.addAttribute("view", view);

        List<AttachFile> attach = boardService.viewAttachFiles(uuid);
        model.addAttribute("attach", attach);

        return "summerView";
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

    @GetMapping("/editSummerEditor/{uuid}") //다중파일 업로드용
    public String editSummer(Model model, @PathVariable("uuid") int uuid){
        BoardVo view = boardService.viewSummer(uuid);
        model.addAttribute("view", view);

        List<AttachFile> attach = boardService.viewAttachFiles(uuid);
        model.addAttribute("attach", attach);

        return "summerEditorEdit";
    }




}
