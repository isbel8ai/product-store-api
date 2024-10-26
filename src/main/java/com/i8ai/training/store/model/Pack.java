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
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double receivedAmount;

    @NotNull
    @Column(nullable = false)
    private Date deliveredAt;

    @Column
    private Double soldAmount;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Lot lot;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Shop shop;

    public Double getCurrentAmount() {
        if (soldAmount == null) return receivedAmount;
        return receivedAmount - soldAmount;
    }
}
