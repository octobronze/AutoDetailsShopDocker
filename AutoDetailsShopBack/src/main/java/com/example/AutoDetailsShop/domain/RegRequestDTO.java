package com.example.AutoDetailsShop.domain;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
}
