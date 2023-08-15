package com.sportArea.service;


import com.sportArea.entity.ProductUA;
import com.sportArea.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductUAService {

    ProductUA findById(Long productId);

    List<ProductUA> findAll();

    List<ProductUA> searchByKeyWord( String keyWord);

    ProductUA save(ProductUA product);

    void delete(Long productId);

    void deleteProductUABetweenIds(Long startId, Long endId);
}
