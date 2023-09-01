package com.sportArea.service;


import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.ProductUaDTO;

import java.util.List;

public interface ProductUAService {

    ProductUaDTO findById(Long productId);

    List<ProductUaDTO> findAll();

    List<ProductUaDTO> searchByKeyWordInDescription(String keyWord);

    List<ProductUaDTO> searchByKeyWordInTypeSubtype(String keyWord);

    List<ProductUaDTO> searchByPromotionPrice();

    void save(ProductUaDTO product);

    void delete(Long productId);

    void deleteProductUABetweenIds(Long startId, Long endId);
}
