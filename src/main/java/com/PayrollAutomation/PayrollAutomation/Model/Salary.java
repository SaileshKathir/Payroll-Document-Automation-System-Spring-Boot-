package com.PayrollAutomation.PayrollAutomation.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String payPeriod;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private double basicPay;
    @Column(nullable = false)
    private double hra;
    private double allowances;
    @Column(nullable = false)
    private double pf;
    @Column(nullable = false)
    private double tax;
    private double otherDeductions;
    @Column(nullable = false)
    private double netPay;
    @ManyToOne
    @JoinColumn(name = "employeeId",foreignKey = @ForeignKey(name = "FK_Salary_Employee"))
    private Employee employee;
    @Column(nullable = false)
    private boolean isCurrent;

    public void setisCurrent(boolean b) {
        this.isCurrent=b;
    }
}
