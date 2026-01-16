package com.PayrollAutomation.PayrollAutomation.Controller;
import com.PayrollAutomation.PayrollAutomation.Model.Employee;
import com.PayrollAutomation.PayrollAutomation.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class EmployeeController {

    private static final Logger log=Logger.getLogger(EmployeeController.class.getName());

    @Autowired
    EmployeeService service;

    @PostMapping("/upload-Employee-details")
    @ResponseBody
    public ResponseEntity<String> uploadEmployeeDetails(@RequestParam("file") MultipartFile employeeDetailsFile){
        if(!(service.isValidFile(employeeDetailsFile))){
            return ResponseEntity.ok("Please upload a valid file.");
        }
        try{
            service.uploadEmployeeDetails(employeeDetailsFile);
            return ResponseEntity.ok("File processing completed successfully");
        }
        catch(Exception ex){
            log.log(Level.SEVERE,"There is an issue with the uploaded file",ex);
            return ResponseEntity.ok("Failed to process the file due to the error:"+ex);
        }
    }
}
