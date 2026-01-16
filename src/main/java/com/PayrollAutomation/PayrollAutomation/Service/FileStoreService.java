package com.PayrollAutomation.PayrollAutomation.Service;

import com.PayrollAutomation.PayrollAutomation.Model.FileStore;
import com.PayrollAutomation.PayrollAutomation.Repository.FileStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.OffsetDateTime;

@Service
public class FileStoreService {

    @Autowired
    FileStore fileStore;
    @Autowired
    FileStoreRepo repo;

    public void createLog(File file) throws IOException {
        fileStore.setFileName(file.getName());
        fileStore.setFilePath(file.getPath());
        fileStore.setCreatedById(1);
        fileStore.setFileContent(Files.readAllBytes(file.toPath()));
        repo.save(fileStore);
    }
}
