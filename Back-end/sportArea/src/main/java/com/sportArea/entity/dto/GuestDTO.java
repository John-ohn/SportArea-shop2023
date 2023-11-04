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
public class GuestDTO {

    private Long guestId;

    private Role role;

}
