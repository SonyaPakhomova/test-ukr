package com.example.testurk.shopTest.dao;

import com.example.testurk.shopTest.model.Orders;

import java.util.List;

public interface OrderDetailsDao {
    List<Orders> getOrderWithCountOfMoreThanTwo();

    List<Orders> getByOrdersQuantityBetween(int min, int max);

}
