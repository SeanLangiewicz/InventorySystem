/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.script.ScriptEngine;
import model.Part;
import model.Product;
import model.AlertMessages;

/**
 *
 * @author Sean
 */


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
   
     

    public static Product selectProduct(int id) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    public static void addPart(Part newPart) {

        allParts.add(newPart);

    }

    public static void addProduct(Product newProduct) {
       
        allProducts.add(newProduct);
    }
/*
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> partSearchResult = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for (Part partSeach : allParts) {
            if (partSeach.getName().contains(partName)) {
                partSearchResult.add(partSeach);
            }
        }

        return partSearchResult;

    }
*/

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResult = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();
     

        for (Product productSearch : allProducts) {
            if (productSearch.getName().contains(productName)) {

                searchResult.add(productSearch);
            }

        }

        return searchResult;
    }

    public static void updatePart(Part selectedPart, int index) {
        int updateIndex = -1;

        for (Part p : Inventory.getAllParts()) {

            updateIndex++;
            if (p.getId() == index) {
                Inventory.getAllParts().set(updateIndex, selectedPart);

            }
        }

    }

    public static void updateProduct(Product newProduct, int id) {
        {
            int updateIndex = -1;

            for (Product p : Inventory.getAllProducts()) {
                updateIndex++;
                if (p.getId() == id) {
                    allProducts.set(updateIndex, newProduct);
                    break;

                }

            }

        }
    }

    public static boolean deletePart(Part selectedPart) {

       // System.out.println(selectedPart);
       
       if(allParts.remove(selectedPart))   
          return  true;
     
        return false;
    }

    public static boolean deleteProduct(Product selectedProduct) {

        
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this product ?");
        AlertMessages myAlert = new AlertMessages();
       
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK)
        {
             if(allProducts.remove(selectedProduct))
           return true;
            
        }
      
        if (selectedProduct == null) {
           // myAlert.productErrors(4);
        }
        return false;
    }
    public static boolean deleteTempProduct (Product selectedProduct)
    {
        allProducts.remove(selectedProduct);
        return false;
    }

    public static Part lookupPart(Integer partID) {
        Part Part = null;

        return Part;
    }

    public static Product lookupProduct(Integer productID) {
        Product Product = null;

        return Product;
    }

    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
