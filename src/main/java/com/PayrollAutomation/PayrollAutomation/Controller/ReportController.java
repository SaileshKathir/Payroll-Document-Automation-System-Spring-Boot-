package com.PayrollAutomation.PayrollAutomation.Controller;

import com.PayrollAutomation.PayrollAutomation.Service.ReportService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private static final String STORAGE_DIRECTORY="D:\\sailesh\\FileStore\\SalaryPaySlipReports";

    @GetMapping("/employees/report")
    public ResponseEntity<Resource> downloadEmployeeReport(@RequestParam("empId") String empId,@RequestParam("payPeriod") String payPeriod,@RequestParam("year") String year) {
        try {
            String fileName=empId+"_"+payPeriod+"_"+year;
            Path filePath= Paths.get(STORAGE_DIRECTORY).resolve(fileName).normalize();
            Resource resource= new UrlResource(filePath.toUri());
            if(!resource.exists()){
                ResponseEntity.notFound().build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+fileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
