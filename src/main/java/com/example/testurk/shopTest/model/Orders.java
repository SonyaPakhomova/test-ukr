package com.example.testurk.shopTest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Orders {
    private Long id;
    private Order orderId;
    private Goods goodsId;
    private int quantity;
    private double totalPrice;
}
