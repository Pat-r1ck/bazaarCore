package com.okit.profileservice.services;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface S3Service
{
    void uploadFile(String fileName, File file);
    void deleteFile(String fileName);
}
