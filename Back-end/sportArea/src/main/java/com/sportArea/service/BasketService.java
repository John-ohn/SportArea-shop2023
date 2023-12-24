package com.sportArea.service;

import com.sportArea.entity.BasketItem;
import com.sportArea.entity.dto.BasketDTO;
import com.sportArea.entity.dto.BasketItemDTO;


import java.util.List;

public interface BasketService {

    BasketDTO findByUserId(Long userId);

    List<BasketDTO> findAll();

//    List<BasketDTO> findByGuestId(Long id);

//    void save(BasketItemDTO basketItem);

    BasketDTO createUpdateBasket(Long userId, Long productId, Integer productQuantity);

    BasketItem createBasketItem(Long productId, Integer productQuantity);

    void delete(Long basketId);

//    void deleteByGuestId(Long guestId);
}
