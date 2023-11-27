package com.okit.resourceservice.constants;

import java.util.UUID;

public class S3Constants
{
    public static final String MISS_MATCH_FILE_MSG = "fileNames: %d , files: %d";
    public static final String PRODUCT_NOT_FOUND = "Product %s not found.";
    public static final String S3_PREFIX = "s3://ustbazaar/";
    public static final String PRODUCT_IMAGES_DIRECTORY = "https://bazaarbucket.s3.amazonaws.com/";
    public static String productImagesDirectory(UUID id)
    {
        return "product_images_" + id + "/";
    }
    public static String s3UrlFormatter(String path)
    {
        return S3_PREFIX + path;
    }
}
