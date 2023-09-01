package com.sportArea.service.Imp;

import com.sportArea.dao.ProductUARepository;
import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.exception.ProductException;
import com.sportArea.service.ProductUAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductUAServiceImp implements ProductUAService {

    Logger logger = LoggerFactory.getLogger(ProductUAServiceImp.class);

    private final ProductUARepository productRepository;

    @Autowired
    public ProductUAServiceImp(ProductUARepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public ProductUaDTO findById(Long productId) {
        Optional<ProductUA> product = productRepository.findById(productId);

        if (product.isPresent()) {
            logger.info("From ProductUAServiceImp method -findById- return Product by id: {} ", productId);

            return createProductDTOFromProductUA(product.get());
        } else {
            logger.warn("From ProductUAServiceImp method -findById- send war message " +
                    "( Product with productId {} is not available. ({}))", productId, HttpStatus.NOT_FOUND);
            throw new ProductException("Product with productId: " + productId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductUaDTO> findAll() {
        List<ProductUA> productList = productRepository.findAll();
        if (!productList.isEmpty()) {

            List<ProductUaDTO> productDTOList = convertToProductDTOList(productList);

            logger.info("From ProductUAServiceImp method -findAll- return List<ProductUaDTO>.");

            return productDTOList;
        } else {
            throw new ProductException("Don't find any Products. Products list is empty.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<ProductUaDTO> searchByKeyWordInDescription(String keyWord) {
        if (keyWord != null || !keyWord.isEmpty()) {
            List<ProductUA> productUAList = productRepository.searchByKeyWordInDescription(keyWord);

            if (!productUAList.isEmpty()) {
                List<ProductUaDTO> productDTOList = convertToProductDTOList(productUAList);
                logger.info("From ProductUAServiceImp method -searchByKeyWordInDescription- return List<ProductDTO> with keyWord: {}.", keyWord);
                return productDTOList;
            } else {
                logger.warn("From ProductUAServiceImp method -searchByKeyWordInDescription- send war message " +
                        "(Product with keyWord: {} not found. ({}))", keyWord, HttpStatus.NOT_FOUND.value());

                throw new ProductException("Product with keyWord: " + keyWord + " not found.",
                        HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchByKeyWordInDescription- send war message " +
                    "Key word is empty or null . ({})", HttpStatus.NOT_FOUND.value());

            throw new ProductException("Key word is empty or null" + keyWord,
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductUaDTO> searchByKeyWordInTypeSubtype(String keyWord) {
        if (keyWord != null || !keyWord.isEmpty()) {
            List<ProductUA> productUAList = productRepository.searchByKeyWordInTypeSubtype(keyWord);
            if (!productUAList.isEmpty()) {
                List<ProductUaDTO> productDTOList = convertToProductDTOList(productUAList);
                logger.info("From ProductUAServiceImp method -searchByKeyWordInTypeSubtype- return List<ProductUA> with keyWord: {}.", keyWord);
                return productDTOList;
            } else {
                logger.warn("From ProductUAServiceImp method -searchByKeyWordInTypeSubtype- send war message " +
                        "(Product with keyWord: {} not found. ({}))", keyWord, HttpStatus.NOT_FOUND.name());

                throw new ProductException("Product with keyWord: " + keyWord + " not found.",
                        HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchByKeyWordInTypeSubtype- send war message " +
                    "Key word is empty or null . ({})", HttpStatus.NOT_FOUND.name());

            throw new ProductException("Key word is empty or null" + keyWord,
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductUaDTO> searchByPromotionPrice() {
        List<ProductUA> productList = productRepository.searchByPromotionPrice();
        if (!productList.isEmpty()) {
            List<ProductUaDTO> productDTOList = convertToProductDTOList(productList);
            logger.info("From ProductUAServiceImp method -searchByBestPrice- return List<ProductUA> ");
            return productDTOList;
        } else {
            logger.warn("From ProductUAServiceImp method -searchByBestPrice- send war message " +
                    "List with promotion products is empty or not available. ({})", HttpStatus.NOT_FOUND.name());
            throw new ProductException("List with promotion products is empty or not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void save(ProductUaDTO product) {

        if (product != null) {
            ProductUA productUa = createProductFromProductUaDTO(product);
            productRepository.save(productUa);
            logger.info("From ProductUAServiceImp method -save- return new message (Product was added successfully.).");


        } else {
            logger.warn("From ProductUAServiceImp method -save- send war message " +
                    "( Product is not available or his is empty. ({}))", HttpStatus.NO_CONTENT.value());

            throw new ProductException("Product is not available or his is empty. ", HttpStatus.NO_CONTENT);
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

            throw new ProductException("Product with productId: " + productId + " is not available.",
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
            throw new ProductException("Product with productIds: " + startId + "and " + endId + " is not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    public ProductUA createProductFromProductUaDTO(ProductUaDTO productUaDTO) {
        ProductUA productUA = new ProductUA();
        productUA.setProductName(productUaDTO.getProductName());
        productUA.setBrands(productUaDTO.getBrands());
        productUA.setType(productUaDTO.getType());
        productUA.setSubtype(productUaDTO.getSubtype());
        productUA.setFormOfIssue(productUaDTO.getFormOfIssue());
        productUA.setProducingCountry(productUaDTO.getProducingCountry());
        productUA.setTaste(productUaDTO.getTaste());
        productUA.setPrice(productUaDTO.getPrice());
        productUA.setCurrency(productUaDTO.getCurrency());
        productUA.setArticle(productUaDTO.getArticle());
        productUA.setProductAmount(productUaDTO.getProductAmount());
        productUA.setDescription(productUaDTO.getDescription());
        productUA.setProductConsist(productUaDTO.getProductConsist());
        productUA.setRating(productUaDTO.getRating());
        productUA.setStatus(productUaDTO.getStatus());
        productUA.setPromotion(productUaDTO.getPromotion());

        return productUA;
    }

    public ProductUaDTO createProductDTOFromProductUA(ProductUA productUA) {
        return ProductUaDTO.builder()
                .productName(productUA.getProductName())
                .brands(productUA.getBrands())
                .type(productUA.getType())
                .subtype(productUA.getSubtype())
                .formOfIssue(productUA.getFormOfIssue())
                .producingCountry(productUA.getProducingCountry())
                .taste(productUA.getTaste())
                .price(productUA.getPrice())
                .currency(productUA.getCurrency())
                .article(productUA.getArticle())
                .productAmount(productUA.getProductAmount())
                .description(productUA.getDescription())
                .productConsist(productUA.getProductConsist())
                .rating(productUA.getRating())
                .status(productUA.getStatus())
                .promotion(productUA.getPromotion())
                .build();
    }

    public List<ProductUaDTO> convertToProductDTOList(List<ProductUA> productList) {
        return productList
                .stream()
                .map(this::createProductDTOFromProductUA)
                .toList();
    }


}
