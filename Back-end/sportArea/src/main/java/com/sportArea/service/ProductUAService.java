package com.sportArea.service;


import com.sportArea.entity.ProductUA;

import java.util.List;

public interface ProductUAService {

    ProductUA findById(Long productId);

    List<ProductUA> findAll();

    List<ProductUA> searchByKeyWordInDescription(String keyWord);

    List<ProductUA> searchByKeyWordInTypeSubtype(String keyWord);

    List<ProductUA> searchByPromotionPrice();

    ProductUA save(ProductUA product);

    void delete(Long productId);

    void deleteProductUABetweenIds(Long startId, Long endId);
}
