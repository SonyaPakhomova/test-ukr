package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.config.AppConfig;
import com.example.testurk.shopTest.dao.OrdersDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import com.example.testurk.shopTest.model.Goods;
import com.example.testurk.shopTest.model.Order;
import com.example.testurk.shopTest.model.Orders;
import com.example.testurk.shopTest.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class OrdersDaoImpl implements OrdersDao {
    private AppConfig appConfig;

    @Override
    public List<Orders> getOrderWithCountOfMoreThanTwo() {
        String query = "SELECT orders.id as id, orders.total_price as total_price, orders.quantity as quantity, " +
                " o.id as order_id," +
                " o.order_number as order_number," +
                " g.id as goods_id," +
                " g.name as goods_name," +
                " g.quantity as goods_quantity," +
                " g.price_for_one as goods_price_for_one," +
                " u.id as user_id," +
                " u.name as user_name," +
                " u.email as user_email," +
                " u.account_status as account_status" +
                " FROM orders " +
                "JOIN order_ o on o.id = orders.order_id " +
                "JOIN users u on u.id = o.user_id " +
                "JOIN goods g on u.name = g.name " +
                "GROUP BY goods_id " +
                "HAVING COUNT(goods_id) >= 2 ";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAllOrdersStatement
                     = connection.prepareStatement(query)) {
            List<Orders> orders = new ArrayList<>();
            ResultSet resultSet = getAllOrdersStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(setOrders(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders with goods quantity more than two", e);
        }
    }

    @Override
    public List<Integer> getByOrdersQuantityBetween() {
        String query = "SELECT o.order_number " +
                "FROM order_ o " +
                "JOIN orders o2 on o.id = o2.order_id " +
                "WHERE o2.quantity BETWEEN 5 AND 10";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getByOrderStatement
                     = connection.prepareStatement(query)) {
            List<Integer> orders = new ArrayList<>();
            resultSet = getByOrderStatement.executeQuery();
            while (resultSet.next()) {
                Integer result = resultSet.getInt("order_number");
                orders.add(result);
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders number with total quantity between 5 and 10 ", e);
        }
    }

    @Override
    public Orders create(Orders orders) {
        String query = "INSERT INTO orders (order_id, goods_id, quantity, total_price)"
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement createOrdersStatement =
                     connection.prepareStatement(
                             query, Statement.RETURN_GENERATED_KEYS)) {
            createOrdersStatement.setObject(1, orders.getOrderId().getId());
            createOrdersStatement.setObject(2, orders.getGoodsId().getId());
            createOrdersStatement.setObject(3, orders.getQuantity());
            createOrdersStatement.setObject(4, orders.getTotalPrice());
            createOrdersStatement.executeUpdate();
            ResultSet resultSet = createOrdersStatement.getGeneratedKeys();
            if (resultSet.next()) {
                orders.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create orders " + orders, e);
        }
        return orders;
    }

    @Override
    public List<Orders> getAll() {
        String query = "SELECT orders.id as id, orders.total_price as total_price, orders.quantity as quantity, " +
                " o.id as order_id," +
                " o.order_number as order_number," +
                " g.id as goods_id," +
                " g.name as goods_name," +
                " g.quantity as goods_quantity," +
                " g.price_for_one as goods_price_for_one," +
                " u.id as user_id," +
                " u.name as user_name," +
                " u.email as user_email," +
                " u.account_status as account_status" +
                " FROM orders " +
                " JOIN goods g on g.id = orders.goods_id " +
                " JOIN order_ o on o.id = orders.order_id " +
                " JOIN users u on u.id = o.user_id";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAllOrdersStatement
                     = connection.prepareStatement(query)) {
            List<Orders> orders = new ArrayList<>();
            resultSet = getAllOrdersStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(setOrders(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of orders "
                    + "from orders table. ", e);
        }
    }

    private Orders setOrders(ResultSet resultSet) throws SQLException {
        Long goodsId = resultSet.getObject("goods_id", Long.class);
        String goodName = resultSet.getNString("goods_name");
        int goodQuantity = resultSet.getInt("goods_quantity");
        double priceForOne = resultSet.getDouble("goods_price_for_one");
        Goods goods = new Goods(goodName, goodQuantity, priceForOne);
        goods.setId(goodsId);

        Long userId = resultSet.getObject("user_id", Long.class);
        String userName = resultSet.getString("user_name");
        String userEmail = resultSet.getString("user_email");
        Boolean userStatus = resultSet.getBoolean("account_status");
        User user = new User(userEmail, userName, userStatus);
        user.setId(userId);

        Long orderId = resultSet.getObject("order_id", Long.class);
        int orderNumber = resultSet.getInt("order_number");
        Order order = new Order(orderNumber, user);
        order.setId(orderId);

        Long ordersId = resultSet.getObject("id", Long.class);
        int totalPrice = resultSet.getInt("total_price");
        int quantity = resultSet.getInt("quantity");
        Orders orders = new Orders(order, goods, quantity, totalPrice);
        orders.setId(orderId);
        return orders;
    }
}
