package com.example.testurk.shopTest.service.impl;

import com.example.testurk.shopTest.dao.OrdersDao;
import com.example.testurk.shopTest.model.Order;
import com.example.testurk.shopTest.model.Orders;
import com.example.testurk.shopTest.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private OrdersDao ordersDao;

    @Override
    public List<Orders> getOrderWithCountOfMoreThanTwo() {
        return ordersDao.getOrderWithCountOfMoreThanTwo();
    }

    @Override
    public List<Integer> getByOrdersQuantityBetween() {
        return ordersDao.getByOrdersQuantityBetween();
    }

    @Override
    public Orders create(Orders order) {
        return ordersDao.create(order);
    }

    @Override
    public List<Orders> getAll() {
        return ordersDao.getAll();
    }

}
