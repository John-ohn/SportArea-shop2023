package com.sportArea.entity.dto.delivery;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddressUpdate {

    @NotNull(message = "Delivery Id can't be empty or null.")
    private Long deliveryId;

    @NotEmpty(message = "Region name can't be empty.")
    @Pattern(regexp = "^[\\p{L}\\s-]{3,30}", message = "Write a correct region name. Use only chars. Min 3 not more than 30.")
    private String region;

    @NotEmpty(message = "City name can't be empty.")
    @Pattern(regexp = "^[\\p{L}\\s-]{3,30}", message = "Write a correct city name. Use only chars. Min 3 not more than 30.")
    private String city;

    @NotEmpty(message = "Department name can't be empty.")
    private String department;


}
