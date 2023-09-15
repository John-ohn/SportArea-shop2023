package com.sportArea.service;


import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.ProductUaDTO;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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

    List<ProductUaDTO> sortByProductNameAscKeyWordDescription(String keyWord);

    List<ProductUaDTO> sortByNumberOfOrdersDescKeyWordDescription(String keyWord);

    List<ProductUaDTO> sortByPriceBetweenKeyWordDescription(
            String keyWord,
            BigDecimal lowPrice,
            BigDecimal highPrice
    );

    List<ProductUaDTO> sortByTimeKeyWordDescription(String keyWord);

    List<ProductUaDTO> searchAndSortKeyWordDescriptionFromDataBase(String keyWord, String sortBy);

    List<ProductUaDTO> searchAndSort(String keyWord, String sortBy, String searchLocation);
}
