package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.dao.OrderDetailsDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import com.example.testurk.shopTest.model.Goods;
import com.example.testurk.shopTest.model.Orders;
import com.example.testurk.shopTest.util.ConnectionUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public List<Orders> getOrderWithCountOfMoreThanTwo() {
        String selectQuery = "SELECT * FROM " +
                " "
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllOrdersStatement
                     = connection.prepareStatement(selectQuery)) {
            List<Orders> orders = new ArrayList<>();
            ResultSet resultSet = getAllOrdersStatement.executeQuery();
            while (resultSet.next()) {
               orders.add(setOrders(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders with goods quantity more than goodstwo", e);
        }
    }

    @Override
    public List<Orders> getByOrdersQuantityBetween(int min, int max) {
        return null;
    }

    private Orders setOrders(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        Double totalPrice = resultSet.getDouble("total_price");
        Long goodsId = resultSet.getObject("goods_id", Long.class);
        int quantity = resultSet.getInt("quantity");
        Long orderId = resultSet.getObject("order_id",Long.class);
        Orders orders = new Orders(totalPrice, goodsId, quantity, orderId);
        goods.setId(id);
        return goods;
    }
}
