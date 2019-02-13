package ullaf.controller;

import animatefx.animation.SlideInUp;
import database_ullaf.ConnectivityClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;

public class BookAdding implements Initializable {

    public TextField book_id;
    public TextField book_name;
    public TextField author_field;
    public Button button_add;
    public ComboBox<String> duration_field;
    public AnchorPane anchor_book;
    private ActionEvent event;
    private ObservableList durations = FXCollections.observableArrayList();





    public void button_add(ActionEvent actionEvent) {
        event = actionEvent;
        ConnectivityClass connectionClass=new ConnectivityClass();
        Connection connection= connectionClass.getConnection();
        String sql="INSERT INTO books(Book_id, Book_name, Book_author, Book_duration) VALUES('"+book_id.getText()+"','"+book_name.getText()+"','"+author_field.getText()+"','"+duration_field.getValue()+"')";

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate(sql);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Book added!");
            alert.setTitle("Book Addition");
            alert.setHeaderText(null);

            alert.showAndWait();

            /*try {
                change = FXMLLoader.load(getClass().getResource("../fxml/ullafmain.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene change_scene = new Scene(change);
            Stage stage_app = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage_app.setScene(change_scene);
            stage_app.setResizable(false);
            stage_app.setMaximized(true);
            stage_app.show();*/
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Book not added!");
            alert.setTitle("Book Addition");
            alert.setHeaderText(null);

            alert.showAndWait();

            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SlideInUp(anchor_book).play();
        myCombo(this.duration_field);

    }
    public void myCombo(ComboBox duration_loaned) {

        durations.removeAll(durations);
        String two_duration[] = {"LONG", "SHORT"};
        for (String Duration : two_duration) {
            durations.addAll(Duration);
        }
        Collections.sort(durations);
        duration_loaned.getItems().addAll(durations);
    }
}
