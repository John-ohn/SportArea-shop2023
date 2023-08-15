package com.sportArea.controller;

import com.sportArea.entity.ProductUA;
import com.sportArea.exception.ProductExeption;
import com.sportArea.service.ProductUAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductUAController {

    Logger logger = LoggerFactory.getLogger(ProductUAController.class);
    private final ProductUAService productService;

    @Autowired
    public ProductUAController(ProductUAService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public List<ProductUA> findAll() {
        List<ProductUA> productList = productService.findAll();
        logger.info("From ProductUAController method -findAll- /product/list. Return List<ProductUA");
        return productList;
    }

    @PostMapping("/add")
    public ResponseEntity<ProductUA> addProductUA(@RequestBody ProductUA product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @GetMapping("/searchProduct")
    public List<ProductUA> searchProductKeyWord(@RequestParam("keyWord") String keyWord) {

        List<ProductUA> productList = productService.searchByKeyWord(keyWord);
        logger.info("From ProductUAController method -searchProductKeyWord- /product/searchProduct. Return List<ProductUA");
        return productList;
    }


}
