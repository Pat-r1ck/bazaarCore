package com.okit.resourceservice.services.implementations;

import com.okit.resourceservice.constants.ProductCoreConstants;
import com.okit.resourceservice.constants.S3Constants;
import com.okit.resourceservice.dto.DomainResponse;
import com.okit.resourceservice.dto.ProductDetailRequest;
import com.okit.resourceservice.exceptions.EmailNotFoundException;
import com.okit.resourceservice.exceptions.ProductNotFoundException;
import com.okit.resourceservice.mappers.ProductMapper;
import com.okit.resourceservice.models.Product;
import com.okit.resourceservice.models.UserProfile;
import com.okit.resourceservice.repositories.ProductRepository;
import com.okit.resourceservice.repositories.UserProfileRepository;
import com.okit.resourceservice.services.ProductService;
import com.okit.resourceservice.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService
{
    @Override
    public Product getProduct(String id)
    {
        UUID uuid = UUID.fromString(id);

        return productRepository.findByUUID(uuid)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Set<Product> getProductByEmail(String email)
    {
        return productRepository.findProductByEmail(email);
    }

    @Override
    public DomainResponse registerProduct(ProductDetailRequest request, String email) throws IOException
    {
        UserProfile userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        Product product = Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .owner(userProfile)
                .build();

        List<MultipartFile> images = request.getImages();

        if(images != null)
        {
            product.setImages(formatS3Url(product.getId(), images));

            s3Service.uploadFiles(images, ProductCoreConstants.PRODUCT_IMAGES_DIRECTORY);
        }

        productRepository.save(product);

        return DomainResponse.builder()
                .msg(String.format(ProductCoreConstants.SUCCESSFULLY_UPDATED_PRODUCT, product.getId().toString()))
                .build();
    }

    @Override
    public DomainResponse updateProduct(ProductDetailRequest request, String id, String email) throws IOException
    {
        UserProfile userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        Product product = productRepository.findByUUID(UUID.fromString(id)).orElseThrow();

        if(!product.getOwner().getEmail().equals(email))
        {
            throw new EmailNotFoundException((email));
        }

        List<MultipartFile> images = request.getImages();

        if(images != null)
        {
            product.setImages(formatS3Url(product.getId(), images));

            s3Service.uploadFiles(images, ProductCoreConstants.PRODUCT_IMAGES_DIRECTORY);
        }

        productMapper.updateProduct(request, product);

        productRepository.save(product);

        return DomainResponse.builder()
                .msg(String.format(ProductCoreConstants.SUCCESSFULLY_UPDATED_PRODUCT, product.getId().toString()))
                .build();
    }

    private List<String> formatS3Url(UUID id, List<MultipartFile> files)
    {
        List<String> s3Url = new ArrayList<>();

        for(MultipartFile file : files)
        {
            s3Url.add(
                    S3Constants.s3UrlFormatter(S3Constants.productImagesDirectory(id) + file.getOriginalFilename())
            );
        }

        return s3Url;
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
