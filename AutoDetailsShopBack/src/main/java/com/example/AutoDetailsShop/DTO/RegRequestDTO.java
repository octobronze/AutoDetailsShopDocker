package com.example.AutoDetailsShop.DTO;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class RegRequestDTO {

    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 6, max = 20)
    private String password;

    @Size(min = 1, max = 40)
    private String firstName;

    @Size(min = 1, max = 40)
    private String lastName;

    @Email
    private String email;

    @Size(min = 4, max = 6)
    private String sex;
}
