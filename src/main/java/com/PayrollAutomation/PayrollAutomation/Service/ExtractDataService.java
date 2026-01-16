package com.PayrollAutomation.PayrollAutomation.Service;

import com.PayrollAutomation.PayrollAutomation.Model.Employee;
import com.PayrollAutomation.PayrollAutomation.Model.Salary;
import com.PayrollAutomation.PayrollAutomation.Repository.EmployeeRepo;
import com.PayrollAutomation.PayrollAutomation.Repository.SalaryRepo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ExtractDataService {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    SalaryRepo salaryRepo;

    @Async("batchExecutor")
    public CompletableFuture<Void> extractEmployeeData(Row row, List<Employee> employeeList) {
        Employee employee=new Employee();
        employee.setName(row.getCell(0).getStringCellValue());
        employee.setActive(row.getCell(1).getBooleanCellValue());
        employee.setCreatedById(1);
        employee.setDateOfBirth(row.getCell(2).getDateCellValue());
        employee.setAddress(row.getCell(3).getStringCellValue());
        employee.setDesignation(row.getCell(4).getStringCellValue());
        employeeList.add(employee);
        return CompletableFuture.completedFuture(null);
    }

    @Async("batchExecutor")
    public CompletableFuture<Void> extractSalaryData(Row row,List<Salary> salaryList) throws IOException {
        Salary salary=new Salary();
        int empId= (int) row.getCell(0).getNumericCellValue();
        if(!employeeRepo.existsById(empId)){
            throw new RuntimeException("Employee Id not found: "+empId);
        }
        Employee employee = employeeRepo.findById(empId);
        salary.setEmployee(employee);
        salary.setPayPeriod(row.getCell(1).getStringCellValue());
        salary.setNetPay(row.getCell(2).getNumericCellValue());
        salary.setBasicPay(row.getCell(3).getNumericCellValue());
        salary.setTax(row.getCell(4).getNumericCellValue());
        salary.setPf(row.getCell(5).getNumericCellValue());
        salary.setHra(row.getCell(6).getNumericCellValue());
        salary.setAllowances(row.getCell(7).getNumericCellValue());
        salary.setOtherDeductions(row.getCell(8).getNumericCellValue());
        salary.setYear(2025);

        Salary salary2=new Salary();
        salary2=salaryRepo.getEmployeeCurrentSalary(empId);
        if(salary2!=null) {
            salary2.setisCurrent(false);
            salaryRepo.save(salary2);
        }
        salary.setisCurrent(true);
        salaryList.add(salary);
        return CompletableFuture.completedFuture(null);
    }
}
