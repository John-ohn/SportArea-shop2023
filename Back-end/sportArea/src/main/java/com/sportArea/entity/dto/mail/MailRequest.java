package com.sportArea.entity.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {
    String subject;
    String messageSt;
    String toEmail;

}
