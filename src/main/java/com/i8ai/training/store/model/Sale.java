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
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Date registered;

    @NotNull
    @Column(nullable = false)
    private Double amount;

    @NotNull
    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Pack pack;
}
