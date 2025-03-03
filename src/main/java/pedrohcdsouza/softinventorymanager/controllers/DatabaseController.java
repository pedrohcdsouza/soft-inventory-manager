package pedrohcdsouza.softinventorymanager.controllers;

import pedrohcdsouza.softinventorymanager.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController{

    private final String PATH = "src/main/resources/pedrohcdsouza/softinventorymanager/database/stock.db";

    public void connect() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + PATH);
        Statement statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS product (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "quantity INTEGER NOT NULL)");
    }

    public void CREATE(Product product) throws SQLException{

        String sql = "INSERT INTO product (name, price, quantity) VALUES (?, ?, ?)";
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + PATH);
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, product.getName());
        statement.setDouble(2, product.getPrice());
        statement.setInt(3, product.getQuantity());

        statement.executeUpdate();
        statement.close();

    }

    public List<Product> READ() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";

        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + PATH);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");

            products.add(new Product(id, name, price, quantity));
        }

        resultSet.close();
        statement.close();
        connection.close();

        return products;
    }

    public void UPDATE(Integer id, Product updatedProduct) throws SQLException {
        String sql = "UPDATE product SET name = ?, price = ?, quantity = ? WHERE id = ?";

        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + PATH);
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, updatedProduct.getName());
        statement.setDouble(2, updatedProduct.getPrice());
        statement.setInt(3, updatedProduct.getQuantity());
        statement.setInt(4, id);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public void DELETE(Integer id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";

        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + PATH);
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

}