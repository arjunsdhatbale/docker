package com.main.controller;

import com.main.dto.ProductDto;
import com.main.entity.ProductEntity;
import com.main.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<ProductEntity> saveProduct(@RequestBody ProductEntity productEntity){
        logger.info("Request received for save product.");
        ProductEntity response = productService.saveProduct(productEntity);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        logger.info("Request received to getAllProducts.");
        List<ProductDto> response = productService.getAllProducts();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id){
        logger.info("Request received to get Product by id: {}.",id);
        ProductDto response = productService.getProduct(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id){
//        logger.info("Request received to update Product by id: {}.",id);
//        ProductDto response = productService.updateProduct(id);
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }

}
























