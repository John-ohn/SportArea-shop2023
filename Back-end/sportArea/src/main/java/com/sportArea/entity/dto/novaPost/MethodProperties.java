package com.sportArea.entity.dto.novaPost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MethodProperties {

    @JsonProperty("CityName")
    private String CityName;

    @JsonProperty("Language")
    private String Language;

    @JsonProperty("TypeOfWarehouseRef")
    private String TypeOfWarehouseRef;

    @JsonProperty("AreaRef")
    private String AreaRef;

    @JsonProperty("FindByString")
    private String FindByString;

    @JsonProperty("Page")
    private String Page;

    @JsonProperty("Limit")
    private String Limit;
}
