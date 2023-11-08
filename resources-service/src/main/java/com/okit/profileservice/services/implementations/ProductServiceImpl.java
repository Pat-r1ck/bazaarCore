package com.okit.profileservice.services.implementations;

import com.okit.profileservice.constants.ProductCoreConstants;
import com.okit.profileservice.constants.S3Constants;
import com.okit.profileservice.dto.DomainResponse;
import com.okit.profileservice.dto.UpdateProductRequest;
import com.okit.profileservice.exceptions.EmailNotFoundException;
import com.okit.profileservice.mappers.ProductMapper;
import com.okit.profileservice.models.Product;
import com.okit.profileservice.models.UserProfile;
import com.okit.profileservice.repositories.ProductRepository;
import com.okit.profileservice.repositories.UserProfileRepository;
import com.okit.profileservice.services.ProductService;
import com.okit.profileservice.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService
{
    @Override
    public DomainResponse updateProduct(UpdateProductRequest request, String email) throws IOException
    {
        String id = request.getId();

        UserProfile userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        Product product = id != null
                ? productRepository.findByUUID(UUID.fromString(id)).orElseThrow()
                : Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .owner(userProfile)
                .build();

        List<MultipartFile> images = request.getImages();

        if(images != null)
        {
            List<String> fileNames = formatFileName(product.getId(), images.size());

            product.setImages(formatS3Url(fileNames));

            s3Service.uploadFiles(images, ProductCoreConstants.PRODUCT_IMAGES_DIRECTORY);
        }

        productMapper.updateProduct(request, product);

        productRepository.save(product);

        return DomainResponse.builder()
                .msg(String.format(ProductCoreConstants.SUCCESSFULLY_UPDATED_PRODUCT, product.getId().toString()))
                .build();
    }

    private List<String> formatFileName(UUID id, int size)
    {
        List<String> fileNames = new ArrayList<>();

        for(int i = 0 ; i < size ; ++i)
        {
            fileNames.add(id.toString() + "_" + i);
        }

        return fileNames;
    }

    private List<String> formatS3Url(List<String> fileNames)
    {
        return fileNames.stream()
                .map(file -> S3Constants.S3_PREFIX + file)
                .toList();
    }

    @Autowired
    private final S3Service s3Service;

    @Autowired
    private final ProductMapper productMapper;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final UserProfileRepository userProfileRepository;
}
