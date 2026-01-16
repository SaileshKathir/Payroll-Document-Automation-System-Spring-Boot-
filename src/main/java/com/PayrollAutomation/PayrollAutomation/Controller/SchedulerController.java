package com.PayrollAutomation.PayrollAutomation.Controller;

import com.PayrollAutomation.PayrollAutomation.Service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class SchedulerController {

    private static final Logger log=Logger.getLogger(SchedulerController.class.getName());

    @Autowired
    SchedulerService service;

    @PostMapping("/scheduler")
    @ResponseBody
    public void scheduler(){
        try {
            service.runBatchScheduler();
        }
        catch (Exception ex){
            log.log(Level.SEVERE,"There is an error in scheduler Task"+ex);
        }
    }
}
