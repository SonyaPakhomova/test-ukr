package com.example.testurk.shopTest.dao;

import com.example.testurk.shopTest.model.Goods;

import java.util.List;
import java.util.Optional;

public interface GoodsDao {
    List<Goods> getAllContains(String contains);

    Goods create(Goods goods);

    List<Goods> getAll();

    double getAvailableQuantity(String name);

    Optional<Goods> getByName(String name);
}
