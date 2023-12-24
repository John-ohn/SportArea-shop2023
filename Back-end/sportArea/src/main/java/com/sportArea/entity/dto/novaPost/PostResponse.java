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
public class PostResponse implements Comparable<PostResponse> {

//    @JsonProperty("Ref")
//    private String Ref;
//    @JsonProperty("AreasCenter")
//    private String AreasCenter;

    @JsonProperty("Description")
    private String Description;

    @JsonProperty("AreaDescription")
    private String AreaDescription;

    @JsonProperty("Area")
    private String  Area;

    @JsonProperty("Number")
    private String Number;

    @Override
    public int compareTo(PostResponse other) {
        // Assuming 'Area' is a String, you can use compareTo method for String comparison.
        // If 'Area' is another type, adjust the comparison accordingly.
        return this.AreaDescription.compareTo(other.AreaDescription);
    }


}

