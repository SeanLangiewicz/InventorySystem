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
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AlertMessages;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Sean
 */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

  /*
    // Unchanged Labels
    
    @FXML
    private Label IDLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label invLabel;

    @FXML
    private Label priceCostLabel;

    @FXML
    private Label maxLabel;
*/
    @FXML
    private Label companyLabel;

    @FXML
    private TextField partID;

    @FXML
    private TextField partName;

    @FXML
    private TextField partMachineInv;

    @FXML
    private TextField partCost;

    @FXML
    private TextField partMax;

    @FXML
    private TextField partMin;

    @FXML
    private TextField partMachineID;

    @FXML
    private RadioButton inHouseBTN;

    @FXML
    private RadioButton outSourceBTN;

    @FXML
    private ToggleGroup inHouseTG;

    AlertMessages alert = new AlertMessages();

    @FXML
    void onActionInHouse(ActionEvent event) {
        companyLabel.setText("InHouse");
        partMachineID.setText("");
        partMachineID.setPromptText("MachineID");

    }

    @FXML
    void onActionOutSource(ActionEvent event) {

        companyLabel.setText("OutSource");
        partMachineID.setText("");
        partMachineID.setPromptText("Company Name");
    }



    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(partID.getText().trim());
            String newName = partName.getText().trim();
            int newInv = Integer.parseInt(partMachineInv.getText().trim());
            double newPrice = Double.parseDouble(partCost.getText().trim());
            int newMax = Integer.parseInt(partMax.getText().trim());
            int newMin = Integer.parseInt(partMin.getText().trim());
            
           
            
            if (outSourceBTN.isSelected()) {

                String newCompanyName = partMachineID.getText();

                if (newCompanyName.isEmpty()) {
                    System.out.println("invalid company name");
                    AlertMessages.partErrors(9, partMachineID);
                    defaultColorFields();
                    return;
                } else {
                    System.out.println("Out source selected");
                    Part outSourcedPart = new Outsourced(id, newName, newPrice, newInv, newMax, newMin, newCompanyName);
                    Inventory.updatePart(outSourcedPart, id);
                    defaultColorFields();
                  
                }

                
            }
            if (inHouseBTN.isSelected()) {

                int newpartMachineID = Integer.parseInt(partMachineID.getText());
              
                if (newpartMachineID <= 0) {
                    System.out.println("Error in number");
                    AlertMessages.partErrors(10, partMachineID);
                    defaultColorFields();
                    return;
                } else {
                    Part inHousePart = new InHouse(id, newName, newPrice, newInv, newMax, newMin, newpartMachineID);
                    Inventory.updatePart(inHousePart, id);
                    defaultColorFields();
                 
                }

            }
            
          

            if (newName.isEmpty()) {
                System.out.println("part name null");
                AlertMessages.partErrors(1, partName);
                return;

            }
            
            if (newInv <= 0) {
                System.out.println("Inventory less than or equal to 0");
                AlertMessages.partErrors(2, partMachineInv);
                return;
            }
            if (newPrice <= 0) {
                System.out.println("Price is less than or equal to 0");
                AlertMessages.partErrors(3, partCost);
                return;
            }
            if (newMax < newMin) {
                System.out.println("Max Value Error. Min Can't be greater than Max");
                AlertMessages.partErrors(13, partMax);
                return;
            }
           
            if (newMin < 0 || newMin > newInv) {
                System.out.println("Min can't be less than or equal to 0 and can't be lower than inventory");
                AlertMessages.partErrors(5, partMin);
                return;
            }
            if ( newMax > newInv)
            {
                System.out.println("Max Error");
                AlertMessages.partErrors(5,partMax);
               return;
            }

              System.out.println(newInv + " Part Inv");
        } catch (NumberFormatException e) {
           
           
            AlertMessages.productFieldErrors(1, partName, partMachineInv, partCost, partMax, partMin);
            
            return;

        }

        defaultColorFields();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));

        stage.show();

    }

    void defaultColorFields() {
        partName.setStyle("-fx-border-color: white");
        partMachineInv.setStyle("-fx-border-color: white");
        partCost.setStyle("-fx-border-color: white");
        partMax.setStyle("-fx-border-color: white");
        partMin.setStyle("-fx-border-color: white");
    }

    public void resetFields() {
        partName.setText("");
        partMachineInv.setText("");
        partCost.setText("");
        partMax.setText("");
        partMin.setText("");
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        
        
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all entries and take you back to the main screen,"
                + "do you want to contiunue ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) 
        {
        resetFields();
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void sendPart(Part part) {
        if (part instanceof InHouse) {
            inHouseBTN.setSelected(true);
            InHouse inHousePart = (InHouse) part;
            partMachineID.setText(String.valueOf(inHousePart.getMachineId()));
            companyLabel.setText("Machine ID");
        } else {
            outSourceBTN.setSelected(true);
            Outsourced outSourcedPart = (Outsourced) part;
            partMachineID.setText(String.valueOf(outSourcedPart.getCompanyName()));
            companyLabel.setText("Company Name");
        }

        partID.setText(String.valueOf(part.getId()));
        partName.setText(String.valueOf(part.getName()));
        partMachineInv.setText(String.valueOf(part.getStock()));
        partCost.setText(String.valueOf(part.getStock()));
        partMax.setText(String.valueOf(part.getMax()));
        partMin.setText(String.valueOf(part.getMin()));

    }

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partID.setEditable(false);

    }

}
