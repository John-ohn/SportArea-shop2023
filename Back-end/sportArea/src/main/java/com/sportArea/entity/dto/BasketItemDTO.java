package com.sportArea.entity.dto;

import com.sportArea.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketItemDTO {

//        private Long basketId;

        private Long customerId;

        private Role customerRole;

        private Long productId;

        private Integer productQuantity;


}


