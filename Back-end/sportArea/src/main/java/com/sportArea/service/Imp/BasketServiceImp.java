package com.sportArea.service.Imp;

import com.sportArea.dao.BasketRepository;
import com.sportArea.entity.Basket;
import com.sportArea.entity.dto.BasketDTO;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.BasketService;
import com.sportArea.service.ProductUAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BasketServiceImp implements BasketService {

    private final BasketRepository basketRepository;

    private final ProductUAService productUAService;

    @Autowired
    public BasketServiceImp(BasketRepository basketRepository, ProductUAService productUAService) {
        this.basketRepository = basketRepository;
        this.productUAService = productUAService;
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
    public List<BasketDTO> findByGuestId(Long guestId) {
        List<Basket> basketList = basketRepository.findByGuestId(guestId);

        List<BasketDTO> basketDTOS = convertToDTOList(basketList);


        return basketDTOS;
    }

    @Override
    public void save(BasketDTO basket) {

        ProductUaDTO productUA = productUAService.findById(basket.getProduct().getProductId());
        if ((productUA.getProductAmount() - 1) > 0) {
            basketRepository.save(convertToEntity(basket));
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
        return Basket.builder()
                .basketId(basket.getBasketId())
                .guestId(basket.getGuestId())
                .productUA(productUAService.createProductFromProductUaDTO(basket.getProduct()))
                .build();
    }

    private BasketDTO convertToDTO(Basket basket) {
        return BasketDTO.builder()
                .basketId(basket.getBasketId())
                .guestId(basket.getGuestId())
                .product(productUAService.createProductDTOFromProductUA(basket.getProductUA()))
                .build();
    }

    private List<BasketDTO> convertToDTOList(List<Basket> basketList) {

        return basketList.stream().map(this::convertToDTO).toList();
    }
}
