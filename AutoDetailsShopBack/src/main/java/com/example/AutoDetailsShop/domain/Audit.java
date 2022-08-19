package com.example.AutoDetailsShop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "audit")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Audit {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request")
    private String request;

    @Column(name = "link")
    private String link;

    @Column(name = "interaction_time")
    private Date interactionTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
