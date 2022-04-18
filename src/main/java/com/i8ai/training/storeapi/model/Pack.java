package com.i8ai.training.storeapi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Date delivered;

    @NotNull
    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Lot lot;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Shop shop;
}
