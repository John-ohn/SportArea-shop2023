package com.sportArea.controller;


import com.sportArea.entity.dto.GeneralResponse;
import com.sportArea.entity.dto.ProductDto;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.ProductUAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductUAController {


    private final GeneralLogg generalLogg;

    private final ProductUAService productService;

    @Autowired
    public ProductUAController(ProductUAService productService, GeneralLogg generalLogg) {
        this.productService = productService;
        this.generalLogg = generalLogg;
    }

    @GetMapping
    public List<ProductDto> findAll() {
        List<ProductDto> productList = productService.findAllConvertToProductDto();

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "findAll",
                "/products",
                "List of Product");

        return productList;
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable("productId") Long productId) {
        ProductDto productDTO = productService.findByIdConvertToProductDto(productId);

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "getProductById",
                "/products/{productId}",
                "Product with productId: " + productId);

        return productDTO;
    }

    @GetMapping("/search/category")
    public List<ProductDto> searchProductsByCategory(@RequestParam("categoryId") Long categoryId,
                                                     @RequestParam("subCategoryId") Long subCategoryId) {

        List<ProductDto> productList = productService.findAllConvertToProductDto();

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "searchProductsByCategory",
                "/search/category",
                "List of Products by categoryId: " + categoryId + ". subCategoryId: " + subCategoryId);

        return productList;
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> addProductUA(@RequestBody ProductUaDTO product) {
        productService.save(product);

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "addProductUA",
                "/products",
                "message (Product was added successfully.) and save new Product.");

        GeneralResponse generalResponse = new GeneralResponse(
                HttpStatus.OK.value(),
                "Product was added successfully.");

        return ResponseEntity.ok(generalResponse);
    }

    @GetMapping("/search")
    public List<ProductDto> searchProducts(@RequestParam("keyWord") String keyWord,
                                           @RequestParam("searchLocation") String searchLocation) {

        List<ProductDto> productList = productService.searchProducts(keyWord, searchLocation);

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "searchProducts",
                "/search",
                "List of Products by keyWord: " + keyWord + ". searchLocation: " + searchLocation);

        return productList;
    }
}
