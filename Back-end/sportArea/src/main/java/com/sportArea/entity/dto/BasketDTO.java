package com.sportArea.entity.dto;

import com.sportArea.entity.ProductUA;
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

    private Long guestId;

    private ProductUaDTO product;
}
