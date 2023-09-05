package com.sportArea.entity.dto;

import com.sportArea.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddComment {

   private String message;

   private Long userId;

   private Long productId;

   private Note note;

   private Float productRating;

}
