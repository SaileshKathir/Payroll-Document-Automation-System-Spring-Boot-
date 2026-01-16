package com.PayrollAutomation.PayrollAutomation.Service;

import com.PayrollAutomation.PayrollAutomation.Model.Salary;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    @Autowired
    ReportService reportService;

    @Autowired
    FileStoreService fileStoreService;

    private static final String STORAGE_DIRECTORY="D:\\sailesh\\FileStore\\SalaryPaySlipReports";

    @Async("batchExecutor")
    public CompletableFuture<Void> processBatch(List<Salary> salaryList, int batchNumber) throws Exception {
        try{
            for(Salary salary:salaryList){
                int empId=salary.getEmployee().getId();
                System.out.println(Thread.currentThread().getName()+" BatchNumber: "+batchNumber+" empId"+empId);
                byte[] pdf=reportService.generatePaySlip(empId);
                String fileName=empId+"_"+salary.getPayPeriod()+"_"+salary.getYear();
                Path filePath=Paths.get(STORAGE_DIRECTORY).resolve(fileName).normalize();
                Files.write(filePath,pdf);
            }
        }
        catch (Exception ex){
            throw new Exception("Issue in:"+AsyncService.class.getName()+ex);
        }
        System.out.println(batchNumber+" Processing Completed");
        return CompletableFuture.completedFuture(null);
    }
}
