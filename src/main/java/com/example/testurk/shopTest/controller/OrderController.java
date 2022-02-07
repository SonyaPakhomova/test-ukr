package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.model.Order;
import com.example.testurk.shopTest.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
