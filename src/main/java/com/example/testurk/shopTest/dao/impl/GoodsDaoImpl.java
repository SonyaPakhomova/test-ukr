package com.example.testurk.shopTest.dao.impl;

import com.example.testurk.shopTest.dao.GoodsDao;
import com.example.testurk.shopTest.exception.DataProcessingException;
import com.example.testurk.shopTest.model.Goods;
import com.example.testurk.shopTest.util.ConnectionUtil;
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
public class GoodsDaoImpl implements GoodsDao {

    @Override
    public List<Goods> getAllContains(String contains) {
        String selectQuery = "SELECT * FROM goods WHERE name Like %contains%";
        ResultSet resultSet;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllGoodsStatement
                     = connection.prepareStatement(selectQuery)) {
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
/*
    @Override
    public Goods create(Goods good) {
        String insertQuery = "INSERT INTO goods (name, quantity, price_for_one)"
                + "VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createGoodsStatement =
                     connection.prepareStatement(
                             insertQuery, Statement.RETURN_GENERATED_KEYS)) {
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
    public Optional<Goods> get(Long id) {
        String selectQuery = "SELECT * FROM goods WHERE id = ?";
        Goods goods = null;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getGoodsStatement =
                     connection.prepareStatement(selectQuery)) {
            getGoodsStatement.setLong(1, id);
            ResultSet resultSet = getGoodsStatement.executeQuery();
            if (resultSet.next()) {
                goods = setGoods(resultSet);
            }
            return Optional.ofNullable(goods);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get good by id: " + id, e);
        }
    }

    @Override
    public List<Goods> getAll() {
        String query = "SELECT * FROM goods";
        ResultSet resultSet;
        try (Connection connection = ConnectionUtil.getConnection();
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
    public Goods update(Goods good) {
        String query = "UPDATE goods SET name = ?, quantity = ?, price_for_one = ?"
                + " WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateGoodsStatement
                     = connection.prepareStatement(query)) {
            updateGoodsStatement.setLong(1, good.getId());
            updateGoodsStatement.setString(2, good.getName());
            updateGoodsStatement.setInt(3, good.getQuantity());
            updateGoodsStatement.setDouble(4, good.getPriceForOne());
            updateGoodsStatement.executeUpdate();
            return good;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update a good "
                    + good, e);
        }
    }
*/
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
