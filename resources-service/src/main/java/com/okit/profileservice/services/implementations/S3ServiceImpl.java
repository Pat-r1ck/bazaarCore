package com.okit.profileservice.services.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.okit.profileservice.constants.S3Constants;
import com.okit.profileservice.exceptions.MissMatchFileException;
import com.okit.profileservice.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service
{
    @Async
    @Override
    public void uploadFiles(List<String> fileNames, List<MultipartFile> files) throws IOException
    {
        int fileNamesSize = fileNames.size();
        int filesSize = files.size();

        if(fileNamesSize != filesSize)
        {
            throw new MissMatchFileException(String.format(S3Constants.MISS_MATCH_FILE_MSG, fileNamesSize, filesSize));
        }

        for(int i = 0 ; i < filesSize ; ++i)
        {
            String fileName = fileNames.get(i);
            MultipartFile file = files.get(i);

            uploadFile(fileName, file);
        }
    }

    @Async
    @Override
    public void uploadFile(String fileName, MultipartFile file) throws IOException
    {
        File image = convertImageToFile(file);

        s3.putObject(
                new PutObjectRequest(bucketName,fileName,image)
        );

        image.delete();
    }

    @Async
    @Override
    public void deleteFiles(List<String> fileNames)
    {
        for(String fileName : fileNames)
        {
            deleteFile(fileName);
        }
    }

    @Async
    @Override
    public void deleteFile(String fileName)
    {
        s3.deleteObject(
                new DeleteObjectRequest(bucketName, fileName)
        );
    }

    private File convertImageToFile(MultipartFile file) throws IOException
    {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

        FileOutputStream fos = new FileOutputStream(convFile);

        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }

    @Autowired
    private final AmazonS3 s3;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;
}
