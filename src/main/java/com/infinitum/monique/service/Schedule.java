package com.infinitum.monique.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 30 * * * * ?")
    public void scheduled (){
        System.out.println("The time is now "+ dateFormat.format(new Date()));
    }
}
