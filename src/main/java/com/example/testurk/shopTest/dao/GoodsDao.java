package com.example.testurk.shopTest.dao;

import com.example.testurk.shopTest.model.Goods;

import java.util.List;

public interface GoodsDao {
    List<Goods> getAllContains(String contains);
}
