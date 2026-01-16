package com.PayrollAutomation.PayrollAutomation.Model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.time.Instant;
import java.time.OffsetDateTime;

@Component
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "FileStores")
public class FileStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(nullable = false)
    private String FileName;
    @Column(nullable = false)
    private String FilePath;
    @Column(nullable = false)
    private byte[] FileContent;
    @Column(nullable = false)
    private int CreatedById;
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private Instant CreatedTime;
}
