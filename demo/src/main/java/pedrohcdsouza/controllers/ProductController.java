package pedrohcdsouza.controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pedrohcdsouza.models.Product;

public class ProductController {
    private Connection connection;
    
    public ProductController() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/stock.db");
            createTable();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS produtos ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,"
            + "price REAL NOT NULL,"
            + "quantity INTEGER NOT NULL"
            + ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    public void CREATE(Product product) throws SQLException {
        String sql = "INSERT INTO produtos (name, price, quantity) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.executeUpdate();
        }
    }

    public List<Product> READ() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
                );
                products.add(product);
            }
        }
        return products;
    }
    
    public void UPDATE(Product product) throws SQLException {
        String sql = "UPDATE produtos SET name = ?, price = ?, quantity = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setInt(4, product.getId());
            pstmt.executeUpdate();
        }
    }

    public void DELETE(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

}
