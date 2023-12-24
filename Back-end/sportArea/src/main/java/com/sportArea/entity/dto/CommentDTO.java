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
public class CommentDTO {

    private Long commentId;

    private String message;

    private Note note;

    private Float productRating;

    private UserRegistration userRegistration;

    private ProductUaDTO productDTO;
}
