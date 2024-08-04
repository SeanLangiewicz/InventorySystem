/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.lang.NumberFormatException;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import model.Inventory;
import model.Product;
import model.Part;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AlertMessages;


/**
 * FXML Controller class
 *
 * @author Sean
 */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    AlertMessages alert = new AlertMessages();

    @FXML
    private TableView<Part> partListTbleView;

    @FXML
    private TableColumn<Part, Integer> selectedPartID;

    @FXML
    private TableColumn<Part, String> selectedPartName;

    @FXML
    private TableColumn<Part, Integer> selectedPartinv;

    @FXML
    private TableColumn<Part, Double> selectedPartPrice;

    @FXML
    private TextField modMax;

    @FXML
    private TextField modMin;

    @FXML
    private TextField productID;

    @FXML
    private TextField productName;

    @FXML
    private TextField productInv;

    @FXML
    private TextField productPrice;

    @FXML
    private TableView<Part> selectedPartTblVw;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInv;

    @FXML
    private TableColumn<Part, Double> partCost;

    @FXML
    private TextField partSearchField;

    public ObservableList<Part> addPartToProduct = FXCollections.observableArrayList();
    public ObservableList<Part> deletedParts = FXCollections.observableArrayList();
    public ObservableList<Part> partSearchList = FXCollections.observableArrayList();
    public ObservableList<Product> productModifyList = FXCollections.observableArrayList();
    public ObservableList<Part> partList = FXCollections.observableArrayList();

    Product product;

    /**
     * Initializes the controller class.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all entries and take you back to the main screen,"
                + "do you want to contiunue ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedPart = partListTbleView.getSelectionModel().getSelectedItem();
            this.product.deleteAssociatedPart(selectedPart);

            resetFields();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    @FXML
    void onActionDelete(ActionEvent event) {

        Part selectedPart = selectedPartTblVw.getSelectionModel().getSelectedItem();
        this.product.deleteAssociatedPart(selectedPart);

    }

    public void resetFields() {
        productName.setText("");
        productInv.setText("");
        productPrice.setText("");
        modMax.setText("");
        modMin.setText("");

    }

    public void defaultColorFields() {
       
        
        productName.setStyle("-fx-border-color: white");
        productInv.setStyle("-fx-border-color: white");
        productPrice.setStyle("-fx-border-color: white");
        modMax.setStyle("-fx-border-color: white");
        modMin.setStyle("-fx-border-color: white");

    }

    @FXML
    void onActionProductAdd(ActionEvent event) {

        //Local list to track if part has been added to the list
        ObservableList<Part> partCheckList = FXCollections.observableArrayList();

        
        partList.addAll(partCheckList);
        Part selectedPart = partListTbleView.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;
        if (selectedPart == null) {
            AlertMessages.partErrors(6, null);
            return;
        } else {
            partCheckList = product.getAllAssociatedParts();
            int id = selectedPart.getId();
            for (int i = 0; i < partCheckList.size(); i++) {
                if (partCheckList.get(i).getId() == id) {
                    AlertMessages.productErrors(5, null);
                    repeatedItem = true;
                    return;
                }
            }
            if (!repeatedItem) {
                this.product.addAssociatedPart(selectedPart);
               

                System.out.println(partCheckList);
                return;
            }
        }

    }

    @FXML
    void onActionProductSearch(ActionEvent event) {

     
        if (!partSearchField.getText().trim().isEmpty()) //if(!search.isEmpty())
        {
            partSearchList.clear();
            for (Part p : Inventory.getAllParts()) {
                if (p.getName().contains(partSearchField.getText().trim())) {
                    partSearchList.add(p);
                }
            }
            partListTbleView.setItems(partSearchList);
            partListTbleView.refresh();

        }
        if (partSearchField.getText().isEmpty()) {
            partListTbleView.setItems(Inventory.getAllParts());
        }

    }

    public void sendProduct(Product product) {
        productID.setText(String.valueOf(product.getId()));
        productName.setText(String.valueOf(product.getName()));
        productInv.setText(String.valueOf(product.getStock()));
        productPrice.setText(String.valueOf(product.getPrice()));
        modMax.setText(String.valueOf(product.getMax()));
        modMin.setText(String.valueOf(product.getMin()));

        selectedPartTblVw.setItems(product.getAllAssociatedParts());
        selectedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedPartinv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
       this.product = product;
    }

    @FXML
    void saveModifyProduct(ActionEvent event) throws IOException {

           

        try {

           int id = Integer.parseInt(productID.getText());
            String newName = productName.getText();
            int newInv = Integer.parseInt(productInv.getText());
            double newPrice = Double.parseDouble(productPrice.getText());
            int newMax = Integer.parseInt(modMax.getText());
            int newMin = Integer.parseInt(modMin.getText());
             
            Product product = new Product(id, newName, newPrice, newInv, newMin, newMax);
            product.getAllAssociatedParts().addAll(this.product.getAllAssociatedParts());
            partList.addAll(this.product.getAllAssociatedParts());


            if (newPrice <= 0) {
                System.out.println("Price Error");
                AlertMessages.productErrors(5, productPrice);
                defaultColorFields();
                return;
            }

            if (newName.isEmpty()) {
                //System.out.println("Name of product needed" + newName);
                AlertMessages.generalErrors(4, productName);
                return;
            }

            if (newMax > newInv) {
                //System.out.println("Max is larger then Inv");
                AlertMessages.generalErrors(2, modMax);
                return;
            }
            if (newMin <= 0) {
                //System.out.println("Min needs to be higher than 0");
                AlertMessages.generalErrors(2, modMin);
                return;

            }
            if (newMax < newMin) {
                //System.out.println("Max greater than min");
                AlertMessages.productErrors(6, modMax);
                
                return;
            }
            if (newInv <= 0) {
                //System.out.println("Inventory needs to be higher than 0");
                AlertMessages.generalErrors(8, productInv);
               
                return;
            }
            if (partList.isEmpty()) {
                //System.out.println("part list empty");
                AlertMessages.generalErrors(7, null);
                return;
            }

            Inventory.updateProduct(product, id);
        } 
        catch (NumberFormatException e) 
        {
            
            
            AlertMessages.productFieldErrors(1, productName,productInv,productPrice,modMax,modMin);
            return;
        }

            
        
        defaultColorFields();
        resetFields();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        productID.setEditable(false);
        productID.setStyle("-fx-control-inner-background:gray");
        partListTbleView.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
