package com.sportArea.controller;


import com.sportArea.entity.TargetCategory;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.service.ProductUAService;
import com.sportArea.service.TargetCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductUAController {

    Logger logger = LoggerFactory.getLogger(ProductUAController.class);

    private final TargetCategoryService targetCategoryService;
    private final ProductUAService productService;

    @Autowired
    public ProductUAController(ProductUAService productService, TargetCategoryService targetCategoryService) {
        this.productService = productService;
        this.targetCategoryService=targetCategoryService;
    }

    @GetMapping("/list")
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

    @PostMapping("/add")
    public ResponseEntity<String> addProductUA(@RequestBody ProductUaDTO product) {
        productService.save(product);

        return ResponseEntity.ok("Product was added successfully.");
    }

    @GetMapping("/searchProduct")
    public List<ProductUaDTO> searchAndSortKeyWordDescriptionFromDataBase(@RequestParam("keyWord") String keyWord,
                                                                          @RequestParam("sortBy") String sortBy,
                                                                          @RequestParam("searchLocation") String searchLocation,
                                                                          @RequestParam("priceBetween") String priceBetween,
                                                                          @RequestParam("lowPrice") BigDecimal lowPrice,
                                                                          @RequestParam("highPrice") BigDecimal highPrice) {

        List<ProductUaDTO> productList = productService.searchAndSort(keyWord, sortBy, searchLocation, priceBetween, lowPrice, highPrice);
        logger.info("From ProductUAController method -searchAndSortKeyWordDescriptionFromDataBase- /product/searchProduct. Return List of Products");
        return productList;
    }

    @GetMapping("/targetCategory")
    public List<TargetCategory> getAllTargetCategory(){

       return targetCategoryService.findAll();
    }

    @GetMapping("/targetCategory/{categoryId}")
    public TargetCategory getTargetCategoryById(@PathVariable("categoryId") Long categoryId){
        TargetCategory category = targetCategoryService.findById(categoryId);

        return category;
    }

    @PostMapping("/targetCategory")
    public ResponseEntity<String> saveTargetCategory(@RequestBody TargetCategory category){

        targetCategoryService.save(category);

       return ResponseEntity.ok("Category was saved on target category successfully");
    }

    @DeleteMapping ("/targetCategory/{categoryId}")
    public ResponseEntity<String> deleteTargetCategory(@PathVariable("categoryId") Long categoryId){

        targetCategoryService.delete(categoryId);

        return ResponseEntity.ok("Category was deleted from target category successfully");
    }


}
