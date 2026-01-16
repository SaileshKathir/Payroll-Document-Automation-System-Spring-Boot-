package com.PayrollAutomation.PayrollAutomation.Controller;

import com.PayrollAutomation.PayrollAutomation.Model.Salary;
import com.PayrollAutomation.PayrollAutomation.Repository.SalaryRepo;
import com.PayrollAutomation.PayrollAutomation.Service.SalaryService;
import net.sf.jasperreports.engine.util.JRStyledText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class SalaryController {

    @Autowired
    private SalaryService service;

    private static final Logger log=Logger.getLogger(SalaryController.class.getName());

    @PostMapping("/upload-salary")
    public ResponseEntity<String> uploadSalary(@RequestParam("file") MultipartFile file) throws IOException {
        if(!(service.isValidFile(file))){
            return ResponseEntity.ok("Please upload a valid file.");
        }
        try{;
            service.uploadSalary(file);
            return ResponseEntity.ok("Employee Salary Processed Successfully");
        }
        catch(Exception ex){
            log.log(Level.SEVERE,"Invalid File:",ex);
            return ResponseEntity.ok("Invalid File: "+ex);
        }
    }
}
