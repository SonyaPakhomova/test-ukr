package com.example.testurk.shopTest.service.impl;

import com.example.testurk.shopTest.dao.GoodsDao;
import com.example.testurk.shopTest.model.Goods;
import com.example.testurk.shopTest.service.GoodsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private GoodsDao goodsDao;

    @Override
    public List<Goods> getAllContains(String contains) {
        return goodsDao.getAllContains(contains);
    }

    @Override
    public Goods create(Goods goods) {
        return goodsDao.create(goods);
    }

    @Override
    public List<Goods> getAll() {
        return goodsDao.getAll();
    }

    @Override
    public double getAvailableQuantity(String name) {
        return goodsDao.getAvailableQuantity(name);
    }

    @Override
    public Goods getByName(String name) {
        return goodsDao.getByName(name)
                .orElseThrow(() -> new RuntimeException("Can't get good by name " + name));
    }

}
