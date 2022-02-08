package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.config.AppConfig;
import com.example.testurk.shopTest.dao.OrderDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import com.example.testurk.shopTest.model.Order;
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
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private AppConfig appConfig;

    @Override
    public List<Order> getAll() {
        String query = "SELECT  o.id as id, o.order_number as order_number, " +
                "name, " +
                "email, " +
                "account_status, " +
                "u.account_status as account_status, " +
                "u.id as user_id," +
                "u.name as user_name, " +
                "u.email as user_email " +
                "FROM order_ o " +
                "JOIN users u on o.user_id = u.id";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAllOrderStatement
                     = connection.prepareStatement(query)) {
            List<Order> orders = new ArrayList<>();
            resultSet = getAllOrderStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(setOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of orders "
                    + "from order table. ", e);
        }
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO order_ (order_number, user_id)"
                + "VALUES (?, ?)";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement createOrderStatement =
                     connection.prepareStatement(
                             query, Statement.RETURN_GENERATED_KEYS)) {
            createOrderStatement.setInt(1, order.getOrderNumber());
            createOrderStatement.setObject(2, order.getUserId().getId());
            createOrderStatement.executeUpdate();
            ResultSet resultSet = createOrderStatement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order " + order, e);
        }
        return order;
    }

    private Order setOrder(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getObject("user_id", Long.class);
        String userName = resultSet.getString("user_name");
        String userEmail = resultSet.getString("user_email");
        Boolean userAccountStatus = resultSet.getBoolean("account_status");
        User user = new User();
        user.setName(userName);
        user.setEmail(userEmail);
        user.setAccountStatus(userAccountStatus);
        user.setId(userId);

        Long id = resultSet.getObject("id", Long.class);
        int orderNumber = resultSet.getInt("order_number");
        Order order = new Order();
        order.setId(id);
        order.setOrderNumber(orderNumber);
        order.setUserId(user);
        return order;
    }
}
