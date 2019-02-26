package ullaf.controller;

import animatefx.animation.SlideInUp;
import database_ullaf.ConnectivityClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Overdue implements Initializable {

    public AnchorPane overdue_anchor;
    @FXML
    private TableView<TableModel> overdue_table;
    @FXML
    private TableColumn<TableModel, String > overdue_books;
    @FXML
    private TableColumn<TableModel, String>overdue_users;

    ObservableList<TableModel> observableList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SlideInUp(overdue_anchor).play();
        ConnectivityClass connectivityClass = new ConnectivityClass();
        Connection connection = connectivityClass.getConnection();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select DISTINCTROW Book_id, Student_id from overdue");

            while (resultSet.next()){
                observableList.add(new TableModel(resultSet.getString("Book_id"),resultSet.getString("Student_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        overdue_books.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        overdue_users.setCellValueFactory(new PropertyValueFactory<>("user_id"));
       overdue_table.setItems(observableList);
    }
}
