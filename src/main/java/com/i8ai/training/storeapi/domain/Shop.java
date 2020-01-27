package com.i8ai.training.storeapi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Shop {
    @Id
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    private String Description;
}
