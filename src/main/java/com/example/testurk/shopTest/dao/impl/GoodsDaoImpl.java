package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.config.AppConfig;
import com.example.testurk.shopTest.dao.GoodsDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import com.example.testurk.shopTest.model.Goods;
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
public class GoodsDaoImpl implements GoodsDao {
    private AppConfig appConfig;

    @Override
    public List<Goods> getAllContains(String contains) {
        String query = "SELECT * FROM goods WHERE name LIKE ?";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAllGoodsStatement
                     = connection.prepareStatement(query)) {
            getAllGoodsStatement.setString(1, "'%" + contains + "%'");
            List<Goods> goods = new ArrayList<>();
            resultSet = getAllGoodsStatement.executeQuery();
            while (resultSet.next()) {
                goods.add(setGoods(resultSet));
            }
            return goods;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get goods which contains : " + contains, e);
        }
    }

    @Override
    public Goods create(Goods good) {
        String query = "INSERT INTO goods (name, quantity, price_for_one)"
                + "VALUES (?, ?, ?)";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement createGoodsStatement =
                     connection.prepareStatement(
                             query, Statement.RETURN_GENERATED_KEYS)) {
            createGoodsStatement.setString(1, good.getName());
            createGoodsStatement.setInt(2, good.getQuantity());
            createGoodsStatement.setDouble(3, good.getPriceForOne());
            createGoodsStatement.executeUpdate();
            ResultSet resultSet = createGoodsStatement.getGeneratedKeys();
            if (resultSet.next()) {
                good.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create good " + good, e);
        }
        return good;
    }

    @Override
    public List<Goods> getAll() {
        String query = "SELECT * FROM goods";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAllGoodsStatement
                     = connection.prepareStatement(query)) {
            List<Goods> goods = new ArrayList<>();
            resultSet = getAllGoodsStatement.executeQuery();
            while (resultSet.next()) {
                goods.add(setGoods(resultSet));
            }
            return goods;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of goods "
                    + "from goods table. ", e);
        }
    }

    private Goods setGoods(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        int quantity = resultSet.getInt("quantity");
        double priceForOne = resultSet.getDouble("price_for_one");
        Goods goods = new Goods(name, quantity, priceForOne);
        goods.setId(id);
        return goods;
    }
}
