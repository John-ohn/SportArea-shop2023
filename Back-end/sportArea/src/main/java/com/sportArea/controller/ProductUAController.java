package com.sportArea.controller;


import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.service.ProductUAService;
import com.sportArea.service.TargetCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductUAController {

    Logger logger = LoggerFactory.getLogger(ProductUAController.class);

    private final ProductUAService productService;

    @Autowired
    public ProductUAController(ProductUAService productService, TargetCategoryService targetCategoryService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductUaDTO> findAll() {
        List<ProductUaDTO> productList = productService.findAll();
        logger.info("From ProductUAController method -findAll- /product/list. Return List of ProductDTO");
        return productList;
    }

    @GetMapping("/{productId}")
    public ProductUaDTO getProductById(@PathVariable("productId") Long productId) {
        ProductUaDTO productDTO = productService.findById(productId);
        logger.info("From ProductUAController method -getProductById- /product/{productId}. Return ProductUA");

        return productDTO;
    }

    @PostMapping
    public ResponseEntity<String> addProductUA(@RequestBody ProductUaDTO product) {
        productService.save(product);

        return ResponseEntity.ok("Product was added successfully.");
    }

    @GetMapping("/search")
    public List<ProductUaDTO> searchProducts(@RequestParam("keyWord") String keyWord,
                                             @RequestParam("searchLocation") String searchLocation) {

        List<ProductUaDTO> productList = productService.searchProducts(keyWord, searchLocation);
        logger.info("From ProductUAController method -searchAndSortKeyWordDescriptionFromDataBase- /product/searchProduct. Return List of Products");
        return productList;
    }

//    @GetMapping("/search")
//    public List<ProductUaDTO> searchAndSortKeyWordDescriptionFromDataBase(@RequestParam("keyWord") String keyWord,
//                                                                          @RequestParam("sortBy") String sortBy,
//                                                                          @RequestParam("searchLocation") String searchLocation,
//                                                                          @RequestParam("priceBetween") String priceBetween,
//                                                                          @RequestParam("lowPrice") BigDecimal lowPrice,
//                                                                          @RequestParam("highPrice") BigDecimal highPrice) {
//
//        List<ProductUaDTO> productList = productService.searchAndSort(keyWord, sortBy, searchLocation, priceBetween, lowPrice, highPrice);
//        logger.info("From ProductUAController method -searchAndSortKeyWordDescriptionFromDataBase- /product/searchProduct. Return List of Products");
//        return productList;
//    }




}
