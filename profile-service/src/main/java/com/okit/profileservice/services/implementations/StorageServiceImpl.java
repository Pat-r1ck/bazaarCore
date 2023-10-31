package com.okit.profileservice.services.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.okit.profileservice.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService
{
    @Override
    public void uploadFile(String fileName, File file)
    {
        s3Client.putObject(
                new PutObjectRequest(bucketName,fileName,file)
        );
    }

    @Override
    public void deleteFile(String fileName)
    {
        s3Client.deleteObject(
                new DeleteObjectRequest(bucketName + "/", fileName)
        );
    }

    @Autowired
    private final AmazonS3 s3Client;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;
}
