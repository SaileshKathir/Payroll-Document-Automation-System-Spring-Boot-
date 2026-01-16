package com.PayrollAutomation.PayrollAutomation.Repository;

import com.PayrollAutomation.PayrollAutomation.Model.Employee;
import com.PayrollAutomation.PayrollAutomation.Model.Salary;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepo extends JpaRepository<Salary, Id> {

    Salary findById(int salaryId);

    @Query("select s from Salary s where s.employee.id=?1 and s.isCurrent=true")
    Salary getEmployeeCurrentSalary(int i);

    long countByIsCurrentTrue();

    Page<Salary> findAllByIsCurrentTrue(Pageable pageable);
}