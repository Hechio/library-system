package ullaf.controller;

import database_ullaf.ConnectivityClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class entry {
    public Label enter_result;
    public Button entry_button;
    public String id;
    public TextField id_text;
    public AnchorPane anchor_entry;


    public void check(MouseEvent event) {


        ConnectivityClass connectionClass=new ConnectivityClass();
        Connection connection= connectionClass.getConnection();

        id=id_text.getText();
      if (id.length() < 8 && id.isEmpty()) {
          enter_result.setText("Invalid input");
      }
      else {
          Statement statement= null;
          try {
              statement = connection.createStatement();
          } catch (SQLException e) {
              e.printStackTrace();
          }
          String sql="SELECT id_field FROM registration WHERE id_field ='"+id_text.getText()+"'";
          ResultSet resultSet= null;
          try {
              resultSet = statement.executeQuery(sql);
          } catch (SQLException e) {
              e.printStackTrace();
          }
          try {
              if (resultSet.next()) {

                  try {
                      FXMLLoader loader= new  FXMLLoader(getClass().getResource("../fxml/status_active.fxml"));
                      Parent root= loader.load();
                      status_active status = loader.getController();
                      status.retrive_id(id_text.getText());
                      Stage stage_app = new Stage();
                      //stage_app.getIcons().add(image);
                      stage_app.initModality(Modality.APPLICATION_MODAL);
                      stage_app.focusedProperty().addListener(e-> {
                          stage_app.toFront();
                      });
                      stage_app.setScene(new Scene(root));


                      // Scene change_scene = new Scene(root);
                      //Stage stage_app = (Stage) ((Node) event.getSource()).getScene().getWindow();
                      // stage_app.setScene(change_scene);
                      stage_app.showAndWait();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }


              }
              else {
                  enter_result.setText("Invalid input");
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }


  }


    public void on_entry_click(MouseEvent event) {
      check(event);
    }

    public void new_register(ActionEvent actionEvent) {

        Parent change = null;
        try {
            change = FXMLLoader.load(getClass().getResource("../fxml/registration.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene change_scene = new Scene(change);
            Stage stage_app = new Stage();
            stage_app.setScene(change_scene);
           // stage_app.getIcons().add(image);
            //change_scene.getStylesheets().add("ullaf.css");
            stage_app.show();
    }
   /* public void close(AnchorPane anchorPane){
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }*/
}
