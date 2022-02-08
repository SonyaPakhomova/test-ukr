package com.example.testurk.shopTest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Goods {
    private Long id;
    private String name;
    private int quantity;
    private double priceForOne;
}
