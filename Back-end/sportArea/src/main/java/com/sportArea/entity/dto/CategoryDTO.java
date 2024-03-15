package com.sportArea.entity.dto;



import com.sportArea.entity.CategoryEn;
import com.sportArea.entity.CategoryUa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {


    private Long categoryId;

    private CategoryUa categoryUa;

    private CategoryEn categoryEn;
}
