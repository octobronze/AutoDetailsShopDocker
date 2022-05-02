package com.example.AutoDetailsShop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="car_models")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarModel {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="car_model_name", nullable = false)
    private String carModelName;

}
