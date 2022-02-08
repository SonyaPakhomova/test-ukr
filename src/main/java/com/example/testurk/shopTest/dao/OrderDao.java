package com.example.testurk.shopTest.dao;

import com.example.testurk.shopTest.model.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAll();

    Order create(Order order);
}
