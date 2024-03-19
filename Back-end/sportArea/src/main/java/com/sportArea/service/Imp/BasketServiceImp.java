package com.sportArea.service.Imp;

import com.sportArea.dao.BasketItemRepository;
import com.sportArea.dao.BasketRepository;
import com.sportArea.entity.*;
import com.sportArea.entity.dto.BasketDTO;
import com.sportArea.entity.dto.BasketItemDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.BasketService;
import com.sportArea.service.ProductUAService;
import com.sportArea.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BasketServiceImp implements BasketService {

    Logger logger = LoggerFactory.getLogger(BasketServiceImp.class);
    private final BasketRepository basketRepository;

    private final BasketItemRepository basketItemRepository;

    private final CustomerService customerService;
    private final ProductUAService productUAService;

    @Autowired
    public BasketServiceImp(BasketRepository basketRepository,
                            ProductUAService productUAService,
                            CustomerService customerService,
                            BasketItemRepository basketItemRepository
    ) {
        this.basketRepository = basketRepository;
        this.productUAService = productUAService;
        this.customerService = customerService;
        this.basketItemRepository = basketItemRepository;

    }

    @Override
    public BasketDTO findByUserId(Long userId) {
        Basket basket = basketRepository.findByUserId(userId).get();

        return convertToDTO(basket);
    }

    @Override
    public List<BasketDTO> findAll() {
        return null;
    }

//    @Override
//    public List<BasketDTO> findByGuestId(Long customerId) {
//        if (customerId > 0) {
////            if (customerRole.equals(Role.ROLE_GUEST.toString()) || customerRole.equals(Role.ROLE_USER.toString())) {
//                List<Basket> basketList = basketRepository.findByGuestId(customerId);
//                if (!basketList.isEmpty()) {
//                    List<BasketDTO> basketDTOList = convertToDTOList(basketList);
//
//                    return basketDTOList;
//                } else {
//                    return new ArrayList<>();
//                }
////            } else if (customerRole.equals(Role.ROLE_USER)) {
////                List<Basket> basketList = basketRepository.findByUserId(customerId);
////                if (!basketList.isEmpty()) {
////                    List<BasketDTO> basketDTOList = convertToDTOList(basketList);
////
////                    return basketDTOList;
////                } else {
////                    return new ArrayList<>();
////                }
////            } else {
////                throw new GeneralException("Don't find role. Pleas set correct role parameter (ROLE_GUEST or ROLE_USER).", HttpStatus.BAD_REQUEST);
////            }
//        } else {
//            throw new GeneralException("Don't find id. Pleas set correct id number.", HttpStatus.BAD_REQUEST);
//        }
//    }

    @Override
    public BasketDTO createUpdateBasket(Long userId, Long productId, Integer productQuantity) {
        if (userId <= 0 || productId <= 0) {
            throw new GeneralException("Don't user id or find product id. Pleas set correct id number.", HttpStatus.BAD_REQUEST);
        }

        Optional<Basket> basketCheck = basketRepository.findByUserId(userId);
        if (basketCheck.isEmpty()) {
            BasketItem basketItem = createBasketItem(productId, productQuantity);
            Customer customer = customerService.findByIdInUser(userId);


            BasketItem saveBasketItem = basketItemRepository.save(basketItem);
            Basket newBasket = new Basket();
            newBasket.setCustomer(customer);
            newBasket.addBasketItemToBasket(saveBasketItem);
            Integer productListSize = newBasket.getProducts().size();
            newBasket.setProductQuantity(productListSize);
            BigDecimal basketTotalPrice = newBasket
                    .getProducts()
                    .stream()
                    .map(BasketItem::getProductTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            newBasket.setBasketTotalPrice(basketTotalPrice);
            Basket basket = basketRepository.save(newBasket);

            return convertToDTO(basket);
        } else {

            BasketDTO basket = updateBasket(
                    basketCheck
                            .get()
                            .getBasketId(), productId, productQuantity);
            return basket;

        }

//     throw new GeneralException("You can not add product in basket,that product amount is not available.", HttpStatus.BAD_REQUEST);

    }


    public BasketDTO updateBasket(Long basketId, Long productId, Integer productQuantity) {

        BasketItem basketItem = createBasketItem(productId, productQuantity);
//        User user = userService.findByIdInUser(userId);

        Basket basket = basketRepository.findById(basketId).orElseThrow(() ->
                new GeneralException("Don't find id. Pleas set correct basket id or user id number.", HttpStatus.BAD_REQUEST));

//        basketItem.addBasketToBasketItem(basket);
//        BasketItem saveBasketItem = basketItemRepository.save(basketItem);
//        basketRepository.flush();
        basket.addBasketItemToBasket(basketItem);
        Integer productListSize = basket.getProducts().size();
        logger.info("productListSize is {}", productListSize);
        basket.setProductQuantity(productListSize);
        BigDecimal basketTotalPrice = basket
                .getProducts()
                .stream()
                .map(BasketItem::getProductTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        logger.info("basketTotalPrice is {}", basketTotalPrice);
        basket.setBasketTotalPrice(basketTotalPrice);
        logger.info("basket basketTotalPrice is {}", basket.getBasketTotalPrice());
        logger.info("basket productListSize is {}", basket.getProductQuantity());

//        basketRepository.save(basket);
        basketItemRepository.save(basketItem);
        basketRepository.update(basket.getBasketTotalPrice(),
                basket.getProductQuantity(),
                basket.getCustomer().getUserId());


        return convertToDTO(basket);
    }

    @Override
    public BasketItem createBasketItem(Long productId, Integer productQuantity) {

        ProductUA product = productUAService.findByIdWithoutDTO(productId);
        BigDecimal productPrice;
        if (product.getPromotionPrice() == null || product.getPromotionPrice().equals(BigDecimal.ZERO)
                || product.getPromotionPrice().compareTo(BigDecimal.ZERO) < 0) {

            productPrice = product.getPrice();
        } else {
            productPrice = product.getPromotionPrice();
        }

        BasketItem basketItem = new BasketItem();
        basketItem.setProduct(product);
        if (productQuantity == null || productQuantity <= 0
                || productQuantity == 1) {
            basketItem.setProductQuantity(1);
            basketItem.setProductTotalPrice(productPrice);
        } else {
            basketItem.setProductQuantity(productQuantity);
            basketItem.setProductTotalPrice(productPrice.multiply(BigDecimal.valueOf(productQuantity)));
        }

        return basketItem;

    }


    @Override
    public void delete(Long basketId) {

    }

//    @Override
//    public void deleteByGuestId(Long guestId) {
//
//        basketRepository.deleteByGuestId(guestId);
//
//    }

    private Basket convertToEntity(BasketDTO basket) {
        if (basket.getBasketId() != null) {
            return Basket.builder()
                    .basketId(basket.getBasketId())
                    .customer(customerService.createUserFromUserDTO(basket.getUser()))
                    .productQuantity(basket.getProductQuantity())
                    .basketTotalPrice(basket.getBasketTotalPrice())
                    .products(convertToList(basket.getProducts()))
                    .build();
        } else {
            return Basket.builder()
                    .customer(customerService.createUserFromUserDTO(basket.getUser()))
                    .productQuantity(basket.getProductQuantity())
                    .basketTotalPrice(basket.getBasketTotalPrice())
                    .products(convertToList(basket.getProducts()))
                    .build();
        }
    }

    private BasketDTO convertToDTO(Basket basket) {
        if (basket.getBasketId() != null) {
            return BasketDTO.builder()
                    .basketId(basket.getBasketId())
                    .user(customerService.createUserDTOFromUser(basket.getCustomer()))
                    .productQuantity(basket.getProductQuantity())
                    .basketTotalPrice(basket.getBasketTotalPrice())
                    .products(convertToItemDTOList(basket.getProducts()))
                    .build();
        } else {
            return BasketDTO.builder()
                    .user(customerService.createUserDTOFromUser(basket.getCustomer()))
                    .productQuantity(basket.getProductQuantity())
                    .basketTotalPrice(basket.getBasketTotalPrice())
                    .products(convertToItemDTOList(basket.getProducts()))
                    .build();
        }
    }

    private List<BasketDTO> convertToDTOList(List<Basket> basketList) {

        return basketList.stream().map(this::convertToDTO).toList();
    }

    private List<BasketItem> convertToList(List<BasketItemDTO> basketList) {

        return basketList.stream().map(this::convertToEntity).toList();
    }


    private BasketItem convertToEntity(BasketItemDTO basket) {
        if (basket.getItemId() != null || basket.getBasket().getBasketId() != null) {
            return BasketItem.builder()
                    .itemId(basket.getItemId())
                    .product(productUAService.createProductFromProductUaDTO(basket.getProduct()))
                    .basket(convertToEntity(basket.getBasket()))
                    .productQuantity(basket.getProductQuantity())
                    .productTotalPrice(basket.getProductTotalPrice())
                    .build();
        } else {
            return BasketItem.builder()
                    .product(productUAService.createProductFromProductUaDTO(basket.getProduct()))
                    .productQuantity(basket.getProductQuantity())
                    .productTotalPrice(basket.getProductTotalPrice())
                    .build();
        }
    }

    private BasketItemDTO convertToItemDTOEntity(BasketItem basket) {
        if (basket.getItemId() != null || basket.getBasket().getBasketId() != null) {
            return BasketItemDTO.builder()
                    .itemId(basket.getItemId())
                    .product(productUAService.createProductDTOFromProductUA(basket.getProduct()))
//                    .basket(convertToDTO(basket.getBasket()))
                    .productQuantity(basket.getProductQuantity())
                    .productTotalPrice(basket.getProductTotalPrice())
                    .build();
        } else {
            return BasketItemDTO.builder()
                    .product(productUAService.createProductDTOFromProductUA(basket.getProduct()))
                    .productQuantity(basket.getProductQuantity())
                    .productTotalPrice(basket.getProductTotalPrice())
                    .build();
        }
    }

    private List<BasketItemDTO> convertToItemDTOList(List<BasketItem> basketList) {

        return basketList.stream().map(this::convertToItemDTOEntity).toList();
    }
}
