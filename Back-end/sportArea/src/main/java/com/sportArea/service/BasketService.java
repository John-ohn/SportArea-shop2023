package com.sportArea.service;

import com.sportArea.entity.Role;
import com.sportArea.entity.dto.BasketDTO;
import com.sportArea.entity.dto.BasketItemDTO;
import com.sportArea.entity.Customer;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

public interface BasketService {

    BasketDTO findById(Long basketId);

    List<BasketDTO> findAll();

    List<BasketDTO> findByGuestId(Long id);

    void save(BasketItemDTO basketItem);

    void delete(Long basketId);

    void deleteByGuestId(Long guestId);
}
