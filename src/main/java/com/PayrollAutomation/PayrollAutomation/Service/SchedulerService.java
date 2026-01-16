package com.PayrollAutomation.PayrollAutomation.Service;

import com.PayrollAutomation.PayrollAutomation.Model.Salary;
import com.PayrollAutomation.PayrollAutomation.Repository.SalaryRepo;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SchedulerService {

    @Autowired
    AsyncService service;

    @Autowired
    SalaryRepo salaryRepo;

    // Scheduler runs every 30seconds if @Scheduled(fixedRate=30000)
    @Scheduled(cron = "0 * * * * *")
    public void runBatchScheduler() throws Exception {
        System.out.println("Scheduler triggered - main thread: " + Thread.currentThread().getName());
        List<CompletableFuture> futures=new ArrayList<>();
        int pageSize=10;
        long totalRecords=salaryRepo.countByIsCurrentTrue();
        int totalPages=(int) Math.ceil((double)totalRecords/pageSize);
        for (int i = 0; i < totalPages; i++) {
            Pageable pageable= PageRequest.of(i,pageSize);
            Page<Salary> salaryPage=salaryRepo.findAllByIsCurrentTrue(pageable);
            List<Salary> salaryList = salaryPage.getContent();
            var future=service.processBatch(salaryList,i+1); // async method
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("Batch Processing Completed");
    }

}
