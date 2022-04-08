package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.dao.GoodsDao;
import com.example.testurk.shopTest.model.Goods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoodsControllerTest1 {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    public void resetDb() {
        goodsDao.deleteAll();
    }

    @Test
    public void createGoods_isOk() {
        Goods goods = new Goods("TV", 15, 125);
        ResponseEntity<Goods> response = restTemplate.postForEntity("/goods/create", goods, Goods.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(Objects.requireNonNull(response.getBody()).getName(), is("TV"));
    }

    @Test
    public void createGoods_isNot_Ok() {
        Goods goods = new Goods("TV", 15, 125);
        ResponseEntity<Goods> response = restTemplate.postForEntity("/goods/create", goods, Goods.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(Objects.requireNonNull(response.getBody()).getName(), is("TV"));
    }

    @Test
    public void getAll_isOk() {
        createGoods("TV", 15, 125);
        createGoods("Iphone", 25, 1543);
        createGoods("PlayStation", 30, 564);

        ResponseEntity<List<Goods>> response = restTemplate.exchange("/goods/getAll", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Goods>>() {});

        List<Goods> goods = response.getBody();
        assertThat(goods, hasSize(3));
        assertThat(goods.get(0).getName(), is("TV"));
        assertThat(goods.get(1).getName(), is("Iphone"));
        assertThat(goods.get(2).getName(), is("PlayStation"));
    }

    @Test
    public void getByName_isOk() {
        Goods goods = createGoods("TV", 15, 125);
        String name = goods.getName();
        Goods goods1 = restTemplate.getForObject("/goods/get-by-name/{name}", Goods.class, name);
        assertThat(goods.getName(), is("TV"));
    }

    @Test
    public void getByName_isNotOk() {
        createGoods("TV", 15, 125);
        createGoods("Iphone", 25, 1543);
        createGoods("PlayStation", 30, 564);

        String name = "Xbox";
        ResponseEntity<Goods> responseEntity = restTemplate.getForEntity("/goods/get-by-name/{name}", Goods.class, name);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }


    @Test
    public void getAllContains_isOk() {
        createGoods("TV", 15, 125);
        createGoods("Iphone", 25, 1543);
        createGoods("PlayStation", 30, 564);
        String contains = "TV";
        ResponseEntity<Goods> responseEntity = restTemplate.getForEntity("/goods/contains/{contains}", Goods.class, contains);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void getAllContains_isNot_Ok() {
        createGoods("TV", 15, 125);
        createGoods("Iphone", 25, 1543);
        createGoods("PlayStation", 30, 564);
        String contains = "Xbox";
        ResponseEntity<Goods> responseEntity = restTemplate.getForEntity("/goods/contains/{contains}", Goods.class, contains);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    private Goods createGoods(String name, int quantity, float priceForOne) {
        Goods newGoods = new Goods(name, quantity, priceForOne);
        return goodsDao.create(newGoods);
    }
}