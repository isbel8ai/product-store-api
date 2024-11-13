package com.i8ai.training.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double acquiredAmount;

    @NotNull
    @Column(nullable = false)
    private Double costPerUnit;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime acquiredAt;

    @Column
    private Double deliveredAmount;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Product product;

    public Double getCurrentAmount() {
        if (deliveredAmount == null) return acquiredAmount;
        return acquiredAmount - deliveredAmount;
    }
}
