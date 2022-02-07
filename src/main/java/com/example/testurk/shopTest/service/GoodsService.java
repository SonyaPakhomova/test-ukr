package com.example.testurk.shopTest.service;

import com.example.testurk.shopTest.model.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> getAllContains(String contains);

    Goods create(Goods goods);

    List<Goods> getAll();
}
