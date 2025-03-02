package pedrohcdsouza.softinventorymanager.controllers;

import pedrohcdsouza.softinventorymanager.models.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController{

    public void connect() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:resource::pedrohcdsouza/softinventorymanager/database/stock.db");
        Statement statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS product (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "quantity INTEGER NOT NULL)");
    }

    public void CREATE(Product product) {

    }


}