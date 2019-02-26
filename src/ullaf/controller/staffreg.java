package ullaf.controller;

import database_ullaf.ConnectivityClass;
import database_ullaf.ConnectivityClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ullaf.controller.validator;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class staffreg {
    public ActionEvent event;
    public TextField staff_id;
    public TextField name_field;
    public TextField password;
    public TextField confirm_password;
    public PasswordField admin_pass;
    public TextField email;


    @FXML
    public Label PasswordErrorLabel;
    public Label report_email;

    validator validate= new validator();

    public void staffreg(ActionEvent actionEvent) {

        event = actionEvent;
        ConnectivityClass connectionClass=new ConnectivityClass();
        Connection connection= connectionClass.getConnection();
        String query="select key_in from admin";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
          ResultSet resultSet= pst.executeQuery();
          while (resultSet.next()){
              String getPass = resultSet.getString("key_in");
              if(getPass.equals(admin_pass.getText())){


                  String sql="INSERT INTO STAFF_REGISTRATION(name, staff_id, password, confirm_password, email) VALUES('"+name_field.getText()+"','"+staff_id.getText()+"','"+password.getText()+"','"+confirm_password.getText()+"','"+email.getText()+"')";
                  Statement statement = null;
                  try {
                      statement = connection.createStatement();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
                  try {
                      statement.executeUpdate(sql);
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
                  try {
                      if(validate.validate(password.getText())&& validate.validate(confirm_password.getText())){
                          statement.executeUpdate(sql);


                      }else{
                          PasswordErrorLabel.setText("incorrect password");
                          PasswordErrorLabel.setStyle("-fx-text-fill:red;");
                      }
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
                  Parent change = null;
                  try {
                      change = FXMLLoader.load(getClass().getResource("../fxml/authentication.fxml"));
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
                  Scene change_scene = new Scene(change);
                  Stage stage_app = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                  stage_app.setScene(change_scene);
                  stage_app.show();
              }
              else {
                  System.out.println("Check details");
              }
          }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void back_entry(MouseEvent event) {
        Parent change = null;
        try {
            change = FXMLLoader.load(getClass().getResource("../fxml/authentication.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene change_scene = new Scene(change);
        Stage stage_app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage_app.setScene(change_scene);
        //stage_app.getIcons().add(image);
        stage_app.show();
    }
    



    public void emailCheck(KeyEvent keyEvent) {
        String emailEntered = email.getText();
        validator valid = new validator();
        if( valid.emailValidate(emailEntered));
        else report_email.setText("Invalid email");
    }
}
