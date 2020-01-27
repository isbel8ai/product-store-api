package com.i8ai.training.storeapi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor

public class Product {
    @Id
    private Long id;
    @NonNull
    private String code;
    @NonNull
    private String name;
    @NonNull
    private String measure;
    private String description;
}
