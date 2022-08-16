package com.infinitum.monique.controller;

import com.infinitum.monique.service.Schedule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MainController {
    private final Schedule schedule;

    public MainController(Schedule schedule) {
        this.schedule = schedule;
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

}
