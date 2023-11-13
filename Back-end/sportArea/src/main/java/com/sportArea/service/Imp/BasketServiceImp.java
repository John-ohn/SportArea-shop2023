package com.sportArea.service.Imp;

import com.sportArea.dao.BasketRepository;
import com.sportArea.dao.CustomerRepository;
import com.sportArea.entity.Basket;
import com.sportArea.entity.ProductUA;
import com.sportArea.entity.Role;
import com.sportArea.entity.dto.BasketDTO;
import com.sportArea.entity.dto.BasketItemDTO;
import com.sportArea.entity.Customer;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.BasketService;
import com.sportArea.service.ProductUAService;
import com.sportArea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BasketServiceImp implements BasketService {

    private final BasketRepository basketRepository;

    private CustomerRepository customerRepository;

    private final UserService userService;
    private final ProductUAService productUAService;

    @Autowired
    public BasketServiceImp(BasketRepository basketRepository,
                            ProductUAService productUAService,
                            UserService userService,
                            CustomerRepository customerRepository) {
        this.basketRepository = basketRepository;
        this.productUAService = productUAService;
        this.userService = userService;
        this.customerRepository=customerRepository;
    }

    @Override
    public BasketDTO findById(Long basketId) {
        Basket basket = basketRepository.findById(basketId).get();

        return convertToDTO(basket);
    }

    @Override
    public List<BasketDTO> findAll() {
        return null;
    }

    @Override
    public List<BasketDTO> findByGuestId(Long customerId) {
        if (customerId > 0) {
//            if (customerRole.equals(Role.ROLE_GUEST.toString()) || customerRole.equals(Role.ROLE_USER.toString())) {
                List<Basket> basketList = basketRepository.findByGuestId(customerId);
                if (!basketList.isEmpty()) {
                    List<BasketDTO> basketDTOList = convertToDTOList(basketList);

                    return basketDTOList;
                } else {
                    return new ArrayList<>();
                }
//            } else if (customerRole.equals(Role.ROLE_USER)) {
//                List<Basket> basketList = basketRepository.findByUserId(customerId);
//                if (!basketList.isEmpty()) {
//                    List<BasketDTO> basketDTOList = convertToDTOList(basketList);
//
//                    return basketDTOList;
//                } else {
//                    return new ArrayList<>();
//                }
//            } else {
//                throw new GeneralException("Don't find role. Pleas set correct role parameter (ROLE_GUEST or ROLE_USER).", HttpStatus.BAD_REQUEST);
//            }
        } else {
            throw new GeneralException("Don't find id. Pleas set correct id number.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void save(BasketItemDTO basketItem) {

        ProductUA productUA = productUAService.findByIdInProduct(basketItem.getProductId());
        Customer customer = new Customer();

        if ((productUA.getProductAmount() - 1) > 0) {
            Basket basket = new Basket();
            if (basketItem.getCustomerId() != null && basketItem.getCustomerId() > 0) {
                if(basketItem.getCustomerRole().equals(Role.ROLE_USER) || basketItem.getCustomerRole().equals(Role.ROLE_GUEST)) {
                     customer = new Customer(basketItem.getCustomerId(), basketItem.getCustomerRole());
                    basket.setCustomer(customer);
                }
            }
//            if (basketItem.getGuestId() != null && basketItem.getGuestId() > 0) {
//                Guest guest = new Guest(basketItem.getGuestId(),Role.ROLE_GUEST);
//                basket.setGuest(guest);
//            }
//            if (basketItem.getUserId() != null && basketItem.getUserId() > 0) {
//                basket.setUser(userService.findByIdInUser(basketItem.getUserId()));
//            }
            if (basketItem.getProductId() != null && basketItem.getProductId() > 0) {
                basket.setProductUA(productUA);
            }
            if (basketItem.getProductQuantity() == null || basketItem.getProductQuantity() <= 0) {
                basket.setProductQuantity(1);
            } else {
                basket.setProductQuantity(basketItem.getProductQuantity());
            }

            customerRepository.save(customer);
            basketRepository.save(basket);
        } else {
            throw new GeneralException("You can not add product in basket,that product amount is not available.", HttpStatus.BAD_REQUEST);
        }

    }


    @Override
    public void delete(Long basketId) {

    }

    @Override
    public void deleteByGuestId(Long guestId) {

        basketRepository.deleteByGuestId(guestId);

    }

    private Basket convertToEntity(BasketDTO basket) {
        if (basket.getUser() != null) {
            return Basket.builder()
                    .basketId(basket.getBasketId())
                    .customer(basket.getCustomer())
                    .user(userService.createUserFromUserDTO(basket.getUser()))
                    .productUA(productUAService.createProductFromProductUaDTO(basket.getProduct()))
                    .productQuantity(basket.getProductQuantity())
                    .build();
        } else {
            return Basket.builder()
                    .basketId(basket.getBasketId())
                    .customer(basket.getCustomer())
                    .productUA(productUAService.createProductFromProductUaDTO(basket.getProduct()))
                    .productQuantity(basket.getProductQuantity())
                    .build();
        }
    }

    private BasketDTO convertToDTO(Basket basket) {
        if (basket.getUser() != null) {
            return BasketDTO.builder()
                    .basketId(basket.getBasketId())
                    .customer(basket.getCustomer())
                    .user(userService.createUserDTOFromUser(basket.getUser()))
                    .product(productUAService.createProductDTOFromProductUA(basket.getProductUA()))
                    .productQuantity(basket.getProductQuantity())
                    .build();
        } else {
            return BasketDTO.builder()
                    .basketId(basket.getBasketId())
                    .customer(basket.getCustomer())
                    .product(productUAService.createProductDTOFromProductUA(basket.getProductUA()))
                    .productQuantity(basket.getProductQuantity())
                    .build();
        }
    }

    private List<BasketDTO> convertToDTOList(List<Basket> basketList) {

        return basketList.stream().map(this::convertToDTO).toList();
    }
}
