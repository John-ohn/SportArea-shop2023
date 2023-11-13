package com.sportArea.entity.dto;

import com.sportArea.entity.DeliveryAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long orderId;

    private String firstName;

    private String lastName;

    private String email;

    private Long phoneNumber;

    private String paymentMethod;

    private DeliveryAddress delivery;

    private Integer amount;

    private LocalDateTime orderDate;

    private UserDTO user;

    private List<ProductUaDTO> product;
}
