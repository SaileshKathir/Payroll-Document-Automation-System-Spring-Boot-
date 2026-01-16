package com.PayrollAutomation.PayrollAutomation.Repository;

import com.PayrollAutomation.PayrollAutomation.Model.FileStore;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStoreRepo extends JpaRepository<FileStore, Id> {
}
