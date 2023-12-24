package com.sportArea.entity.dto.delivery;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddressRequest {

    private Long deliveryId;

    private String region;

    private String city;

    private String department;

}
