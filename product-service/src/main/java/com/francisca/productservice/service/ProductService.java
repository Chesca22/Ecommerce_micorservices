package com.francisca.productservice.service;

import com.francisca.productservice.dto.ProductDto;
import com.francisca.productservice.dto.ProductResponse;
import com.francisca.productservice.model.Product;
import com.francisca.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;
    public void createProduct(ProductDto productDto){
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product with id: {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts(){
      List<Product> products = productRepository.findAll();
     return products.stream().map(this::mapToProductResponse).toList();

    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
