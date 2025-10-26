package com.main.service;

import com.main.entity.ProductEntity;
import com.main.dto.ProductDto;
import com.main.repo.ProductRepo;
import com.main.utils.GenericMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final GenericMapper genericMapper;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepo productRepo, GenericMapper genericMapper, ModelMapper modelMapper){
        this.productRepo = productRepo;
        this.genericMapper = genericMapper;
        this.modelMapper = modelMapper;
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        ProductEntity saveduser = productRepo.save(productEntity);
        return saveduser;
    }

    public List<ProductDto> getAllProducts() {
        List<ProductEntity> productEntityList = productRepo.findAll();

        return productEntityList.stream().map(this::toProductDto).collect(Collectors.toList());
    }

    public ProductDto getProduct(Long id) {
        Optional<ProductEntity> productEntityOptional = Optional.of(productRepo.findById(id).orElseThrow());
        ProductEntity productEntity = productEntityOptional.get();
        return toProductDto(productEntity);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
         ProductEntity existingProduct = this.productRepo.findById(id)
                 .orElseThrow(() -> new RuntimeException("Product not found with id : " + id));
         GenericMapper.copyNonNullProperties(productDto,existingProduct);
         ProductEntity updatedProduct = productRepo.save(existingProduct);
         return genericMapper.map(updatedProduct,ProductDto.class);
    }


    public void deleteProductByProductId(Long id) {
        Optional<ProductEntity> optionalProductEntity = productRepo.findById(id);
        if(!(optionalProductEntity.isPresent())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product with Product id : " + id + "not found.");
        }
        this.productRepo.deleteById(id);
    }

    private ProductDto toProductDto(ProductEntity productEntity){
        return modelMapper.map(productEntity,ProductDto.class);
    }
}

























