package com.okit.resourceservice.services.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.okit.resourceservice.services.S3Service;
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
import java.util.concurrent.CompletableFuture;

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

    @Override
    public String uploadFile(String fileName, MultipartFile file) throws IOException
    {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String objectKey = fileName + "." + fileExtension;

        File image = convertImageToFile(file);

        String contentType = getContentTypeByFileExtension(fileExtension);

        PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, image);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        request.setMetadata(metadata);

        s3.putObject(request);

        image.delete();
        return objectKey;
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
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }
    
    private String getContentTypeByFileExtension(String fileExtension) {
        // Map common file extensions to content types
        switch (fileExtension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            // Add more mappings as needed for other image types
            default:
                // If the file extension is not recognized, you can set a default content type
                // or throw an exception
                return "application/octet-stream";
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    @Autowired
    private final TransferManager transferManager;

    @Autowired
    private final AmazonS3 s3;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;
}
