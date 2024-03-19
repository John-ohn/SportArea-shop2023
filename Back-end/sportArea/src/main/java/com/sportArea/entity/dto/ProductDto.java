package com.sportArea.entity.dto;

import com.sportArea.entity.ProductEN;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long productId;

    private ProductUaDTO productUa;

    private ProductEN productEn;

}
