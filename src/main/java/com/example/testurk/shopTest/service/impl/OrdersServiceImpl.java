package com.example.testurk.shopTest.service.impl;

import com.example.testurk.shopTest.dao.GoodsDao;
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
    private GoodsDao goodsDao;

    @Override
    public List<Orders> getOrderWithCountOfMoreThanTwo() {
        return ordersDao.getOrderWithCountOfMoreThanTwo();
    }

    @Override
    public List<Integer> getByOrdersQuantityBetween() {
        return ordersDao.getByOrdersQuantityBetween();
    }

    @Override
    public Orders create(Orders orders) {
        if (goodsDao.getAvailableQuantity(orders.getGoodsId().getName()) >=
                ordersDao.getNeededQuantity(orders.getGoodsId().getName())) {
            return ordersDao.create(orders);
        }
        return new Orders();
    }

    @Override
    public List<Orders> getAll() {
        return ordersDao.getAll();
    }

    @Override
    public double getAverageTotalPrice(String email) {
        return ordersDao.getAverageTotalPrice(email);
    }

}
