package com.i8ai.training.storeapi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private Date received;
    @NotBlank
    @Column(nullable = false)
    private Double cost;
    @NotBlank
    @Column(nullable = false)
    private Double amount;
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Product product;
}
