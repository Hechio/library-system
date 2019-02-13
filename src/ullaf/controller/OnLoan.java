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

public class OnLoan implements Initializable {
    public AnchorPane onloan_anchor;
    @FXML
    private TableView<TableModel> tableView;
    @FXML
    private TableColumn<TableModel, String > column_book;
    @FXML
    private TableColumn<TableModel, String> column_user;

    ObservableList<TableModel> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SlideInUp(onloan_anchor).play();
        ConnectivityClass connectivityClass = new ConnectivityClass();
        Connection connection = connectivityClass.getConnection();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select Book_id, Student_id from onloan");

           while (resultSet.next()){
               observableList.add(new TableModel(resultSet.getString("Book_id"),resultSet.getString("Student_id")));
           }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        column_book.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        column_user.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        tableView.setItems(observableList);
    }
}
