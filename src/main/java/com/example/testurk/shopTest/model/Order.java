package com.example.testurk.shopTest.model;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private int orderNumber;
    private User userId;
}
