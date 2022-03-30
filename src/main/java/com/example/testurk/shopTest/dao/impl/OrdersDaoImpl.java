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
        String query = "SELECT SUM(orders.total_price) as total_price," +
                "order_.order_number as order_number, order_.user_id as user_id " +
                "FROM orders " +
                "INNER JOIN order_ " +
                "ON orders.order_id = order_.id " +
                "GROUP BY orders.order_id " +
                "HAVING COUNT(goods_id) >= 2";
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
        String query = "SELECT order_.order_number as order_number " +
                "FROM orders " +
                "JOIN order_ " +
                "ON order_.id = orders.order_id " +
                "GROUP BY orders.order_id " +
                "HAVING sum(orders.quantity) BETWEEN 5 AND 10";
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
                orders.add(setAll(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of orders "
                    + "from orders table. ", e);
        }
    }

    @Override
    public double getAverageTotalPrice(String email) {
        String query = "SELECT avg(orders.total_price) as average_price " +
                "FROM orders " +
                "JOIN order_ " +
                "ON order_.id = orders.order_id " +
                "join users " +
                "on users.id = order_.user_id " +
                "WHERE users.email = ? ";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAverageStatement
                     = connection.prepareStatement(query)) {
            getAverageStatement.setString(1, email);
            Double average = 0.00;
            resultSet = getAverageStatement.executeQuery();
            while (resultSet.next()) {
                average = resultSet.getDouble("average_price");
            }
            return average;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get average sum by email : " + email, e);
        }
    }

    @Override
    public double getNeededQuantity(String name) {
        String query = "SELECT orders.quantity as quantity " +
                "FROM orders " +
                "join goods " +
                "on orders.goods_id = goods.id " +
                "WHERE goods.name = ? ";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getNeededStatement
                     = connection.prepareStatement(query)) {
            getNeededStatement.setString(1, name);
            Double needed = 0.00;
            resultSet = getNeededStatement.executeQuery();
            while (resultSet.next()) {
                needed = resultSet.getDouble("quantity");
            }
            return needed;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get needed quantity by name : " + name, e);
        }
    }

    private Orders setOrders(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getObject("user_id", Long.class);
        User user = new User();
        user.setId(userId);
        int orderNumber = resultSet.getInt("order_number");
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setUserId(user);
        int totalPrice = resultSet.getInt("total_price");
        Orders orders = new Orders();
        orders.setTotalPrice(totalPrice);
        orders.setOrderId(order);
        return orders;
    }

    private Orders setAll(ResultSet resultSet) throws SQLException {
        Long goodsId = resultSet.getObject("goods_id", Long.class);
        String goodName = resultSet.getNString("goods_name");
        int goodQuantity = resultSet.getInt("goods_quantity");
        float priceForOne = resultSet.getFloat("goods_price_for_one");
        Goods goods = new Goods();
        goods.setPriceForOne(priceForOne);
        goods.setName(goodName);
        goods.setQuantity(goodQuantity);
        goods.setId(goodsId);

        Long userId = resultSet.getObject("user_id", Long.class);
        String userName = resultSet.getString("user_name");
        String userEmail = resultSet.getString("user_email");
        Boolean userStatus = resultSet.getBoolean("account_status");
        User user = new User();
        user.setEmail(userEmail);
        user.setName(userName);
        user.setAccountStatus(userStatus);
        user.setId(userId);

        Long orderId = resultSet.getObject("order_id", Long.class);
        int orderNumber = resultSet.getInt("order_number");
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setUserId(user);
        order.setId(orderId);

        Long ordersId = resultSet.getObject("id", Long.class);
        int totalPrice = resultSet.getInt("total_price");
        int quantity = resultSet.getInt("quantity");
        Orders orders = new Orders();
        orders.setOrderId(order);
        orders.setGoodsId(goods);
        orders.setQuantity(quantity);
        orders.setTotalPrice(totalPrice);
        orders.setId(ordersId);
        return orders;
    }

}
