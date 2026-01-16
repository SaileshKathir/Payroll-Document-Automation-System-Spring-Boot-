package com.PayrollAutomation.PayrollAutomation;

import com.PayrollAutomation.PayrollAutomation.Model.Employee;
import com.PayrollAutomation.PayrollAutomation.Repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PayrollAutomationApplication {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(PayrollAutomationApplication.class, args);
	}

}
