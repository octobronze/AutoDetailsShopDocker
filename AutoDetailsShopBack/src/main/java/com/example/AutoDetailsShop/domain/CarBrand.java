package com.example.AutoDetailsShop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="car_brands")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarBrand {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="car_brand_name", nullable = false)
    private String carBrandName;

}
