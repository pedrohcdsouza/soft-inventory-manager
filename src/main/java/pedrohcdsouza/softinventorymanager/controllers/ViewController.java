package pedrohcdsouza.softinventorymanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import pedrohcdsouza.softinventorymanager.models.Product;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    private final DatabaseController databaseController = new DatabaseController();
    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            databaseController.connect();
            configureTableColumns();
            loadProducts();
        } catch (SQLException e) {
            showError("Erro ao conectar ao banco de dados", e.getMessage());
        }
    }

    private void configureTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void loadProducts() {
        try {
            productList.clear();
            productList.addAll(databaseController.READ());
            productTable.setItems(productList);
        } catch (SQLException e) {
            showError("Erro ao carregar produtos", e.getMessage());
        }
    }

    @FXML
    private void handleAddProduct() {
        Dialog<Product> dialog = createProductDialog("Adicionar Produto", null);
        dialog.showAndWait().ifPresent(product -> {
            try {
                databaseController.CREATE(product);
                loadProducts();
            } catch (SQLException e) {
                showError("Erro ao adicionar produto", e.getMessage());
            }
        });
    }

    @FXML
    private void handleEditProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("Selecione um produto para editar", Alert.AlertType.WARNING);
            return;
        }

        Dialog<Product> dialog = createProductDialog("Editar Produto", selectedProduct);
        dialog.showAndWait().ifPresent(product -> {
            try {
                databaseController.UPDATE(selectedProduct.getId(), product);
                loadProducts();
            } catch (SQLException e) {
                showError("Erro ao atualizar produto", e.getMessage());
            }
        });
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("Selecione um produto para remover", Alert.AlertType.WARNING);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Deseja realmente excluir o produto?");
        alert.setContentText("Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                databaseController.DELETE(selectedProduct.getId());
                loadProducts();
            } catch (SQLException e) {
                showError("Erro ao excluir produto", e.getMessage());
            }
        }
    }

    private Dialog<Product> createProductDialog(String title, Product product) {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle(title);

        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField quantityField = new TextField();

        if (product != null) {
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getQuantity()));
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Preço:"), 0, 1);
        grid.add(priceField, 1, 1);
        grid.add(new Label("Quantidade:"), 0, 2);
        grid.add(quantityField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    return new Product(null, name, price, quantity);
                } catch (NumberFormatException e) {
                    showError("Erro de formato", "Por favor, insira valores numéricos válidos para preço e quantidade.");
                    return null;
                }
            }
            return null;
        });

        return dialog;
    }

    private void showError(String header, String content) {
        showAlert(header, Alert.AlertType.ERROR, content);
    }

    private void showAlert(String header, Alert.AlertType type) {
        showAlert(header, type, null);
    }

    private void showAlert(String header, Alert.AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setTitle("Aviso");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}