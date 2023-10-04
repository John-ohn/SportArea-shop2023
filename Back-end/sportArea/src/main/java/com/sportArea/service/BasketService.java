package com.sportArea.service;

import com.sportArea.entity.dto.BasketDTO;


import java.util.List;

public interface BasketService {

    BasketDTO findById(Long basketId);

    List<BasketDTO> findAll();

    List<BasketDTO> findByGuestId(Long guestId);

    void save(BasketDTO basket);

    void delete(Long basketId);

    void deleteByGuestId(Long guestId);
}
