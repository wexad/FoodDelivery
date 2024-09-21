package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.ProductDTO;
import com.wexad.BurgerHub.handler.exceptions.ProductNotFoundException;
import com.wexad.BurgerHub.model.Compound;
import com.wexad.BurgerHub.model.Image;
import com.wexad.BurgerHub.model.Product;
import com.wexad.BurgerHub.repository.CompoundRepository;
import com.wexad.BurgerHub.repository.ImageRepository;
import com.wexad.BurgerHub.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.wexad.BurgerHub.mapper.ProductMapper.PRODUCT_MAPPER;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CompoundRepository compoundRepository;
    private final ImageRepository imageRepository;

    public ProductService(ProductRepository productRepository, CompoundRepository compoundRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.compoundRepository = compoundRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public Product saveProduct(Product product) {
        Image image = product.getImage();
        if (image != null && image.getId() == null) {
            imageRepository.save(image);
        }
        if (product.getCompound().getId() == null) {
            compoundRepository.save(product.getCompound());
        }
        return productRepository.save(product);
    }


    public ProductDTO getProductById(Long id) {
        return PRODUCT_MAPPER.toDTO(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found")));
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.updateDeletedBy(id);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
