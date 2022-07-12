package com.example.AutoDetailsShop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "details")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Detail {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="detail_name", nullable = false)
    private String detailName;

}
