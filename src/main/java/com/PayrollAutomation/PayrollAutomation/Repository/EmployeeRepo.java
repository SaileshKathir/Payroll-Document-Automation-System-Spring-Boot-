package com.PayrollAutomation.PayrollAutomation.Repository;

import com.PayrollAutomation.PayrollAutomation.Model.Employee;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Id> {
    Employee findById(int empId);

    boolean existsById(int empId);
}
