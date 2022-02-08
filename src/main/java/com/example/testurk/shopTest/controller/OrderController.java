package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.model.Order;
import com.example.testurk.shopTest.model.User;
import com.example.testurk.shopTest.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @GetMapping("/getAll")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/create")
    public Order create(@RequestParam long userId, @RequestParam int orderNumber) {
        User user = new User();
        user.setId(userId);

        Order order = new Order();
        order.setUserId(user);
        order.setOrderNumber(orderNumber);
       return orderService.create(order);
    }
}
