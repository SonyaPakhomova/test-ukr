package com.example.testurk.shopTest.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Orders {
    private Long id;
    @NonNull
    private List<Order> orderId;
    @NonNull
    private List<Goods> goodsId;
    @NonNull
    private int quantity;
    @NonNull
    private double totalPrice;
}
