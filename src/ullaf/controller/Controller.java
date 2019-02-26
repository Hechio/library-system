package ullaf.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import animatefx.animation.Flash;
import animatefx.animation.SlideInUp;
import animatefx.animation.SlideOutRight;
import database_ullaf.ConnectivityClass;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import org.controlsfx.control.textfield.CustomTextField;


public class Controller implements Initializable {

    public Label id;
    public Label name;
    public Label author;
    public Label duration;
    public AnchorPane search_anchor;
    public Label id_label;
    public Label name_label;
    public Label author_label;
    public Label duration_label;
    public Label details_label;
    public Label profileStaff;
    public TextArea search_text;

    @FXML
    private TableView<TableModel1> tableView;
    @FXML
    private TableColumn<TableModel1, String > book_id;
    @FXML
    private TableColumn<TableModel1, String> book_name;
    @FXML
    private TableColumn<TableModel1, String > book_author;
    @FXML
    private TableColumn<TableModel1, String> book_duration;
    ObservableList<TableModel1> observableList1 = FXCollections.observableArrayList();

    public ImageView admin_a;
    public  Label loaning_text;
    public Button overdue_button;
    public CustomTextField search_student;
    public CustomTextField search_nonstudent;
    public CustomTextField search_book;
    public Button entry_button;
    private Stage stage;
    public BorderPane borderpane;


