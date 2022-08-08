package com.example.AutoDetailsShop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "offers")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Offer {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    private Detail detail;

    @ManyToOne
    @JoinColumn(name = "car_brand_id")
    private CarBrand carBrand;

    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @Column(name = "price")
    private BigDecimal price;
}
