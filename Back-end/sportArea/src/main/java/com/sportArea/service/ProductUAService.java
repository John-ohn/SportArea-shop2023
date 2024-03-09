package com.sportArea.service;


import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.ProductDto;
import com.sportArea.entity.dto.ProductUaDTO;


import java.math.BigDecimal;
import java.util.List;

public interface ProductUAService {

    ProductUaDTO findById(Long productId);

    ProductUA findByIdWithoutDTO(Long productId);

    List<ProductUaDTO> findAll();

    ProductUA findByIdInProduct(Long productId);

    List<ProductUaDTO> searchByKeyWordInDescription(String keyWord);

    List<ProductUaDTO> searchByKeyWordInTypeSubtype(String keyWord);

    List<ProductUaDTO> searchByPromotionPrice();

    void save(ProductUaDTO product);

    void saveWithoutDTO(ProductUA product);

    void delete(Long productId);

    void deleteProductUABetweenIds(Long startId, Long endId);

    ProductUA createProductFromProductUaDTO(ProductUaDTO productUaDTO);

    ProductUaDTO createProductDTOFromProductUA(ProductUA productUA);

    ProductDto createProductDtoFromProductUA(ProductUA productUA);

    List<ProductUaDTO> searchAndSort(String keyWord,
                                     String sortBy,
                                     String searchLocation,
                                     String priceBetween,
                                     BigDecimal lowPrice,
                                     BigDecimal highPrice);

    List<ProductUaDTO> searchProducts(String keyWord,
                                      String searchLocation);

    List<ProductUA> convertToProductList(List<ProductUaDTO> productList);

    List<ProductUaDTO> convertToProductDTOList(List<ProductUA> productList);

     List<ProductDto> convertToProductDtoList(List<ProductUA> productList);
}
