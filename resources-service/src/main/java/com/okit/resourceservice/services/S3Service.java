package com.okit.resourceservice.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface S3Service
{
    @Async
    void uploadFiles(List<MultipartFile> files, String directory) throws IOException;

    String uploadFile(String fileName, MultipartFile file) throws IOException;

    @Async
    void deleteFile(String fileName);
}
