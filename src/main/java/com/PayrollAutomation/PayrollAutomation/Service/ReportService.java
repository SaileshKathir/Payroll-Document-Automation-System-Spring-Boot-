package com.PayrollAutomation.PayrollAutomation.Service;

import com.PayrollAutomation.PayrollAutomation.Model.Employee;
import com.PayrollAutomation.PayrollAutomation.Model.Salary;
import com.PayrollAutomation.PayrollAutomation.Repository.EmployeeRepo;
import com.PayrollAutomation.PayrollAutomation.Repository.SalaryRepo;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {

    private final EmployeeRepo employeeRepo;
    private final SalaryRepo salaryRepo;

    public byte[] generatePaySlip(int empId) throws IOException {
        try {
            // 1️⃣ Fetch data from DB
            Employee employee = employeeRepo.findById(empId);
            Salary salary = salaryRepo.getEmployeeCurrentSalary(empId);
            // 3️⃣ Load JRXML file from resources
            InputStream reportStream = getClass().getResourceAsStream("/employees_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // 4️⃣ Parameters map (empty for now, can add companyName etc.)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("companyName", "Chapter Inc");
            parameters.put("companyAddress", "Tirunelveli");

            // Employee Details from Employee Model
            parameters.put("employeeName", employee.getName());
            parameters.put("employeeId", employee.getId());
            parameters.put("designation", employee.getDesignation());

            // Salary Details from Salary Model
            parameters.put("payPeriod", salary.getPayPeriod());
            parameters.put("basicPay", salary.getBasicPay());
            parameters.put("hra", salary.getHra());
            parameters.put("allowances", salary.getAllowances());
            parameters.put("pf", salary.getPf());
            parameters.put("tax", salary.getTax());
            parameters.put("otherDeductions", salary.getOtherDeductions());
            parameters.put("netPay", salary.getNetPay());

            // 5️⃣ Fill report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // 6️⃣ Export to PDF as byte array
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
        catch (Exception ex){
            throw new IOException("There is an issue in generating a report:"+AsyncService.class.getName()+ex);
        }
    }
}
