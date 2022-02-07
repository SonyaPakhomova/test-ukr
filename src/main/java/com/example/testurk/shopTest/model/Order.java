package com.example.testurk.shopTest.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Order {
    private Long id;
    @NonNull
    private int orderNumber;
    @NonNull
    private User userId;
}
