package com.example.testurk.shopTest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private Long id;
    private String name;
    private int quantity;
    private float priceForOne;

    public Goods(String name, int quantity, float priceForOne) {
        this.name = name;
        this.quantity = quantity;
        this.priceForOne = priceForOne;
    }
}
