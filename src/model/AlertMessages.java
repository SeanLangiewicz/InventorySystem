/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 *
 * @author Sean
 */
public class AlertMessages {

    public static void productErrors(int code, TextField field) {
        fieldError(field);
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price Error");
            alert.setHeaderText("Invalid Price Set");
            alert.setContentText("Price needs to be equal to or greater than 1");
            alert.showAndWait();
        }
        if (code == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Name");
            alert.setHeaderText("Product Needs a Name");
            alert.setContentText("Please Enter a name for the product");
            alert.showAndWait();

        }
        if (code == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Max number set too high");
            alert.setHeaderText("Max Set too high");
            alert.setContentText("Max needs to be lower than Inventory");
            alert.showAndWait();
        }
        
        //Min needs to be higher than 0
          if (code == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Min too low");
            alert.setHeaderText("Min value is set too low");
            alert.setContentText("Min needs to be higher than 0");
            alert.showAndWait();
        }
          //Max less than min
           if (code == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Max higher than min");
            alert.setHeaderText("Max is not higher than min");
            alert.setContentText("Max needs to be greater than or equal to min");
            alert.showAndWait();
        }
           //Inventory needs to be higher than 0"
            if (code == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory not high enough");
            alert.setHeaderText("Inventory needs to be greater");
            alert.setContentText("Inventory needs to be higher than 0");
            alert.showAndWait();
        }
           
           
          
    }

    public static void partErrors(int code, TextField field) {
        fieldError(field);

        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Name");
            alert.setHeaderText("Invalid Part Name");
            alert.setContentText("No Part Name, please enter a part name");
            alert.showAndWait();
        }
        if (code == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Error");
            alert.setHeaderText("Invalid Inventory count entered");
            alert.setContentText("Inventory needs to be greater to 0");
            alert.showAndWait();
        }
         if (code == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price Error");
            alert.setHeaderText("Price Entry Invalid");
            alert.setContentText("Price needs to be greater to 0");
            alert.showAndWait();
        }
          if (code == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Min Value Error");
            alert.setHeaderText("Min Value Invalid");
            alert.setContentText("Min can't be greater than Max");
            alert.showAndWait();
        }
          if (code == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Max Value Error");
            alert.setHeaderText("Max Value Invalid");
            alert.setContentText("Max can't greater than inventory");
            alert.showAndWait();
        }
          if (code == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Company Name Entered");
            alert.setHeaderText("Invalid Company Name");
            alert.setContentText("No Company Name Entered");
            alert.showAndWait();
        }
           if (code == 7) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Number Entered");
            alert.setHeaderText("Invalid Part Number");
            alert.setContentText("Please Enter a part number and machineID can't be less than or equal to 0");
            alert.showAndWait();
        }
           if (code == 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("InHouse / Outsourced Not Selected");
            alert.setHeaderText("Select a source");
            alert.setContentText("Please select if this is an InHouse or Outsourced part");
            alert.showAndWait();
        }
            if (code == 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Company Name");
            alert.setHeaderText("No Company name entered");
            alert.setContentText("Please enter a company name");
            alert.showAndWait();
        }
             if (code == 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Machine ID");
            alert.setHeaderText("No Machine ID entered");
            alert.setContentText("Please enter a Machine ID");
            alert.showAndWait();
        }
              if (code == 11) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Added");
            alert.setHeaderText("No Part has been added");
            alert.setContentText("No Part has been added to the product, please select a part and try again");
            alert.showAndWait();
        }
       if (code == 12) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Repeated Part");
            alert.setHeaderText("Failed to Add Part");
            alert.setContentText("The Part Selected has already been added, please add a different part");
            alert.showAndWait();
        }
       
        if (code == 13) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Max Value Error");
            alert.setHeaderText("Max Value Invalid");
            alert.setContentText("Max can not be larger than Inventory");
            alert.showAndWait();
        }
    }

    public static void generalErrors(int code, TextField field) 
     
    {

        fieldError(field);
      
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Invalid Input. All Fields need to be filled out. Please try again");
            alert.showAndWait();
        }
//         if (code == 2) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Cancel Selected");
//            alert.setHeaderText("Moving back to Main Screen");
//            alert.setContentText("This will delete all values and take you back to the ");
//            alert.showAndWait();
//        }
        
    }

      
       public static void productFieldErrors (int code, TextField name, TextField Inv, TextField price, TextField Max, TextField Min)
    {
      productAllFieldErrorColor(name, Inv, price, Max, Min);  
      
       if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Invalid Input. All Fields need to be filled out. Please try again");
            alert.showAndWait();
    }
       
       
    }
       
     public static void partFieldErrors (int code, TextField name, TextField Inv, TextField price, TextField Max, TextField Min, TextField partSource)
    {
        partAllFieldErrors(name, Inv, price, Max, Min, partSource);
      
       if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Invalid Input. All Fields need to be filled out. Please try again");
            alert.showAndWait();
    }
       
       
    }
       
    private static void fieldError(TextField field) {
        if (field != null) 
        {
            field.setStyle("-fx-border-color: red");
        }
    }
    private static void productAllFieldErrorColor(TextField name,TextField Inv, TextField price, TextField Max, TextField Min)
    {
        
          if (name != null) 
        {
            name.setStyle("-fx-border-color: red");
        }
           if (Inv != null) 
        {
            Inv.setStyle("-fx-border-color: red");
        }
            if (price != null) 
        {
            price.setStyle("-fx-border-color: red");
        }
             if (Max != null) 
        {
            Max.setStyle("-fx-border-color: red");
        }
              if (Min != null) 
        {
            Min.setStyle("-fx-border-color: red");
        }
    }
    
    private static void partAllFieldErrors(TextField name,TextField Inv, TextField price, TextField Max, TextField Min,TextField partSource)
    {
        
          if (name != null) 
        {
            name.setStyle("-fx-border-color: red");
        }
           if (Inv != null) 
        {
            Inv.setStyle("-fx-border-color: red");
        }
            if (price != null) 
        {
            price.setStyle("-fx-border-color: red");
        }
             if (Max != null) 
        {
            Max.setStyle("-fx-border-color: red");
        }
              if (Min != null) 
        {
            Min.setStyle("-fx-border-color: red");
        }
                    if (partSource != null) 
        {
            partSource.setStyle("-fx-border-color: red");
        }
    }

}
