package ullaf.controller;

import database_ullaf.ConnectivityClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Profile {


    public AnchorPane profile_anchor;
    public Label profile_id;
    public Label deleted;

    public void displayId(String user){
       profile_id.setText(user);
   }


    public void delete_account(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to delete account "+profile_id.getText()+" ?");
        alert.setTitle("Delete account");
        alert.setHeaderText(null);

        Optional<ButtonType> answer =   alert.showAndWait();
        if(answer.get()==ButtonType.OK){
            ConnectivityClass connectivityClass = new ConnectivityClass();
            Connection connection = connectivityClass.getConnection();
            String sql = "DELETE FROM staff_registration WHERE staff_id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,profile_id.getText());
                preparedStatement.executeUpdate();
                   deleted.setText("Account deleted");
            } catch (SQLException e) {
                deleted.setText("Account not deleted");
                e.printStackTrace();
            }
            Parent change = null;
            try {
                change = FXMLLoader.load(getClass().getResource("../fxml/authentication.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene change_scene = new Scene(change);
            Stage stage_app = new Stage(); //(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage_app.setScene(change_scene);
            stage_app.show();
            entry en = new entry();
            en.close(profile_anchor);


        }
    }
}
