package com.main.service;

import com.main.entity.ProductEntity;
import com.main.dto.ProductDto;
import com.main.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        ProductEntity saveduser = productRepo.save(productEntity);
        return saveduser;
    }

    public List<ProductDto> getAllProducts() {
        List<ProductEntity> productEntityList = productRepo.findAll();
        return productEntityToProductDto(productEntityList);
    }

    public List<ProductDto> productEntityToProductDto(List<ProductEntity> productEntityList){

        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        for(ProductEntity productEntity : productEntityList){
            productDto.setProductName(productEntity.getProductName());
            productDto.setPrice(productDto.getPrice());
        }
        productDtoList.add(productDto);
        return productDtoList;
    }

    public ProductDto getProduct(Long id) {
        Optional<ProductEntity> productEntityOptional = Optional.of(productRepo.findById(id).orElseThrow());
        ProductEntity productEntity = productEntityOptional.get();
        return (ProductDto) productEntityToProductDto(Collections.singletonList(productEntity));
    }

//    public ProductDto updateProduct(Long id) {
//        Optional<ProductEntity> productEntityOptional = Optional.o
//    }
}

























