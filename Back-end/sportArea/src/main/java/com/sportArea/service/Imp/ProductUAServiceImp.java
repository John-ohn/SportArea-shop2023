package com.sportArea.service.Imp;

import com.sportArea.dao.ProductUARepository;
import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.ProductUAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


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
            throw new GeneralException("Product with productId: " + productId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ProductUA findByIdInProduct(Long productId) {
        Optional<ProductUA> product = productRepository.findById(productId);

        if (product.isPresent()) {
            logger.info("From ProductUAServiceImp method -findById- return Product by id: {} ", productId);

            return product.get();
        } else {
            logger.warn("From ProductUAServiceImp method -findById- send war message " +
                    "( Product with productId {} is not available. ({}))", productId, HttpStatus.NOT_FOUND);
            throw new GeneralException("Product with productId: " + productId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductUaDTO> findAll() {
        List<ProductUA> productList = productRepository.findAll();
        if (!productList.isEmpty()) {

            List<ProductUaDTO> productDTOList = convertToProductDTOList(productList);

            logger.info("From ProductUAServiceImp method -findAll- return List of ProductUaDTOs.");

            return productDTOList;
        } else {
            throw new GeneralException("Don't find any Products. Products list is empty.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<ProductUaDTO> searchByKeyWordInDescription(String keyWord) {

        return checkConvertSortKeyWordDescription(keyWord,
                "searchByKeyWordInDescription",
                productRepository::searchByKeyWordInDescription);
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

                throw new GeneralException("Product with keyWord: " + keyWord + " not found.",
                        HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchByKeyWordInTypeSubtype- send war message " +
                    "Key word is empty or null . ({})", HttpStatus.NOT_FOUND.name());

            throw new GeneralException("Key word is empty or null" + keyWord,
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
            throw new GeneralException("List with promotion products is empty or not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void save(ProductUaDTO product) {

        if (product != null) {
            ProductUA productUa = createProductFromProductUaDTO(product);
            LocalDateTime localDateTime = LocalDateTime.now();
            productUa.setDateCreation(localDateTime);
            productRepository.save(productUa);

            logger.info("From ProductUAServiceImp method -save- return new message (Product was added successfully.).");


        } else {
            logger.warn("From ProductUAServiceImp method -save- send war message " +
                    "( Product is not available or his is empty. ({}))", HttpStatus.NOT_FOUND.value());

            throw new GeneralException("Product is not available or his is empty. ", HttpStatus.NOT_FOUND);
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

            throw new GeneralException("Product with productId: " + productId + " is not available.",
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
            throw new GeneralException("Product with productIds: " + startId + "and " + endId + " is not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductUaDTO> searchProducts(String keyWord,
                                     String searchLocation) {

        List<ProductUaDTO> products;
        switch (searchLocation) {
            case "main-search":
                products = searchAndSortKeyWordTypeSubtypeFromDataBase(keyWord);
                return products;
            case "description-search":
                products = searchAndSortKeyWordInDescription(keyWord);
                return products;
            case "best-price":
                products = searchAndSortPromotionPriceFromDataBase();
                return products;
            default:
                return new ArrayList<>();
        }

    }

    @Override
    public List<ProductUaDTO> searchAndSort(String keyWord,
                                            String sortBy,
                                            String searchLocation,
                                            String priceBetween,
                                            BigDecimal lowPrice,
                                            BigDecimal highPrice) {

        List<ProductUaDTO> products;
        switch (searchLocation) {
            case "main-search":
                products = searchAndSortKeyWordTypeSubtypeFromDataBase(keyWord, sortBy, priceBetween, lowPrice, highPrice);
                return products;
            case "description-search":
                products = searchAndSortKeyWordInDescription(keyWord, sortBy, priceBetween, lowPrice, highPrice);
                return products;
            case "best-price":
                products = searchAndSortPromotionPriceFromDataBase(sortBy, priceBetween, lowPrice, highPrice);
                return products;
            default:
                return new ArrayList<>();
        }

    }

    public List<ProductUaDTO> searchAndSortKeyWordTypeSubtypeFromDataBase(String keyWord) {
        if (!keyWord.isEmpty()) {
            List<ProductUA> products = productRepository.searchByKeyWordInTypeSubtype(keyWord);
            if (!products.isEmpty()) {
                List<ProductUaDTO> productsList = convertToProductDTOList(products);
                return productsList;
            } else {
                logger.warn("From ProductUAServiceImp method -searchAndSortKeyWordTypeSubtypeFromDataBase-.Nothing found from keyword ( {} ) send war message " +
                        "(Products List is not available or his is empty.)", keyWord);
                throw new GeneralException("Products List is not available or his is empty.", HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchAndSortKeyWordTypeSubtypeFromDataBase- send war message " +
                    "(Key Word is not available or his is empty.)");
            throw new GeneralException("Key Word is not available or his is empty.", HttpStatus.NOT_FOUND);
        }


    }

    public List<ProductUaDTO> searchAndSortKeyWordTypeSubtypeFromDataBase(String keyWord,
                                                                          String sortBy,
                                                                          String priceBetween,
                                                                          BigDecimal lowPrice,
                                                                          BigDecimal highPrice) {
        if (!keyWord.isEmpty()) {
            List<ProductUA> products = productRepository.searchByKeyWordInTypeSubtype(keyWord);
            if (!products.isEmpty()) {
                if (priceBetween.equals("true")) {
                    if (lowPrice.compareTo(BigDecimal.ZERO) >= 0
                            && highPrice.compareTo(BigDecimal.ZERO) > 0
                            && highPrice.compareTo(lowPrice) > (lowPrice.compareTo(highPrice))
                    ) {
                        if (sortBy.isEmpty()) {
                            sortBy = "priceAsc";
                        }

                        List<ProductUaDTO> productsList = sortBy(products, sortBy, lowPrice, highPrice);

                        return productsList;
                    }
                } else {
                    List<ProductUaDTO> productsList = sortBy(products, sortBy);

                    return productsList;
                }
            } else {
                logger.warn("From ProductUAServiceImp method -searchAndSortKeyWordTypeSubtypeFromDataBase-.Nothing found from keyword ( {} ) send war message " +
                        "(Products List is not available or his is empty.)", keyWord);
                throw new GeneralException("Products List is not available or his is empty.", HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchAndSortKeyWordTypeSubtypeFromDataBase- send war message " +
                    "(Key Word is not available or his is empty.)");
            throw new GeneralException("Key Word is not available or his is empty.", HttpStatus.NOT_FOUND);
        }

        return new ArrayList<>();
    }

    public List<ProductUaDTO> searchAndSortKeyWordInDescription(String keyWord) {
        if (!keyWord.isEmpty()) {
            List<ProductUA> products = productRepository.searchByKeyWordInDescription(keyWord);
            if (!products.isEmpty()) {

                List<ProductUaDTO> productsList = convertToProductDTOList(products);
                return productsList;

            } else {
                logger.warn("From ProductUAServiceImp method -searchAndSortKeyWordInDescription-. Nothing found from keyword ( {} ) send war message " +
                        "(Products List is not available or his is empty.)", keyWord);
                throw new GeneralException("Products List is not available or his is empty.", HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchAndSortKeyWordInDescription- send war message " +
                    "(Key Word is not available or his is empty.)");
            throw new GeneralException("Key Word is not available or his is empty.", HttpStatus.NOT_FOUND);
        }

    }

    public List<ProductUaDTO> searchAndSortKeyWordInDescription(String keyWord,
                                                                String sortBy,
                                                                String priceBetween,
                                                                BigDecimal lowPrice,
                                                                BigDecimal highPrice) {
        if (!keyWord.isEmpty()) {
            List<ProductUA> products = productRepository.searchByKeyWordInDescription(keyWord);
            if (!products.isEmpty()) {
                if (priceBetween.equals("true")) {
                    if (lowPrice.compareTo(BigDecimal.ZERO) >= 0
                            && highPrice.compareTo(BigDecimal.ZERO) > 0
                            && highPrice.compareTo(lowPrice) > (lowPrice.compareTo(highPrice))
                    ) {

                        if (sortBy.isEmpty()) {
                            sortBy = "priceAsc";
                        }

                        List<ProductUaDTO> productsList = sortBy(products, sortBy, lowPrice, highPrice);
                        return productsList;
                    }

                } else {
                    List<ProductUaDTO> productsList = sortBy(products, sortBy);

                    return productsList;
                }

            } else {
                logger.warn("From ProductUAServiceImp method -searchAndSortKeyWordInDescription-. Nothing found from keyword ( {} ) send war message " +
                        "(Products List is not available or his is empty.)", keyWord);
                throw new GeneralException("Products List is not available or his is empty.", HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchAndSortKeyWordInDescription- send war message " +
                    "(Key Word is not available or his is empty.)");
            throw new GeneralException("Key Word is not available or his is empty.", HttpStatus.NOT_FOUND);
        }

        return new ArrayList<>();
    }

    public List<ProductUaDTO> searchAndSortPromotionPriceFromDataBase() {

        List<ProductUA> products = productRepository.searchByPromotionPrice();
        if (!products.isEmpty()) {

            List<ProductUaDTO> productsList = convertToProductDTOList(products);
            return productsList;
        } else {
            logger.warn("From ProductUAServiceImp method -searchAndSortPromotionPriceFromDataBase-. Nothing found send war message " +
                    "(Products List is not available or his is empty.)");
            throw new GeneralException("Products List is not available or his is empty.", HttpStatus.NOT_FOUND);
        }
    }

    public List<ProductUaDTO> searchAndSortPromotionPriceFromDataBase(String sortBy,
                                                                      String priceBetween,
                                                                      BigDecimal lowPrice,
                                                                      BigDecimal highPrice) {

        List<ProductUA> products = productRepository.searchByPromotionPrice();
        if (!products.isEmpty()) {
            if (priceBetween.equals("true")) {
                if (lowPrice.compareTo(BigDecimal.ZERO) >= 0
                        && highPrice.compareTo(BigDecimal.ZERO) > 0
                        && highPrice.compareTo(lowPrice) > (lowPrice.compareTo(highPrice))
                ) {
                    if (sortBy.equals("priceAsc")) {
                        sortBy = "promotionPriceBetweenAsc";
                    } else if (sortBy.equals("priceDesc")) {
                        sortBy = "promotionPriceBetweenDesc";
                    } else if (sortBy.isEmpty()) {
                        sortBy = "promotionPriceBetweenAsc";
                    }

                    List<ProductUaDTO> productsList = sortBy(products, sortBy, lowPrice, highPrice);

                    return productsList;
                }
            } else {
                if (sortBy.equals("priceAsc")) {
                    sortBy = "promotionPriceAsc";
                } else if (sortBy.equals("priceDesc")) {
                    sortBy = "promotionPriceDesc";
                }

                List<ProductUaDTO> productsList = sortBy(products, sortBy);

                return productsList;
            }
        } else {
            logger.warn("From ProductUAServiceImp method -searchAndSortPromotionPriceFromDataBase-. Nothing found send war message " +
                    "(Products List is not available or his is empty.)");
            throw new GeneralException("Products List is not available or his is empty.", HttpStatus.NOT_FOUND);
        }
        return new ArrayList<>();
    }

    // The method performs sorting on app side.
    private List<ProductUaDTO> sortBy(List<ProductUA> list,
                                      String sortBy,
                                      BigDecimal lowPrice,
                                      BigDecimal highPrice) {
        List<ProductUaDTO> productsList = new ArrayList<>(convertToProductDTOList(list));
        if (!(sortBy.isEmpty())) {
            switch (sortBy) {
                case "rating":
                    // Sort products by rating logic
                    productsList = productsList.stream().filter(product ->
                                    (product.getPrice().compareTo(lowPrice)) >= 0 && (product.getPrice().compareTo(highPrice)) <= 0)
                            .sorted(Comparator.comparing(ProductUaDTO::getRating).reversed())
                            .toList();
                    break;
                case "priceAsc":
                    // Sort products by price asc logic
                    productsList = productsList.stream().filter(product ->
                                    (product.getPrice().compareTo(lowPrice)) >= 0 && (product.getPrice().compareTo(highPrice)) <= 0)
                            .sorted(Comparator.comparing(ProductUaDTO::getPrice))
                            .toList();
                    break;
                case "priceDesc":
                    // Sort products by price desc logic
                    productsList = productsList.stream().filter(product ->
                                    (product.getPrice().compareTo(lowPrice)) >= 0 && (product.getPrice().compareTo(highPrice)) <= 0)
                            .sorted(Comparator.comparing(ProductUaDTO::getPrice).reversed())
                            .toList();
                    break;
                case "new":
                    // Sort products by datetime logic
                    productsList = productsList.stream().filter(product ->
                                    (product.getPrice().compareTo(lowPrice)) >= 0 && (product.getPrice().compareTo(highPrice)) <= 0)
                            .sorted(Comparator.comparing(ProductUaDTO::getDateCreation).reversed())
                            .toList();
                    break;
                case "popular":
                    // Sort products by popularity logic
                    productsList = productsList.stream().filter(product ->
                                    (product.getPrice().compareTo(lowPrice)) >= 0 && (product.getPrice().compareTo(highPrice)) <= 0)
                            .sorted(Comparator.comparing(ProductUaDTO::getNumberOfOrders).reversed())
                            .toList();
                    break;
                case "name":
                    // Sort products by name logic
                    productsList = productsList.stream().filter(product ->
                                    (product.getPrice().compareTo(lowPrice)) >= 0 && (product.getPrice().compareTo(highPrice)) <= 0)
                            .sorted(Comparator.comparing(ProductUaDTO::getProductName))
                            .toList();
                    break;
//                case "priceBetweenAse":
//                    productsList = productsList.stream().filter(product ->
//                                    (product.getPrice().compareTo(lowPrice)) >= 0 && (product.getPrice().compareTo(highPrice)) <= 0)
//                            .sorted(Comparator.comparing(ProductUaDTO::getPrice))
//                            .toList();
//                    break;
//                case "priceBetweenDesc":
//                    productsList = productsList.stream().filter(product ->
//                                    (product.getPrice().compareTo(lowPrice)) >= 0 && (product.getPrice().compareTo(highPrice)) <= 0)
//                            .sorted(Comparator.comparing(ProductUaDTO::getPrice).reversed())
//                            .toList();
//                    break;

                case "promotionPriceBetweenAsc":
                    productsList = productsList.stream().filter(product ->
                                    (product.getPromotionPrice().compareTo(lowPrice)) >= 0 && (product.getPromotionPrice().compareTo(highPrice)) <= 0)
                            .sorted(Comparator.comparing(ProductUaDTO::getPromotionPrice))
                            .toList();
                    break;
                case "promotionPriceBetweenDesc":
                    productsList = productsList.stream().filter(product ->
                                    (product.getPromotionPrice().compareTo(lowPrice)) >= 0 && (product.getPromotionPrice().compareTo(highPrice)) <= 0)
                            .sorted(Comparator.comparing(ProductUaDTO::getPromotionPrice).reversed())
                            .toList();
                    break;
                default:
                    // Handle default case or throw an exception
                    break;
            }
        }

        return productsList;
    }

    private List<ProductUaDTO> sortBy(List<ProductUA> list, String sortBy) {
        List<ProductUaDTO> productsList = new ArrayList<>(convertToProductDTOList(list));
        if (!(sortBy.isEmpty())) {
            switch (sortBy) {
                case "rating":
                    // Sort products by rating logic
                    productsList.sort(Comparator.comparing(ProductUaDTO::getRating).reversed());
                    break;
                case "priceAsc":
                    // Sort products by price asc logic
                    productsList.sort(Comparator.comparing(ProductUaDTO::getPrice));
                    break;
                case "priceDesc":
                    // Sort products by price desc logic
                    productsList.sort(Comparator.comparing(ProductUaDTO::getPrice).reversed());
                    break;
                case "new":
                    // Sort products by datetime logic
                    productsList.sort(Comparator.comparing(ProductUaDTO::getDateCreation).reversed());
                    break;
                case "popular":
                    // Sort products by popularity logic
                    productsList.sort(Comparator.comparing(ProductUaDTO::getNumberOfOrders).reversed());
                    break;
                case "name":
                    // Sort products by name logic
                    productsList.sort(Comparator.comparing(ProductUaDTO::getProductName));
                    break;
                case "promotionPriceAse":
                    productsList.sort(Comparator.comparing(ProductUaDTO::getPromotionPrice));
                    break;
                case "promotionPriceDesc":
                    productsList.sort(Comparator.comparing(ProductUaDTO::getPromotionPrice).reversed());
                    break;
                default:
                    // Handle default case or throw an exception
                    break;
            }
        }

        return productsList;
    }

    @Override
    public ProductUA createProductFromProductUaDTO(ProductUaDTO productUaDTO) {
        ProductUA productUA = new ProductUA();
        productUA.setProductId(productUaDTO.getProductId());
        productUA.setProductName(productUaDTO.getProductName());
        productUA.setBrands(productUaDTO.getBrands());
        productUA.setType(productUaDTO.getType());
        productUA.setSubtype(productUaDTO.getSubtype());
        productUA.setFormOfIssue(productUaDTO.getFormOfIssue());
        productUA.setProducingCountry(productUaDTO.getProducingCountry());
        productUA.setTaste(productUaDTO.getTaste());
        productUA.setPrice(productUaDTO.getPrice());
        productUA.setPromotionPrice(productUaDTO.getPromotionPrice());
        productUA.setCurrency(productUaDTO.getCurrency());
        productUA.setWeight(productUaDTO.getWeight());
        productUA.setArticle(productUaDTO.getArticle());
        productUA.setProductAmount(productUaDTO.getProductAmount());
        productUA.setDescription(productUaDTO.getDescription());
        productUA.setProductConsist(productUaDTO.getProductConsist());
        productUA.setRating(productUaDTO.getRating());
        productUA.setStatus(productUaDTO.getStatus());
        productUA.setPromotion(productUaDTO.getPromotion());
        productUA.setNumberOfOrders(productUaDTO.getNumberOfOrders());
        productUA.setDateCreation(productUaDTO.getDateCreation());
        productUA.setUrlImage(productUaDTO.getUrlImage());

        return productUA;
    }

    @Override
    public ProductUaDTO createProductDTOFromProductUA(ProductUA productUA) {

        return ProductUaDTO.builder()
                .productId(productUA.getProductId())
                .productName(productUA.getProductName())
                .brands(productUA.getBrands())
                .type(productUA.getType())
                .subtype(productUA.getSubtype())
                .formOfIssue(productUA.getFormOfIssue())
                .producingCountry(productUA.getProducingCountry())
                .taste(productUA.getTaste())
                .price(productUA.getPrice())
                .promotionPrice(productUA.getPromotionPrice())
                .weight(productUA.getWeight())
                .currency(productUA.getCurrency())
                .article(productUA.getArticle())
                .productAmount(productUA.getProductAmount())
                .description(productUA.getDescription())
                .productConsist(productUA.getProductConsist())
                .rating(productUA.getRating())
                .status(productUA.getStatus())
                .promotion(productUA.getPromotion())
                .numberOfOrders(productUA.getNumberOfOrders())
                .dateCreation(productUA.getDateCreation())
                .urlImage(productUA.getUrlImage())
                .build();
    }

    @Override
    public List<ProductUA> convertToProductList(List<ProductUaDTO> productList) {
        return productList
                .stream()
                .map(this::createProductFromProductUaDTO)
                .toList();
    }

    @Override
    public List<ProductUaDTO> convertToProductDTOList(List<ProductUA> productList) {
        return productList
                .stream()
                .map(this::createProductDTOFromProductUA)
                .toList();
    }

    public List<ProductUaDTO> checkConvertSortKeyWordDescription(String keyWord,
                                                                 String methodName,
                                                                 Function<String, List<ProductUA>> sortingMethod) {
        if (!keyWord.isEmpty()) {
            List<ProductUA> productUAList = sortingMethod.apply(keyWord);
            if (!productUAList.isEmpty()) {
                List<ProductUaDTO> productUaDTOList = convertToProductDTOList(productUAList);
                logger.info("From ProductUAServiceImp method -{}- return List Products", methodName);
                return productUaDTOList;
            } else {
                logger.warn("From ProductUAServiceImp method -{}-. Nothing found from keyword ( {} ) send war message " +
                        "(Products List is not available or his is empty.)", methodName, keyWord);
                throw new GeneralException("Products List is not available or his is empty.", HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From ProductUAServiceImp method -{}- send war message " +
                    "(Keyword is not available or his is empty.)", methodName);
            throw new GeneralException("Keyword is not available or his is empty.", HttpStatus.NOT_FOUND);
        }
    }


}
