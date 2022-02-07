package com.example.testurk.shopTest.dao;

import com.example.testurk.shopTest.model.Orders;

import java.util.List;

public interface OrdersDao {
    List<Orders> getOrderWithCountOfMoreThanTwo();

    List<Integer> getByOrdersQuantityBetween();

    Orders create(Orders orders);

    List<Orders> getAll();
}
