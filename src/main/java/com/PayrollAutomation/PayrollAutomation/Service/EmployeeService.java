package com.PayrollAutomation.PayrollAutomation.Service;

import com.PayrollAutomation.PayrollAutomation.Model.Employee;
import com.PayrollAutomation.PayrollAutomation.Repository.EmployeeRepo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeService {

    private static final String STORAGE_DIRECTORY = "D:\\sailesh\\FileStore\\InboundArchive";

    @Autowired
    FileStoreService fileStoreService;

    @Autowired
    EmployeeRepo repo;

    @Autowired
    ExtractDataService extractDataService;
    
    public void uploadEmployeeDetails(MultipartFile employeeDetailsFile) throws IOException {
        var file=new File(STORAGE_DIRECTORY+File.separator+employeeDetailsFile.getOriginalFilename());
        if(!Objects.equals(STORAGE_DIRECTORY,file.getParent())){
            throw new SecurityException("Please change the file name");
        }
        Files.copy(employeeDetailsFile.getInputStream(),file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        try{
            extractEmployeeData(employeeDetailsFile.getInputStream());
            fileStoreService.createLog(file);
        }
        catch(Exception ex){
            throw new IOException("Issue in:"+EmployeeService.class.getName()+ex);
        }
    }

    private void extractEmployeeData(InputStream file) throws IOException {
        List<Employee> employeeList= Collections.synchronizedList(new ArrayList<>());
        Workbook workbook= WorkbookFactory.create(file);
        Sheet sheet1=workbook.getSheetAt(0);
        List<CompletableFuture> futures=new ArrayList<>();
        sheet1.forEach(row->{
            if(row.getRowNum()!=0){
                var future= extractDataService.extractEmployeeData(sheet1.getRow(row.getRowNum()),employeeList);
                futures.add(future);
            }
        });
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        repo.saveAll(employeeList);
    }


    public boolean isValidFile(MultipartFile employeeDetailsFile) {
        return Objects.equals(employeeDetailsFile.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
}
