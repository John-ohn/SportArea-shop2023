package com.sportArea.service;


import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.ProductUaDTO;
import org.springframework.data.repository.query.Param;

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

    ProductUA createProductFromProductUaDTO(ProductUaDTO productUaDTO);

    ProductUaDTO createProductDTOFromProductUA(ProductUA productUA);

    List<ProductUaDTO> sortByPriceDescKeyWordDescription(String keyWord);

    List<ProductUaDTO> sortByPriceAscKeyWordDescription(String keyWord);

    List<ProductUaDTO> sortByRatingDescKeyWordDescription(String keyWord);
}
