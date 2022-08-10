package com.example.AutoDetailsShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTOTP_DTO {
    private String username;
    private String secretKey;
    private int validationCode;
    private List<Integer> scratchCodes;
}
