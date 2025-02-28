package pedrohcdsouza;

import pedrohcdsouza.models.Product;

import java.sql.SQLException;

import pedrohcdsouza.controllers.ProductController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema de Gerenciamento de Inventário");
        
        Product product = new Product(1, "Notebook", 2500.00, 10);
        ProductController productController = new ProductController();
        try {
            productController.CREATE(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}