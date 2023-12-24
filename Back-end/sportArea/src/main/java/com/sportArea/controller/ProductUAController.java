package com.sportArea.controller;


import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.ProductUAService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductUaDTO> findAll() {
        List<ProductUaDTO> productList = productService.findAll();

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "findAll",
                "/products",
                "List of Product");

        return productList;
    }

    @GetMapping("/{productId}")
    public ProductUaDTO getProductById(@PathVariable("productId") Long productId) {
        ProductUaDTO productDTO = productService.findById(productId);

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "getProductById",
                "/products/{productId}",
                "Product with productId: " + productId);

        return productDTO;
    }

    @PostMapping
    public ResponseEntity<String> addProductUA(@RequestBody ProductUaDTO product) {
        productService.save(product);

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "addProductUA",
                "/products",
                "message (Product was added successfully.) and save new Product.");

        return ResponseEntity.ok("Product was added successfully.");
    }

    @GetMapping("/search")
    public List<ProductUaDTO> searchProducts(@RequestParam("keyWord") String keyWord,
                                             @RequestParam("searchLocation") String searchLocation) {

        List<ProductUaDTO> productList = productService.searchProducts(keyWord, searchLocation);

        generalLogg.getLoggerControllerInfo("ProductUAController",
                "searchProducts",
                "/search",
                "List of Products by keyWord: " + keyWord + ". searchLocation: " + searchLocation);

        return productList;
    }
}
