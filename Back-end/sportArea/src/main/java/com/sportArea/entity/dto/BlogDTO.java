package com.sportArea.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDTO {
    private Long blogId;

    private String title;

    private String subTitle;

    private String text;

    private String urlMainImage;
}
