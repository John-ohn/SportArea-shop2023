package com.sportArea.controller;

import com.sportArea.entity.ProductUA;
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
        logger.info("From ProductUAController method -findAll- /product/list. Return List of ProductUA");
        return productList;
    }

    @PostMapping("/add")
    public ResponseEntity<ProductUA> addProductUA(@RequestBody ProductUA product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @GetMapping("/main-search")
    public List<ProductUA> mainSearchProduct(@RequestParam("keyWord") String keyWord) {

        List<ProductUA> productList = productService.searchByKeyWordInTypeSubtype(keyWord);
        logger.info("From ProductUAController method -main-search- /product/main-searchProduct. Return List of ProductUA");
        return productList;
    }

    @GetMapping("/searchProduct")
    public List<ProductUA> searchProductKeyWordDescription(@RequestParam("keyWord") String keyWord) {

        List<ProductUA> productList = productService.searchByKeyWordInDescription(keyWord);
        logger.info("From ProductUAController method -searchProductKeyWord- /product/searchProduct. Return List of ProductUA");
        return productList;
    }

    @GetMapping("/promotion")
    public List<ProductUA> searchByPromotionPrice() {

        List<ProductUA> productList = productService.searchByPromotionPrice();
        logger.info("From ProductUAController method -searchByBestPrice- /product/promotion. Return List of ProductUA");
        return productList;
    }

}
