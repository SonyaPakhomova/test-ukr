package com.example.testurk.shopTest.service.impl;

import com.example.testurk.shopTest.dao.GoodsDao;
import com.example.testurk.shopTest.dao.OrderDao;
import com.example.testurk.shopTest.model.Order;
import com.example.testurk.shopTest.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order create(Order order) {

        return orderDao.create(order);
    }
}
