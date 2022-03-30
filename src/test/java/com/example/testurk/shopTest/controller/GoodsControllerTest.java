package com.example.testurk.shopTest.controller;

import com.example.testurk.shopTest.config.AppConfig;
import com.example.testurk.shopTest.model.Goods;
import com.example.testurk.shopTest.service.GoodsService;
import com.mysql.cj.x.protobuf.Mysqlx;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = AppConfig.class)
class GoodsControllerTest {
    @MockBean
    private GoodsService goodsService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void shouldShowAllGoods() throws Exception {
        List<Goods> mockGoods = List.of(
                new Goods(21L, "TV", 5, 255F),
                new Goods(22L, "IPhone X", 55, 1355F),
                new Goods(23L, "PlayStation", 45, 635F)
        );
        Mockito.when(goodsService.getAll()).thenReturn(mockGoods);

        RestAssuredMockMvc.when()
                .get("/goods/getAll")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3))

                .body("[0].id", Matchers.equalTo(21))
                .body("[0].name", Matchers.equalTo("TV"))
                .body("[0].quantity", Matchers.equalTo(5))
                .body("[0].priceForOne", Matchers.equalTo(255.0F))

                .body("[1].id", Matchers.equalTo(22))
                .body("[1].name", Matchers.equalTo("IPhone X"))
                .body("[1].quantity", Matchers.equalTo(55))
                .body("[1].priceForOne", Matchers.equalTo(1355.0F))

                .body("[2].id", Matchers.equalTo(23))
                .body("[2].name", Matchers.equalTo("PlayStation"))
                .body("[2].quantity", Matchers.equalTo(45))
                .body("[2].priceForOne", Matchers.equalTo(635.0F));
    }

    @Test
    public void shouldShowAllGoods_NOT_OK() throws Exception {
        List<Goods> mockGoods = List.of(
                new Goods(21L, "TV", 5, 255F),
                new Goods(22L, "IPhone X", 55, 1355F),
                new Goods(23L, "PlayStation", 45, 635F)
        );
        Mockito.when(goodsService.getAll()).thenReturn(mockGoods);

        RestAssuredMockMvc.when()
                .get("/goods/getAll")
                .then()
                .statusCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .body("fieldErrors.size()", Matchers.equalTo(2))

                .body("[0].id", Matchers.equalTo(21))
                .body("[0].name", Matchers.equalTo("TV"))
                .body("[0].quantity", Matchers.equalTo(5))
                .body("[0].priceForOne", Matchers.equalTo(255.0F))

                .body("[2].name", Matchers.equalTo("PlayStation"))
                .body("[2].quantity", Matchers.equalTo(45))
                .body("[2].priceForOne", Matchers.equalTo(635.0F));
    }

    @Test
    public void shouldShowAllGoodsContains() throws Exception {
        String contains = "Phone";
        List<Goods> mockGoods = List.of(
                new Goods(22L, "IPhone X", 55, 1355F)
        );
        Mockito.when(goodsService.getAllContains(contains)).thenReturn(mockGoods);

        RestAssuredMockMvc.given()
                .queryParam("contains", contains)
                .when()
                .get("/goods/contains")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(22))
                .body("[0].name", Matchers.equalTo("IPhone X"))
                .body("[0].quantity", Matchers.equalTo(55))
                .body("[0].priceForOne", Matchers.equalTo(1355.0F));
    }

    @Test
    public void shouldShowAllGoodsContains_NOT_Ok() throws Exception {
        String contains = "45";
        List<Goods> empty = new ArrayList<>();
        Mockito.when(goodsService.getAllContains(contains)).thenReturn(empty);

        RestAssuredMockMvc.given()
                .queryParam("contains", contains)
                .when()
                .get("/goods/contains")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldShowGoodsByName() throws Exception {
        String name = "IPhone X";
        Goods iphone = new Goods(22L, "IPhone X", 55, 1355F);
        Mockito.when(goodsService.getByName(name)).thenReturn(iphone);

        RestAssuredMockMvc.given()
                .queryParam("name", name)
                .when()
                .get("/goods/get-by-name")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(22))
                .body("name", Matchers.equalTo("IPhone X"))
                .body("quantity", Matchers.equalTo(55))
                .body("priceForOne", Matchers.equalTo(1355.0F));
    }

    @Test
    public void shouldShowGoodsByName_NOT_OK() throws Exception {
        String name = "IPhone 5";
        Goods iphone = new Goods();
        Mockito.when(goodsService.getByName(name)).thenReturn(iphone);

        RestAssuredMockMvc.given()
                .queryParam("name", name)
                .when()
                .get("/goods/get-by-name")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldCreateGoods() throws Exception {
        Goods goods = new Goods ( "TV", 5, 255F);
        Mockito.when(goodsService.create(goods))
                .thenReturn(new Goods ( 21L, "TV", 5, 255F));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new Goods (21L, goods.getName(), goods.getQuantity(), goods.getPriceForOne()))
                .queryParam("name", goods.getName())
                .queryParam("quantity", goods.getQuantity())
                .queryParam("priceForOne", goods.getPriceForOne())
                .when()
                .post("/goods/create")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(21))
                .body("name", Matchers.equalTo("TV"))
                .body("quantity", Matchers.equalTo(5))
                .body("priceForOne", Matchers.equalTo(255.0F));
    }
}
