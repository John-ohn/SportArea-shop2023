package com.sportArea.entity.dto.novaPost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private MethodProperties methodProperties;

}
