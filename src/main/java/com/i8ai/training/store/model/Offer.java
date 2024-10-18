package com.i8ai.training.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double price;


    @NotNull
    @Column(nullable = false)
    private Date createdAt;

    @Column
    private Double discount;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Pack pack;
}
