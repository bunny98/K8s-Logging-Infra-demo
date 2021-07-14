package com.example.splunkdemo.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/")
public class RequestController {
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);
    private static int count = 0;

    RequestController(){
        Timer timer = new Timer();
        TimerTask task = new MyTimerTask();
        timer.schedule(task, 2000, 10000);
    }

    public String funct0(){
        try {
            int c = 10/0;
        } catch (Exception e) {
            LOGGER.error("ERROR: ", e);
        }finally {
            return "DIVIDE BY 0 EXCEPTION";
        }
    }
    public String funct1(){
        try {
            String a = null;
            System.out.println(a.charAt(0));
        } catch (Exception e) {
            LOGGER.error("ERROR: ", e);
        }finally {
            return "NULL POINTER EXCEPTION";
        }
    }
    public String funct2(){
        try {
            int num = Integer.parseInt ("kavyansh") ;
        } catch (Exception e) {
            LOGGER.error("ERROR: ", e);
        }finally {
           return "NUMBER FORMAT EXCEPTION";
        }
    }

    public String permanent(int num){
        switch (num){
            case 0:
                return funct0();
            case 1:
                return funct1();
            case 2:
                return funct2();

        }
        return "EMPTY";
    }

    @GetMapping("/genError")
     public String logError(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
        return permanent(randomNum);
     }

     @GetMapping("/log")
    public String logMessage(){
        count++;
        LOGGER.info("LOGGED MESSAGE NUMBER "+count);
        return "LOGGED A MESSAGE!";
     }

    @GetMapping("/mulLog")
    public String multiLineLogMessage(){
        count++;
        LOGGER.info("LOGGED MESSAGE NUMBER "+count+" \n This is a multiline log \n Again new line \n Again");
        return "LOGGED A MULTILINE MESSAGE!";
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
            switch (randomNum){
                case 0:
                    logError();
                    break;
                case 1:
                    logMessage();
                    break;
                case 2:
                    multiLineLogMessage();
                    break;
            }
        }
    }
}
