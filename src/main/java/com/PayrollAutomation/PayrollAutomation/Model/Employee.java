package com.PayrollAutomation.PayrollAutomation.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Table(name = "Employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private int createdById;
    @CreatedDate
    @Column(nullable = false)
    private Instant createdTime;
    @Column(nullable = false)
    private Date dateOfBirth;
    private String address;
    @Column(nullable = false)
    private String designation;
}
