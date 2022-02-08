package com.example.testurk.shopTest.service;

import com.example.testurk.shopTest.model.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> getOrderWithCountOfMoreThanTwo();

    List<Integer> getByOrdersQuantityBetween();

    Orders create(Orders order);

    List<Orders> getAll();

    double getAverageTotalPrice(String email);
}
