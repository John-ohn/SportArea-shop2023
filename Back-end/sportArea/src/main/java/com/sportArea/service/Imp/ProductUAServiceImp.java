package com.sportArea.service.Imp;

import com.sportArea.dao.ProductUARepository;
import com.sportArea.entity.ProductUA;
import com.sportArea.exception.ProductExeption;
import com.sportArea.service.ProductUAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductUAServiceImp implements ProductUAService {

    Logger logger = LoggerFactory.getLogger(ProductUAServiceImp.class);
    private final ProductUARepository productRepository;

    @Autowired
    public ProductUAServiceImp(ProductUARepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public ProductUA findById(Long productId) {
        Optional<ProductUA> product = productRepository.findById(productId);

        if (product.isPresent()) {
            logger.info("From ProductUAServiceImp method -findById- return Product by id: {} ", productId);
            return product.get();
        } else {
            logger.warn("From ProductUAServiceImp method -findById- send war message " +
                    "( Product with productId {} is not available. ({}))", productId, HttpStatus.NOT_FOUND);
            throw new ProductExeption("Product with productId: " + productId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductUA> findAll() {
        List<ProductUA> productList = productRepository.findAll();
        logger.info("From ProductUAServiceImp method -findAll- return List<ProductUA>.");
        return productList;
    }

    @Override
    public List<ProductUA> searchByKeyWordInDescription(String keyWord) {
        if (keyWord != null || !keyWord.isEmpty()) {
            List<ProductUA> productUAList = productRepository.searchByKeyWordInDescription(keyWord);
            if (!productUAList.isEmpty()) {
                logger.info("From ProductUAServiceImp method -searchByKeyWord- return List<ProductUA> with keyWord: {}.", keyWord);
                return productUAList;
            } else {
                logger.warn("From ProductUAServiceImp method -sasearchByKeyWordve- send war message " +
                        "(Product with keyWord: {} not found. ({}))", keyWord, HttpStatus.NOT_FOUND.value());

                throw new ProductExeption("Product with keyWord: " + keyWord + " not found.",
                        HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -sasearchByKeyWordve- send war message " +
                    "Key word is empty or null . ({})", HttpStatus.NOT_FOUND.value());

            throw new ProductExeption("Key word is empty or null" + keyWord,
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductUA> searchByKeyWordInTypeSubtype(String keyWord) {
        if (keyWord != null || !keyWord.isEmpty()) {
            List<ProductUA> productUAList = productRepository.searchByKeyWordInTypeSubtype(keyWord);
            if (!productUAList.isEmpty()) {
                logger.info("From ProductUAServiceImp method -searchByKeyWordInTypeSubtype- return List<ProductUA> with keyWord: {}.", keyWord);
                return productUAList;
            } else {
                logger.warn("From ProductUAServiceImp method -searchByKeyWordInTypeSubtype- send war message " +
                        "(Product with keyWord: {} not found. ({}))", keyWord, HttpStatus.NOT_FOUND.name());

                throw new ProductExeption("Product with keyWord: " + keyWord + " not found.",
                        HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchByKeyWordInTypeSubtype- send war message " +
                    "Key word is empty or null . ({})", HttpStatus.NOT_FOUND.name());

            throw new ProductExeption("Key word is empty or null" + keyWord,
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductUA> searchByPromotionPrice() {
        List<ProductUA> productList = productRepository.searchByPromotionPrice();
        if (!productList.isEmpty()) {
            logger.info("From ProductUAServiceImp method -searchByBestPrice- return List<ProductUA> ");
            return productList;
        } else {
            logger.warn("From ProductUAServiceImp method -searchByBestPrice- send war message " +
                    "List with promotion products is empty or not available. ({})", HttpStatus.NOT_FOUND.name());
            throw new ProductExeption("List with promotion products is empty or not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ProductUA save(ProductUA product) {

        if (product != null) {
            ProductUA productUa = productRepository.save(product);
            logger.info("From ProductUAServiceImp method -save- return new ProductUA from Data Base.");

            return productUa;

        } else {
            logger.warn("From ProductUAServiceImp method -save- send war message " +
                    "( Product is not available or his is empty. ({}))", HttpStatus.NO_CONTENT.value());

            throw new ProductExeption("Product is not available or his is empty. ", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public void delete(Long productId) {
        Optional<ProductUA> product = productRepository.findById(productId);

        if (product.isPresent()) {

            productRepository.delete(product.get());
            logger.info("From ProductUAServiceImp method -delete- return message (Product with productId: {} was deleted.)", productId);

        } else {
            logger.warn("From ProductUAServiceImp method -delete- send war message " +
                    "(Product with productId: {} is not available. {} )", productId, HttpStatus.NOT_FOUND.value());

            throw new ProductExeption("Product with productId: " + productId + " is not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProductUABetweenIds(Long startId, Long endId) {

        if (startId >= 0 && endId > startId + 1) {
            productRepository.deleteBetweenIds(startId, endId);
            logger.info(
                    "From ProductUAServiceImp method -deleteUsersBetweenIds- return message (Product between productId: " +
                            "{} and {} was deleted.).", startId, endId);
        } else {
            logger.warn("From ProductUAServiceImp method -deleteProductUABetweenIds- send war message " +
                    "(Product with productIds: {} and {} is not available. {}", startId, endId, HttpStatus.NOT_FOUND.value());
            throw new ProductExeption("Product with productIds: " + startId + "and " + endId + " is not available.",
                    HttpStatus.NOT_FOUND);
        }
    }
}
