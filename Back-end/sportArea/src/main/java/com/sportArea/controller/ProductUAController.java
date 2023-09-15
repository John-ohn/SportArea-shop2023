package com.sportArea.controller;

import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.service.ProductUAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping("/main-search")
    public List<ProductUaDTO> mainSearchProduct(@RequestParam("keyWord") String keyWord) {

        List<ProductUaDTO> productList = productService.searchByKeyWordInTypeSubtype(keyWord);
        logger.info("From ProductUAController method -main-search- /product/main-searchProduct. Return List of ProductUA");
        return productList;
    }

    @GetMapping("/searchProduct")
    public List<ProductUaDTO> searchProductKeyWordDescription(@RequestParam("keyWord") String keyWord) {

        List<ProductUaDTO> productList = productService.searchByKeyWordInDescription(keyWord);
        logger.info("From ProductUAController method -searchProductKeyWord- /product/searchProduct. Return List of ProductUA");
        return productList;
    }

    @GetMapping("/promotion")
    public List<ProductUaDTO> searchByPromotionPrice() {

        List<ProductUaDTO> productList = productService.searchByPromotionPrice();
        logger.info("From ProductUAController method -searchByBestPrice- /product/promotion. Return List of Products");
        return productList;
    }

    @GetMapping("/searchProductPriceAsc")
    public List<ProductUaDTO> sortByPriceAscKeyWordDescription(@RequestParam("keyWord") String keyWord) {
        List<ProductUaDTO> productList = productService.sortByPriceAscKeyWordDescription(keyWord);
        logger.info("From ProductUAController method -sortByPriceAscKeyWordDescription- /product/searchProductPriceAsc. Return List of Products");
        return productList;
    }

    @GetMapping("/searchProductPriceDesc")
    public List<ProductUaDTO> sortByPriceDescKeyWordDescription(@RequestParam("keyWord") String keyWord) {
        List<ProductUaDTO> productList = productService.sortByPriceDescKeyWordDescription(keyWord);
        logger.info("From ProductUAController method -sortByPriceDescKeyWordDescription- /product/searchProductPriceDesc. Return List of Products");
        return productList;
    }

    @GetMapping("/searchProductRatingDesc")
    public List<ProductUaDTO> sortByRatingDescKeyWordDescription(@RequestParam("keyWord") String keyWord) {
        List<ProductUaDTO> productList = productService.sortByRatingDescKeyWordDescription(keyWord);
        logger.info("From ProductUAController method -sortByRatingDescKeyWordDescription- /product/searchProductRatingDesc. Return List of Products");
        return productList;
    }

    @GetMapping("/searchProductNameAsc")
    public List<ProductUaDTO> sortByProductNameAscKeyWordDescription(@RequestParam("keyWord") String keyWord) {
        List<ProductUaDTO> productList = productService.sortByProductNameAscKeyWordDescription(keyWord);
        logger.info("From ProductUAController method -sortByProductNameAscKeyWordDescription- /product/searchProductNameAsc. Return List of Products");
        return productList;
    }

    @GetMapping("/searchProductPriceBetween")
    public List<ProductUaDTO> sortByPriceBetweenKeyWordDescription(
            @RequestParam("keyWord") String keyWord,
            @RequestParam("lowPrice") BigDecimal lowPrice,
            @RequestParam("highPrice") BigDecimal highPrice
    ) {
        List<ProductUaDTO> productList = productService.sortByPriceBetweenKeyWordDescription(keyWord, lowPrice, highPrice);
        logger.info("From ProductUAController method -sortByProductNameAscKeyWordDescription- /product/searchProductPriceBetween. Return List of Products");
        return productList;
    }

    @GetMapping("/searchProductTime")
    public List<ProductUaDTO> sortByTimeKeyWordDescription(@RequestParam("keyWord") String keyWord) {

        List<ProductUaDTO> productList = productService.sortByTimeKeyWordDescription(keyWord);
        logger.info("From ProductUAController method -sortByTimeKeyWordDescription- /product/searchProductPriceTime. Return List of Products");
        return productList;
    }


    @GetMapping("/searchProductPopular")
    public List<ProductUaDTO> sortByNumberOfOrdersDescKeyWordDescription(@RequestParam("keyWord") String keyWord) {

        List<ProductUaDTO> productList = productService.sortByNumberOfOrdersDescKeyWordDescription(keyWord);
        logger.info("From ProductUAController method -sortByNumberOfOrdersDescKeyWordDescription- /product/searchProductPopular. Return List of Products");
        return productList;
    }

//    @GetMapping("/searchProductAll")
//    public List<ProductUaDTO> searchAndSortKeyWordDescriptionFromDataBase(@RequestParam("keyWord") String keyWord,
//                                                                          @RequestParam("sortBy") String sortBy) {
//
//        List<ProductUaDTO> productList = productService.searchAndSortKeyWordDescriptionFromDataBase(keyWord, sortBy);
//        logger.info("From ProductUAController method -searchAndSortKeyWordDescriptionFromDataBase- /product/searchProductAll. Return List of Products");
//        return productList;
//    }

    @GetMapping("/searchProductAll")
    public List<ProductUaDTO> searchAndSortKeyWordDescriptionFromDataBase(@RequestParam("keyWord") String keyWord,
                                                                          @RequestParam("sortBy") String sortBy,
                                                                          @RequestParam("searchLocation") String searchLocation) {

        List<ProductUaDTO> productList = productService.searchAndSort(keyWord, sortBy, searchLocation);
        logger.info("From ProductUAController method -searchAndSortKeyWordDescriptionFromDataBase- /product/searchProductAll. Return List of Products");
        return productList;
    }


}
