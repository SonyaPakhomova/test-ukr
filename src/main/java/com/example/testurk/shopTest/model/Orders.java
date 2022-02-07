package com.example.testurk.shopTest.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Orders {
    private Long id;
    @NonNull
    private Order orderId;
    @NonNull
    private Goods goodsId;
    @NonNull
    private int quantity;
    @NonNull
    private int totalPrice;
}
