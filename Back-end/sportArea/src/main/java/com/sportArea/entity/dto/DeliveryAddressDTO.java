package com.sportArea.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddressDTO {

    private Long deliveryId;

//    private String name;
//
//    private String phoneNumber;

    private String region;

    private String city;

    private String department;

//    private Long userId;
}
