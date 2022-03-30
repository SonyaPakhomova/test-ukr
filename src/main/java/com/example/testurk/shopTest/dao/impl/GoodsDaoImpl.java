package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.config.AppConfig;
import com.example.testurk.shopTest.dao.GoodsDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import com.example.testurk.shopTest.model.Goods;
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
public class GoodsDaoImpl implements GoodsDao {
    private AppConfig appConfig;

    @Override
    public List<Goods> getAllContains(String contains) {
        String query = "SELECT name, price_for_one FROM goods WHERE name LIKE (?) ";
        ResultSet resultSet;
        String like = "%" + contains + "%";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAllGoodsStatement
                     = connection.prepareStatement(query)) {
            getAllGoodsStatement.setString(1, like);
            List<Goods> goodsList = new ArrayList<>();
            resultSet = getAllGoodsStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Float priceForOne = resultSet.getFloat("price_for_one");
                Goods goods = new Goods();
                goods.setName(name);
                goods.setPriceForOne(priceForOne);
                goodsList.add(goods);
            }
            return goodsList;
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

    @Override
    public double getAvailableQuantity(String name) {
        String query = "SELECT goods.quantity as quantity " +
                "FROM goods " +
                "WHERE goods.name = ? ";
        ResultSet resultSet;
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getAvailableStatement
                     = connection.prepareStatement(query)) {
            getAvailableStatement.setString(1, name);
            Float available = 0f;
            resultSet = getAvailableStatement.executeQuery();
            while (resultSet.next()) {
                available = resultSet.getFloat("quantity");
            }
            return available;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get available goods quantity by good name: " + name, e);
        }
    }

    @Override
    public Optional<Goods> getByName(String name) {
        String query = "SELECT * FROM goods WHERE name = ?";
        try (Connection connection = appConfig.dataSource().getConnection();
             PreparedStatement getGoodsStatement = connection.prepareStatement(query)) {
            getGoodsStatement.setString(1, name);
            ResultSet resultSet = getGoodsStatement.executeQuery();
            Goods goods = null;
            if (resultSet.next()) {
                goods = setGoods(resultSet);
            }
            return Optional.ofNullable(goods);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get good by name " + name, e);
        }
    }


    private Goods setGoods(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        int quantity = resultSet.getInt("quantity");
        float priceForOne = resultSet.getFloat("price_for_one");
        Goods goods = new Goods();
        goods.setName(name);
        goods.setQuantity(quantity);
        goods.setPriceForOne(priceForOne);
        goods.setId(id);
        return goods;
    }
}
