package com.okit.profileservice.services.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
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
    public void uploadFiles(List<MultipartFile> files, String directory) throws IOException
    {
        List<File> images = convertImagesToFiles(files);

        MultipleFileUpload upload = transferManager.uploadFileList(
                bucketName, directory, new File("./"),images
        );

        try
        {
            upload.waitForCompletion();

            images.forEach(File::delete);
        }
        catch (InterruptedException e)
        {

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

    private List<File> convertImagesToFiles(List<MultipartFile> files)
    {
        return files.stream()
                .map(f -> {
                    try {
                        return convertImageToFile(f);
                    } catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @Autowired
    private final TransferManager transferManager;

    @Autowired
    private final AmazonS3 s3;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;
}
