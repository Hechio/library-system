package ullaf.controller;
import database_ullaf.ConnectivityClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class registration {

    public ActionEvent event;
    public TextField id_field;
    public TextField name_field;
    public TextField email_address;


    public void register(ActionEvent actionEvent) {
        event = actionEvent;
        ConnectivityClass connectionClass=new ConnectivityClass();
            Connection connection= connectionClass.getConnection();
            String sql="INSERT INTO registration(name1, id_field) VALUES('"+name_field.getText()+"','"+id_field.getText()+"')";

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate(sql);

            Parent change = null;
            try {
                change = FXMLLoader.load(getClass().getResource("../fxml/entry.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene change_scene = new Scene(change);
            Stage stage_app = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage_app.setScene(change_scene);
            stage_app.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
