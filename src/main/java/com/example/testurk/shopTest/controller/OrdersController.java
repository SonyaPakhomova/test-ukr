package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.model.Goods;
import com.example.testurk.shopTest.model.Order;
import com.example.testurk.shopTest.model.Orders;
import com.example.testurk.shopTest.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {
    private OrdersService ordersService;

    @GetMapping("/get-more-than-two")
    public List<Orders> getOrderWithCountOfMoreThanTwo() {
        return ordersService.getOrderWithCountOfMoreThanTwo();
    }

    @GetMapping("/get-by-quantity")
    public List<Integer> getByOrdersQuantityBetween() {
        return ordersService.getByOrdersQuantityBetween();
    }

    @GetMapping("/create")
    public Orders create(@RequestParam long orderId, @RequestParam long goodsId,
                         @RequestParam int quantity, @RequestParam double total, @RequestParam String name) {
        Order order = new Order();
        order.setId(orderId);

        Goods goods = new Goods();
        goods.setId(goodsId);
        goods.setName(name);

        Orders orders = new Orders();
        orders.setTotalPrice((int) total);
        orders.setQuantity(quantity);
        orders.setOrderId(order);
        orders.setGoodsId(goods);
        return ordersService.create(orders);
    }

    @GetMapping("/getAll")
    public List<Orders> getAll() {
        return ordersService.getAll();
    }

    @GetMapping("/get-average")
    public double getAverageSum(@RequestParam String email) {
        return ordersService.getAverageTotalPrice(email);
    }
}