    public Controller() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         new Flash(entry_button).play();
        try {
            borderpane.setCenter(FXMLLoader.load(getClass().getResource("../fxml/onloan.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        search_book.setRight(search_bookID);
        search_student.setRight(search_sudentID);
        //search_nonstudent.setRight(search_nonID);
        search_sudentID.setCursor(Cursor.HAND);
        search_bookID.setCursor(Cursor.HAND);
        //search_nonID.setCursor(Cursor.HAND);
        search_bookID.getStyleClass().add("icon");
        search_sudentID.getStyleClass().add("icon");
        //search_nonID.getStyleClass().add("icon");
        Search_books();
        Search_students();
        overdue_update();


}

    FontAwesomeIconView search_bookID = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
    FontAwesomeIconView search_sudentID = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
   // FontAwesomeIconView search_nonID = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
   public void retrive_id(String users) {
       profileStaff.setText(users);
   }

    public void start(Stage stage)throws FileNotFoundException {
        Image image = new Image(new FileInputStream("/images/gra.jpg"));
        admin_a = new ImageView(image);
        stage.show();
    }


    public void loadUi(String ui) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/"+ui + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderpane.setCenter(root);
    }




    public void onloan(MouseEvent event) {
        loadUi("onloan");
    }


    public void cleared(MouseEvent event) {
        loadUi("cleared");
    }

    public void entry_button(MouseEvent event) {
       loadUi("entry");
            /*Parent change = FXMLLoader.load(getClass().getResource("../fxml/entry.fxml"));
            Scene change_scene = new Scene(change);
            Stage stage_app = new Stage(); //(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage_app.setScene(change_scene);
            //change_scene.getStylesheets().add("ullaf.css");
            stage_app.show();*/
    }

    public void overdue(MouseEvent event) {
        loadUi("overdue");
    }

    public void Search_books(){
        search_bookID.setOnMouseClicked(event ->
              searching_book());
            //search_book.setText("Working"));

    }
    public void Search_students(){
        search_student.setOnMouseClicked(event ->
                searching_student());
    }


    public void overdue_update(){

        ConnectivityClass connectionClass = new ConnectivityClass();
        Connection connection = connectionClass.getConnection();
        String sql = "SELECT Time_loaned,Duration FROM onloan";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet=pst.executeQuery();
            while (resultSet.next()){
            String time_to_return =resultSet.getString("Duration");
            Timestamp when_loaned=resultSet.getTimestamp("Time_loaned");
                java.util.Date date = new java.util.Date();
                Timestamp hechio = new Timestamp(date.getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(hechio.getTime());
                long milliseconds = hechio.getTime() - when_loaned.getTime();
                int seconds = (int) (milliseconds / 1000);
                String due[]={"LONG","SHORT"};
                if ((time_to_return.equals(due[0])&& seconds>86400)||(time_to_return.equals(due[1])&& seconds>7200)){
                    String query = "insert into overdue select Book_id,Student_id,Duration,Date_loaned,Time_loaned from onloan";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    SendEmail email = new SendEmail();
                    email.sendEmailMessage("Overdue Book","Please return book to the library to avoid fining","westvirginia01@gmail.com");
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Exception occurred");
        }
    }


    public void onFined(ActionEvent actionEvent) {
        loadUi("fined");
    }


    public void close(BorderPane borderPane){
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

    }

    public void log_out(ActionEvent actionEvent)
            throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to log out?");
        alert.setTitle("Log out");
        alert.setHeaderText(null);

        Optional<ButtonType> answer =   alert.showAndWait();
        if(answer.get()==ButtonType.OK){
            Parent change = FXMLLoader.load(getClass().getResource("../fxml/authentication.fxml"));
            Scene change_scene = new Scene(change);
            Stage stage_app = new Stage(); //(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage_app.setScene(change_scene);
            //change_scene.getStylesheets().add("ullaf.css");
            //stage_app.getIcons().add(image1);
            stage_app.show();
            close(borderpane);
        }


    }
    public void searching_book() {
        ConnectivityClass connectionClass = new ConnectivityClass();
        Connection connection = connectionClass.getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "SELECT * FROM books WHERE Book_id ='" + search_book.getText() + "'";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet.next()) {

                try {
                    details_label.setText(null);
                    duration_label.setText(null);
                    id_label.setText(null);
                    name.setText(null);
                    author.setText(null);
                    duration.setText(null);
                    String book_id = resultSet.getString("Book_id");
                    String book_name = resultSet.getString("Book_name");
                    String book_author =resultSet.getString("Book_author");
                    String book_duration = resultSet.getString("Book_duration");
                    details_label.setText("Book Details");
                    id_label.setText("ID");
                    name_label.setText("Name");
                    author_label.setText("Author");
                    duration_label.setText("Duration");
                    id.setText(book_id);
                    name.setText(book_name);
                    author.setText(book_author);
                    duration.setText(book_duration);
                    borderpane.setCenter(search_anchor);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                id.setText("Invalid input");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void searching_student() {
        details_label.setText(null);
        author_label.setText(null);
        duration_label.setText(null);
        id_label.setText(null);
        name.setText(null);
        author.setText(null);
        duration.setText(null);
        ConnectivityClass connectionClass = new ConnectivityClass();
        Connection connection = connectionClass.getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "SELECT * FROM registration WHERE id_field ='" + search_student.getText() + "'";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet.next()) {

                try {
                    String book_id = resultSet.getString("id_field");
                    String book_name = resultSet.getString("name1");
                    details_label.setText("Student's Details");
                    id_label.setText("ID");
                    name_label.setText("Name");
                    id.setText(book_id);
                    name.setText(book_name);
                    borderpane.setCenter(search_anchor);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                id.setText("Invalid input");
            }
            String query= "select Book_id from returned where Student_id='"+id_label.getText()+"'";
            PreparedStatement statement1 = connection.prepareStatement(query);
            ResultSet resultSet1=statement1.executeQuery();
            while (resultSet1.next()){
                String books = resultSet1.getString("Book_id");
                search_text.appendText(
                        "Books loaned: \t" +books
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void profile_load(ActionEvent actionEvent) throws IOException {
       Image image1 = new Image("/images/logo.jpg");
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("../fxml/profile.fxml"));
        Parent root= loader.load();
        Profile pro = loader.getController();
        pro.displayId(profileStaff.getText());
        Stage stage_app = new Stage();
        stage_app.setScene(new Scene(root));
        stage_app.getIcons().add(image1);
        stage_app.show();

    }


    public void add_book(ActionEvent actionEvent) {
       loadUi("book_adding");
    }
}

