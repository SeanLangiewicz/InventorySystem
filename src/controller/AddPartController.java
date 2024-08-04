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

import javafx.stage.Stage;
import model.AlertMessages;
import model.Inventory;
import model.Outsourced;

import static seanlangiewiczinventorysystem.SeanLangiewiczInventorySystem.generatePartID;

/**
 * FXML Controller class
 *
 * @author Sean
 */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    // @FXML
    //private Label IDLabel;
    @FXML
    private Label nameLabel;

    @FXML
    private Label invLabel;

    @FXML
    private Label priceCostLabel;

    @FXML
    private Label maxLabel;

    @FXML
    private Label companyLabel;

    @FXML
    private TextField partIDAdd;

    @FXML
    private TextField partAddName;

    @FXML
    private TextField partAddStock;

    @FXML
    private TextField partAddCost;

    @FXML
    private TextField partAddMax;

    @FXML
    private TextField partAddMin;

    @FXML
    private TextField partSource;

    @FXML
    private RadioButton inHouseBTN;

    @FXML
    private RadioButton outSourceBTM;

    @FXML
    private ToggleGroup inHouseTG;

    public int newPartID = generatePartID();

    @FXML
    void outSourceSelectBTN(ActionEvent event) {

        partIDAdd.setText("Auto Gen - Disabled");
        partIDAdd.setEditable(false);
        companyLabel.setText("Company Name");
        partSource.setPromptText("Company Name");
        partIDAdd.setStyle("-fx-text-fill: white");
        partIDAdd.setStyle("-fx-control-inner-background:gray");
    }

    @FXML
    void inHouseSelectBTN(ActionEvent event) {

        partIDAdd.setPromptText("Part ID");
        partIDAdd.setText("Auto Gen Enabled");
        partIDAdd.setEditable(false);

        partSource.setPromptText("Machine ID");

        companyLabel.setText("Machine ID");

        partIDAdd.setStyle("-fx-control-inner-background:white");

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try {

            String name = partAddName.getText();
            int stock = Integer.parseInt(partAddStock.getText());
            double price = Double.parseDouble(partAddCost.getText());
            int max = Integer.parseInt(partAddMax.getText());
            int min = Integer.parseInt(partAddMin.getText());
            String companyName = partSource.getText();

            if (name.isEmpty()) {
                System.out.println("part name null");
                AlertMessages.partErrors(1, partAddName);
                return;

            }
            if (stock <= 0) {
                System.out.println("Inventory less than or equal to 0");
                AlertMessages.partErrors(2, partAddStock);
                return;
            }
            if (price <= 0) {
                System.out.println("Price is less than or equal to 0");
                AlertMessages.partErrors(3, partAddCost);
                return;
            }
            if (max <= 0 || max < min) {
                System.out.println("Max Value Error. Min Can't be greater than Max");
                AlertMessages.partErrors(4, partAddMax);
                return;
            }
            if (min <= 0 || min > stock) {
                System.out.println("Min can't be less than or equal to 0 and can't be lower than inventory");
                AlertMessages.partErrors(5, partAddMin);
                return;
            }
            if (inHouseBTN.isSelected()) {

                int machineIDIntCheck = Integer.parseInt(partSource.getText().trim());

                if (machineIDIntCheck <= 0) {

                    System.out.println("Machine ID empty");
                    AlertMessages.partErrors(7, partIDAdd);
                    return;
                }

            }
            if (outSourceBTM.isSelected()) {
                String companyNameCheck = partIDAdd.getText();

                if (companyNameCheck.isEmpty()) {

                    AlertMessages.partErrors(6, partIDAdd);
                    return;

                }
            }
            if (!inHouseBTN.isSelected() && !outSourceBTM.isSelected()) {

                AlertMessages.partErrors(8, null);
                return;
            }

            Inventory.addPart(new Outsourced(newPartID, name, price, stock, min, max, companyName));
        } catch (NumberFormatException e) {
             System.out.println(e);
             //(int code, TextField name, TextField Inv, TextField price, TextField Max, TextField Min, TextField partSource)
             AlertMessages.partFieldErrors(1, partAddName, partAddStock,partAddCost,partAddMax,partAddMin,partSource);
            
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
    void onActionCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all entries and take you back to the main screen,"
                + "do you want to contiunue ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            resetFields();

        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partIDAdd.setText("Auto Gen-Disabled");
        partIDAdd.setEditable(false);
        inHouseBTN.setSelected(true);
        companyLabel.setText("Machine ID");
        partSource.setPromptText("Machine ID");
        

    }

    public void resetFields() {
        partIDAdd.setText("");
        partAddName.setText("");
        partAddStock.setText("");
        partAddCost.setText("");
        partAddMax.setText("");
        partAddMin.setText("");
        partSource.setText("");
    }
    void defaultColorFields() {
        partAddName.setStyle("-fx-border-color: white");
        partAddStock.setStyle("-fx-border-color: white");
        partAddCost.setStyle("-fx-border-color: white");
        partAddMax.setStyle("-fx-border-color: white");
        partAddMin.setStyle("-fx-border-color: white");
    }

}
