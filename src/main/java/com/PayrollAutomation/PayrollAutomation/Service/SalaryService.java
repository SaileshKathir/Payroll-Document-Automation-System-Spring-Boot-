package com.PayrollAutomation.PayrollAutomation.Service;

import com.PayrollAutomation.PayrollAutomation.Model.Salary;
import com.PayrollAutomation.PayrollAutomation.Repository.SalaryRepo;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class SalaryService {

    @Autowired
    SalaryRepo salaryRepo;

    @Autowired
    ExtractDataService extractDataService;

    @Autowired
    FileStoreService fileStoreService;

    private static final String STORAGE_DIRECTORY = "D:\\sailesh\\FileStore\\InboundArchive";

    public void uploadSalary(MultipartFile salaryFile) throws IOException {
        var file=new File(STORAGE_DIRECTORY+File.separator+salaryFile.getOriginalFilename());
        if(!Objects.equals(STORAGE_DIRECTORY,file.getParent())){
            throw new SecurityException("Please change the file name");
        }
        Files.copy(salaryFile.getInputStream(),file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        try{
            extractSalaryData(salaryFile.getInputStream());
            fileStoreService.createLog(file);
        }
        catch(Exception ex){
            throw new IOException("There is an issue in generating a report:"+SalaryService.class.getName()+"Please review the data inside: "+salaryFile.getOriginalFilename()+" "+ex);
        }
    }

    private void extractSalaryData(InputStream file) throws IOException {
        List<Salary> salaryList= Collections.synchronizedList(new ArrayList<>());
        Workbook workbook= WorkbookFactory.create(file);
        Sheet sheet=workbook.getSheetAt(0);
        List<CompletableFuture> futures=new ArrayList<>();
        sheet.forEach(row->{
            if(row.getRowNum()!=0){
                try {
                    var future=extractDataService.extractSalaryData(sheet.getRow(row.getRowNum()),salaryList);
                    futures.add(future);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            salaryRepo.saveAll(salaryList);
        });
        salaryRepo.saveAll(salaryList);
    }

    public boolean isValidFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
}