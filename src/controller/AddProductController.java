/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

//import com.sun.xml.internal.bind.v2.model.core.ID;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Product;
import model.Part;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AlertMessages;
import model.Outsourced;
import org.omg.CORBA.DynAnyPackage.Invalid;
import static seanlangiewiczinventorysystem.SeanLangiewiczInventorySystem.generateProductID;

/**
 * FXML Controller class
 *S
 * @author Sean
 */
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField productIDTxtFLD;

    @FXML
    private TextField productNameTxtFLD;

    @FXML
    private TextField productInvTxtFLD;

    @FXML
    private TextField productPriceTxtFLD;

    @FXML
    private TextField productMaxTxtFLD;

    @FXML
    private TextField productMinTxtFLD;

    @FXML
    private TableView<Part> partTblView;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInv;

    @FXML
    private TableColumn<Part, Double> partCost;

    @FXML
    private TableColumn<Part, Integer> addedPartID;

    @FXML
    private TableColumn<Part, String> addedPartName;

    @FXML
    private TableColumn<Part, Integer> addedPartStock;

    @FXML
    private TableColumn<Part, Double> addedPartPrice;

    @FXML
    private TableView<Part> partListTBL;

    @FXML
    private TextField partSearchField;


    //Observable lists used in this class
    @FXML
    public ObservableList<Product> productAddTopField = FXCollections.observableArrayList();

    @FXML
    public ObservableList<Part> partTableBTM = FXCollections.observableArrayList();

    @FXML
    public ObservableList<Part> partSearchList = FXCollections.observableArrayList();
    @FXML
    public ObservableList<Part> deletedParts = FXCollections.observableArrayList();

    public static ObservableList<Part> partList = FXCollections.observableArrayList();

    Product product;

    //Generating a new Product ID each time it is called
    private final int newProductID = generateProductID();

    
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all entries and take you back to the main screen,"
                + "do you want to contiunue ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part selectedPart = partTblView.getSelectionModel().getSelectedItem();
            partList.remove(selectedPart);

            resetFields();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

   
    @FXML
    void onActionDelete(ActionEvent event) throws IOException {

        Part selectedPart = partListTBL.getSelectionModel().getSelectedItem();
        partList.remove(selectedPart);

    }
    public void resetFields() {
        productNameTxtFLD.setText("");
        productInvTxtFLD.setText("");
        productPriceTxtFLD.setText("");
        productMaxTxtFLD.setText("");
        productMinTxtFLD.setText("");

    }

    public void defaultColorFields() {
        productNameTxtFLD.setStyle("-fx-border-color: white");
        productInvTxtFLD.setStyle("-fx-border-color: white");
        productPriceTxtFLD.setStyle("-fx-border-color: white");
        productMaxTxtFLD.setStyle("-fx-border-color: white");
        productMinTxtFLD.setStyle("-fx-border-color: white");

    }

    @FXML
    void addProductSave(ActionEvent event) throws IOException {

        try {

            ObservableList<Part> partCheckList = FXCollections.observableArrayList();
            int id = newProductID;
            String newName = productNameTxtFLD.getText();
            int newInv = Integer.parseInt(productInvTxtFLD.getText());
            double newPrice = Double.parseDouble(productPriceTxtFLD.getText());
            int newMax = Integer.parseInt(productMaxTxtFLD.getText());
            int newMin = Integer.parseInt(productMinTxtFLD.getText());

            Product newProduct = new Product(id, newName, newPrice, newInv, newMin, newMax);
            newProduct.getAllAssociatedParts().addAll(partList);
            partCheckList.addAll(partList);

            if (newPrice <= 0) {

                System.out.println("Price Error");
                AlertMessages.productErrors(1, productPriceTxtFLD);
                return;
            }

            if (newName.isEmpty()) {
                System.out.println("Name of product needed" + newName);
                AlertMessages.productErrors(2, productNameTxtFLD);
                return;
            }

            if (newMax > newInv) {
                System.out.println("Max is larger then Inv");
                AlertMessages.productErrors(3, productMaxTxtFLD);
                return;
            }
            if (newMin <= 0) {
                System.out.println("Min needs to be higher than 0");
                AlertMessages.productErrors(4, productMinTxtFLD);
                return;

            }
            if (newMax < newMin) {
                System.out.println("Max less than min");
                AlertMessages.productErrors(5, productMaxTxtFLD);
                return;
            }
            if (newInv <= 0) {
                System.out.println("Inventory needs to be higher than 0");
                AlertMessages.productErrors(6, productInvTxtFLD);
                return;
            }
            if (partCheckList.isEmpty()) {
                System.out.println("part list empty");
                AlertMessages.partErrors(11, null);
                return;
            }

            Inventory.addProduct(newProduct);
            partList.removeAll(partList);
        } catch (NumberFormatException e) {
            System.out.println(e);
            
            AlertMessages.productFieldErrors(1, productNameTxtFLD, productPriceTxtFLD, productInvTxtFLD, productMaxTxtFLD, productMinTxtFLD);
            return;
        }

        defaultColorFields();
        resetFields();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML

    void onActionPartAdd(ActionEvent event) {

        //List to track if part has been added to the list
        ObservableList<Part> partCheckList = FXCollections.observableArrayList();

      
        Part selectedPart = partTblView.getSelectionModel().getSelectedItem();

        boolean repeatedPart = false;
        if (selectedPart == null) {
            System.out.println("Not Part Selected");
            AlertMessages.partErrors(11, null);
            return;
        } else {
            partCheckList.addAll(partList);
            int id = selectedPart.getId();
            for (int i = 0; i < partCheckList.size(); i++) {
                if (partCheckList.get(i).getId() == id) {
                    AlertMessages.partErrors(12, null);
                    repeatedPart = true;
                    System.out.println("Repeated Part");
                    return;
                }
            }
            if (!repeatedPart) {

                System.out.println("Not Repeated");
                partList.add(selectedPart);
                return;
            }

        }

    }

    @FXML
    void onActionProductSearch(ActionEvent event) {

        if (!partSearchField.getText().trim().isEmpty()) {
            partSearchList.clear();
            for (Part p : Inventory.getAllParts()) {
                if (p.getName().contains(partSearchField.getText().trim())) {
                    partSearchList.add(p);
                }
            }
            partTblView.setItems(partSearchList);
            partTblView.refresh();

        }
        if (partSearchField.getText().isEmpty()) {
            partTblView.setItems(Inventory.getAllParts());
        }

    }

    public void sendProduct(Product product) {
        productIDTxtFLD.setText(String.valueOf(product.getId()));
        productNameTxtFLD.setText(String.valueOf(product.getName()));
        productInvTxtFLD.setText(String.valueOf(product.getStock()));
        productPriceTxtFLD.setText(String.valueOf(product.getPrice()));
        productMaxTxtFLD.setText(String.valueOf(product.getMax()));
        productMinTxtFLD.setText(String.valueOf(product.getMin()));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partTblView.setItems(Inventory.getAllParts());

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        partListTBL.setItems(partList);
        addedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDTxtFLD.setEditable(false);

        productIDTxtFLD.setText("Auto Gen Disabled");

        productIDTxtFLD.setStyle("-fx-control-inner-background:gray");

        addedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

}
