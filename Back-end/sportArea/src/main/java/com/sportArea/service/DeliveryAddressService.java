package com.sportArea.service;

import com.sportArea.entity.dto.DeliveryAddressDTO;

public interface DeliveryAddressService {

    DeliveryAddressDTO findById (Long deliveryId);

    DeliveryAddressDTO  findByUserId(Long userId);

    void save(DeliveryAddressDTO address);

}
