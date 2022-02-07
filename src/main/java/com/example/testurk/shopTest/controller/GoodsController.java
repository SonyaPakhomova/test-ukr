package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.model.Goods;
import com.example.testurk.shopTest.service.GoodsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsController {
    public GoodsService goodsService;

    @GetMapping("/contains")
    public List<Goods> getAllContains(@RequestParam String contains) {
        return goodsService.getAllContains(contains);
    }

    @GetMapping("/getAll")
    public List<Goods> getAll() {
        return goodsService.getAll();
    }

    @GetMapping("/create")
    public Goods create(@RequestParam Goods goods) {
      return goodsService.create(goods);
    }
}
