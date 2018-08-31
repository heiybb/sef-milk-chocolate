package application;

import java.awt.Label;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.jws.soap.SOAPBinding.Style;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

public class MyController implements Initializable {

   @FXML
   private Button startgame;

   @FXML
   private Button quitgame;
   
   @FXML
   private Button showtime;

   @FXML
   private TextField currenttime;
   
   @FXML
   private GridPane showgame;

   @Override
   public void initialize(URL location, ResourceBundle resources) {

       // TODO (don't really need to do anything here).

   }

   // When user click on myButton
   // this method will be called.
   public void showDateTime(ActionEvent event) {
       System.out.println("Button Clicked!");

       Date now= new Date();

       DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
       String dateTimeString = df.format(now);
        // Show in VIEW
       currenttime.setText(dateTimeString);

   }
   
   public void showgame(ActionEvent event) {
	   showgame.applyCss();
	   showgame.autosize();
	   
   }
   
   public void onUp(ActionEvent event) {
	   
   }
   
   public void onDown(ActionEvent event) {
	   
   }
   
   public void onLeft(ActionEvent event) {
	   
   }
   
   public void onRight(ActionEvent event) {
	   
   }
   
   
   
   

}