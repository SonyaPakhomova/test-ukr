package com.example.testurk.shopTest.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Goods {
    private Long id;
    private String name;
    private int quantity;
    private double priceForOne;

    public Goods(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Goods(@NonNull String name, @NonNull int quantity, double priceForOne) {
        this.name = name;
        this.quantity = quantity;
        this.priceForOne = priceForOne;
    }
}
