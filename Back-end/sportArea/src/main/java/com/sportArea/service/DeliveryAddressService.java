package com.sportArea.service;

import com.sportArea.entity.dto.DeliveryAddressDTO;
import com.sportArea.entity.dto.delivery.DeliveryAddressRequest;
import com.sportArea.entity.dto.delivery.DeliveryAddressUpdate;

import javax.validation.Valid;

public interface DeliveryAddressService {

    DeliveryAddressDTO findById (Long deliveryId);

    DeliveryAddressDTO  findByUserId(Long userId);

    void save(DeliveryAddressDTO address);

    DeliveryAddressUpdate createForUpdateRequest(Long deliveryId, DeliveryAddressRequest fieldName);

    void validAndUpdateAddress(@Valid DeliveryAddressUpdate updates);

}
