package com.example.testurk.shopTest.service.impl;

import com.example.testurk.shopTest.dao.GoodsDao;
import com.example.testurk.shopTest.model.Goods;
import com.example.testurk.shopTest.service.GoodsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private GoodsDao goodsDao;

    @Override
    public List<Goods> getAllContains(String contains) {
        return goodsDao.getAllContains(contains).stream()
                .map(this::mapping)
                .collect(Collectors.toList());
    }

    public Goods mapping(Goods goods) {
        return new Goods(goods.getName(), goods.getQuantity());
    }
}
