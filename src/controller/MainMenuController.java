/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;
import model.AlertMessages;


/**
 * FXML Controller class
 *
 * @author Sean
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    private static ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();
    private static ObservableList<Product> productInventoryList = FXCollections.observableArrayList();
    private static ObservableList<Part> partInventorySearch = FXCollections.observableArrayList();
    private static ObservableList<Part> partInventoryList = FXCollections.observableArrayList();
    
    
    //TEMP
     public ObservableList<Product> deletedProduct = FXCollections.observableArrayList();
   

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevel;

    @FXML
    private TableColumn<Part, Double> partCost;

    @FXML
    private Button partDelete;

    @FXML
    private TextField partSearchTxtFLD;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productID;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productInventory;

    @FXML
    private TableColumn<Product, Double> productPrice;

    @FXML
    private Button productSearchBTN;

    @FXML
    private TextField productSearchFLD;

    @FXML
    private Button productModify;

    @FXML
    private Button productDelete;

    /**
     * Initializes the controller class.
     */
    
    AlertMessages alert = new AlertMessages();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Set default product table view values
        productTableView.setItems(Inventory.getAllProducts());

        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set default part view values
        partTableView.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        
    }

    @FXML
    void onActionExit(ActionEvent event) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will close the program, do you want to contiunue ?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK)
        {
            
        System.exit(0);
        
   
        }
       

    }

    @FXML
    void onActionPartAdd(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    void onActionPartModify(ActionEvent event) throws IOException {
        try {
           Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
           if(Inventory.getAllParts().isEmpty())
           {
              //alert.partErrors(5);
              return;
           }
           if(!Inventory.getAllParts().isEmpty() && selectedPart ==null)
           {
             //  alert.partErrors(2);
               return;
           }
           
           
           else
           {
            FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();

        ModifyPartController ModPartController = loader.getController();
        ModPartController.sendPart(partTableView.getSelectionModel().getSelectedItem());
       

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
           }
        } catch (NullPointerException e) {
            System.out.println("Exception:" + e);
            //alert.generalErrors(1);
        }
        
        
        
       
    }

    @FXML
    void onActionProductAdd(ActionEvent event) throws IOException {

        
        
         
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    
    @FXML
    void onActionPartDelete(ActionEvent event) {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will delete this part,do you want to contiunue ?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK)
        {
            
         Part deletedPart=partTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(deletedPart);
        
   
        }
      
    }

    @FXML
    void onActionProductModify(ActionEvent event) throws IOException {

        
        
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController ModProductController = loader.getController();
            ModProductController.sendProduct(productTableView.getSelectionModel().getSelectedItem());
            

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
          
        }

    }

    @FXML
    void onActionProductSearch(ActionEvent event) {

        
        if (!productSearchFLD.getText().trim().isEmpty()) {
            productInventorySearch.clear();
            for (Product p : Inventory.getAllProducts()) {
                if (p.getName().contains(productSearchFLD.getText().trim())) {
                    productInventorySearch.add(p);
                }
            }
            productTableView.setItems(productInventorySearch);
            productTableView.refresh();

        }
        if (productSearchFLD.getText().isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
        }

    }

    @FXML
    void onActionPartSearch(ActionEvent event) {
        
        if (!partSearchTxtFLD.getText().trim().isEmpty()) {
      
        {
            partInventorySearch.clear();
            for (Part partSearch : Inventory.getAllParts()) {
                if (partSearch.getName().contains(partSearchTxtFLD.getText().trim())) {
                    partInventorySearch.add(partSearch);
                }
            }
            partTableView.setItems(partInventorySearch);
            partTableView.refresh();
        }
        if (partSearchTxtFLD.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }

    }
    }

    @FXML
    void onActionProductDelete(ActionEvent event) {
      
     
            
         Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedProduct);
        
   
        
        
      
       
    }


}
