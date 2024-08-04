/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seanlangiewiczinventorysystem;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import model.Part;


/**
 *
 * @author Sean
 */
public class SeanLangiewiczInventorySystem extends Application {

      public static Integer partIDAutoGenNumb =0;
      public static  Integer productIDAutoGenNumb = 0;
      
      
     
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        
    }

    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
   
            //Value order
            //int id, String name, double price, int stock, int min, int max
       
            
           Product product1 = new Product(generateProductID(),"Product1",5.00,5,5,5);
           Product product2 = new Product(generateProductID(),"Product2",10.00,10,5,300);
           Product product3 = new Product(generateProductID(),"Product3",15.00,12,5,5);
            Product product4 = new Product(generateProductID(),"Product4",5000.00,7500,5,5000);
             Product product5 = new Product(generateProductID(),"Product5",1500.00,700,5,500);
              Product product6 = new Product(generateProductID(),"Product6",300.00,500,5,2500);
           
           // Value order
           //int id,String name,double price, int stock, int min, int max, int machineId
           
           InHouse part1 = new InHouse(generatePartID(),"Part1",5.00,5,5,20,504738);
           InHouse part2 = new InHouse(generatePartID(),"Part2",10.00,10,5, 50,123456);
            InHouse part3 = new InHouse(generatePartID(),"Part3",1000.00,100,5, 3500,123456);
           
           Outsourced part4 = new Outsourced(generatePartID(),"Part4",15.00,12,10,20,"Company 1");
           Outsourced part5 = new Outsourced(generatePartID(),"Part5",75.00,35,5,20,"Company 2");
             Outsourced part6 = new Outsourced(generatePartID(),"Part6",100.00,90,5,35,"Company 3");
           
           
          
          //  Part part1 = new Part(1,"part1",5,5,5,5);
                   
           //Added Products
           Inventory.addProduct(product1);
           Inventory.addProduct(product2);
           Inventory.addProduct(product3);
           Inventory.addProduct(product4);
           Inventory.addProduct(product5);
            Inventory.addProduct(product6);
           
           //Added Parts
           Inventory.addPart(part1);
           Inventory.addPart(part2);
           Inventory.addPart(part3);
           Inventory.addPart(part4);
           Inventory.addPart(part5);
           Inventory.addPart(part6);
                launch(args);
        }
     public static Integer generatePartID()
    {
    
       // Integer startingNumb = 1000;
        //Integer incNumb = startingNumb +1;
        //startingNumb ++;
        
        partIDAutoGenNumb ++;
        //partIDAutoGenNumb = partIDAutoGenNumb + incNumb;
        
        
       // System.out.println(partIDAutoGenNumb);
        return partIDAutoGenNumb;
    }
     public static Integer generateProductID ()
             {
                 productIDAutoGenNumb++;
                 
                 return productIDAutoGenNumb;
             }


    
}
