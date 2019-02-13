package ullaf.controller;

import animatefx.animation.SlideInUp;
import database_ullaf.ConnectivityClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


    public class fined implements Initializable {
        public AnchorPane fine_anchor;
        public TextArea text_area;
        @FXML
        private TableView<TableModel> tableView;
        @FXML
        private TableColumn<TableModel, String > column_book;
        @FXML
        private TableColumn<TableModel, String> column_user;
       @FXML
       private TableColumn<TableModel, Double> column_fine;


        ObservableList<TableModel> observableList = FXCollections.observableArrayList();

        public fined() {
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            new SlideInUp(fine_anchor).play();
            ConnectivityClass connectivityClass = new ConnectivityClass();
            Connection connection = connectivityClass.getConnection();
            try {
                ResultSet resultSet = connection.createStatement().executeQuery("select Book_id, Student_id,Fine from fined");

                while (resultSet.next()){
                    observableList.add(new TableModel(resultSet.getString("Book_id"),resultSet.getString("Student_id"),resultSet.getDouble("Fine")));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            column_book.setCellValueFactory(new PropertyValueFactory<>("book_id"));
            column_user.setCellValueFactory(new PropertyValueFactory<>("user_id"));
            column_fine.setCellValueFactory(new PropertyValueFactory<>("fine_id"));
            tableView.setItems(observableList);
        }

        public void display_fine(MouseEvent event) {
            TableModel model = tableView.getSelectionModel().getSelectedItem();
            model.getBook_id();
            model.getFine_id();
            model.getUser_id();
            text_area.clear();
            text_area.appendText(
                    "Admission Number:\t " +model.getUser_id()+
                            "\nBook ID:\t\t\t" +model.getBook_id()+
                            "\nFine: \t\t\t"  +model.getFine_id()
            );
        }

        public void printFine(MouseEvent event) {
            MyPrinter print = new MyPrinter();
            print.Print(text_area);
        }

        public void clear_fine(ActionEvent actionEvent) {
            text_area.clear();
            TableModel model = tableView.getSelectionModel().getSelectedItem();
           double fine = model.getFine_id();
           if(fine>0){
               ConnectivityClass connectivityClass = new ConnectivityClass();
               Connection connection = connectivityClass.getConnection();
               String sql = "update fined set Fine=? where Student_id=?";
               try {
                   PreparedStatement pst = connection.prepareStatement(sql);
                   pst.setInt(1,0);
                   pst.setString(2,model.getUser_id());
                   pst.executeUpdate();
                   connection.close();
                   text_area.appendText(
                           "Admission Number:\t " +model.getUser_id()+
                                   "\nBook ID:\t\t\t" +model.getBook_id()+
                                   "\nFine: \t\t\t"   +model.getFine_id()
                   );

               }
               catch (SQLException e){
                   e.printStackTrace();

               }

           }


        }

        }



