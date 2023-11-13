package com.sportArea.entity.dto;

import com.sportArea.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketDTO {

    private Long basketId;

//    private Long guestId;

    private Customer customer;

    private UserDTO user;

    private ProductUaDTO product;

    private Integer productQuantity;
}
