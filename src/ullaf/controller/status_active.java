package ullaf.controller;

import database_ullaf.ConnectivityClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;

import javax.xml.datatype.Duration;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;


public class status_active extends Thread implements Initializable {


    public TextField book_borrowed;
    public Label if_issued;
    public Label lable_id;
    public Button loan_start;
    public Label shillings;
    public Label cents;
    public CustomTextField duration_loaned;
    public Button stop_return;
    public Label sec_duration;
    public Label min_duration;
    public Label secs_duration;
    public Label fine_label;
    public Label returned_book;
    public Label after_time;
    public Label returned;
    public Label loaned_book_id;

    private ObservableList durations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //return_values();
        check();
        //myCombo(this.duration_loaned);
        lable_id.isFocused();

    }

    public void check() {
        try {

            ConnectivityClass connectionClass = new ConnectivityClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT * FROM onloan WHERE Student_id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lable_id.getText());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book_borrowed.setEditable(false);
                if_issued.setText("Cannot borrow");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*private void return_values(){
        ConnectivityClass connectivityClass = new ConnectivityClass();
        Connection connection = connectivityClass.getConnection();
        String sql = "SELECT Book_id,Duration FROM onloan WHERE Student_id =?";
        try {
            PreparedStatement ppstt = connection.prepareStatement(sql);
            ppstt.setString(1,lable_id.getText());
            ResultSet resultSet = ppstt.executeQuery();
            while (resultSet.next()){
                String book_id = resultSet.getString("Book_id");
                String duration = resultSet.getString("Duration");
                book_borrowed.setText(book_id);
                duration_loaned.setPromptText(duration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public void onreturn_stop(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to return?");
        alert.setTitle("Return");
        alert.setHeaderText(null);

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            java.util.Date date = new java.util.Date();
            Timestamp hechio = new Timestamp(date.getTime());
            ConnectivityClass connectionClass = new ConnectivityClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT Time_loaned,Duration,Book_id FROM onloan WHERE Student_id =?";
            try {
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, lable_id.getText());
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    loan_start.isDisabled();
                    String book = resultSet.getString("Book_id");
                    String time = resultSet.getString("Duration");
                    after_time.setText(time);
                    returned_book.setText(book);
                    Timestamp value = resultSet.getTimestamp("Time_loaned");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(hechio.getTime());
                    long milliseconds = hechio.getTime() - value.getTime();
                    int seconds = (int) (milliseconds / 1000);
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int second = (seconds % 3600) % 60;

                    shillings.setText(value.toString());
                    cents.setText(hechio.toString());


                    secs_duration.setText(seconds + "");
                    String LONG[] = {"LONG", "SHORT"};
                    String duration = resultSet.getString("Duration");
                    if (duration.equals(LONG[0])) {
                        int new_seconds = seconds - 10800;
                        //sec_duration.setText(new_seconds + "secs");
                        if (new_seconds > 0) {
                            double fine = new_seconds * 0.000011574;

                            fine_label.setText(fine + "");

                        }
                    } else if (duration.equals(LONG[1])) {
                        int new_second = seconds - 4200;
                        if (new_second > 0) {
                            //sec_duration.setText(new_second + "");
                            double fine_short = new_second * 0.000011574;
                            double rounded_fine = Math.round(fine_short * 1.0) / 1.0;
                            fine_label.setText(rounded_fine + "");
                        }
                    }

                }
                String new_query = "insert into fined (Book_id,Student_id,Duration,Time_loaned,Time_returned,tSPENT,Fine)values('" + returned_book.getText() + "','" + lable_id.getText() + "','" + after_time.getText() + "','" + shillings.getText() + "','" + cents.getText() + "','" + secs_duration.getText() + "','" + fine_label.getText() + "')";
                PreparedStatement prepare = connection.prepareStatement(new_query);
                prepare.executeUpdate();
                loaned_book_id.setText("Loaned book Id");
                returned.setText("returned!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            database_exchange();

        }
    }

        public void onstart_loan (ActionEvent actionEvent) {
            ConnectivityClass connectionClass = new ConnectivityClass();
            Connection connection = connectionClass.getConnection();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to loan " + book_borrowed.getText() + "?");
            alert.setTitle("Loan");
            alert.setHeaderText(null);

            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                java.util.Date date = new java.util.Date();
                Timestamp hechio = new Timestamp(date.getTime());
                //on_loan();
                LocalDate dateToday = LocalDate.now();
                LocalDateTime timeNow = LocalDateTime.now();

                String sql = "INSERT INTO onloan (Book_id, Student_id, Duration,Date_loaned,Time_loaned) VALUES('" + book_borrowed.getText() + "','" + lable_id.getText() + "','" + duration_loaned.getText() + "','" + dateToday + "','" + timeNow + "')";
                try {

                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.execute();
                    shillings.setText(timeNow.toString());
                } catch (SQLException e) {
                    if_issued.setText("Book not issued");
                    e.printStackTrace();
                }
            }

        }


            public void retrive_id (String users){
            lable_id.setText(users);
        }


        public void home_button (ActionEvent actionEvent){
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
        }

        //adding to combo box
       /* public void myCombo (ComboBox duration_loaned){

            durations.removeAll(durations);
            String two_duration[] = {"", ""};
            for (String Duration : two_duration) {
                durations.addAll(Duration);
            }
            Collections.sort(durations);
            duration_loaned.getItems().addAll(durations);
        }*/

        public void database_exchange () {
            ConnectivityClass connectivityClass = new ConnectivityClass();
            Connection connection = connectivityClass.getConnection();
            String sql = "insert into returned select Book_id,Student_id,Date_loaned from onloan where Student_id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, lable_id.getText());
                preparedStatement.executeUpdate();
                //while (resultSet.next()){
                String query = "delete from onloan where Student_id=?";
                PreparedStatement prepare = connection.prepareStatement(query);
                prepare.setString(1, lable_id.getText());
                prepare.executeUpdate();

                String query_overdue = "delete from overdue where Student_id=?";
                PreparedStatement preparest = connection.prepareStatement(query_overdue);
                preparest.setString(1, lable_id.getText());
                preparest.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void on_clear (ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to clear "+fine_label.getText()+"?");
            alert.setTitle("Return");
            alert.setHeaderText(null);

            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                java.util.Date date = new java.util.Date();
                Timestamp hechio = new Timestamp(date.getTime());
                fine_label.setText("00.00");
                ConnectivityClass connectivityClass = new ConnectivityClass();
                Connection connection = connectivityClass.getConnection();
                String new_query = "insert into fined (Book_id,Student_id,Duration,Time_loaned,Time_returned,tSPENT,Fine)values('" + returned_book.getText() + "','" + lable_id.getText() + "','" + after_time.getText() + "','" + shillings.getText() + "','" + cents.getText() + "','" + secs_duration.getText() + "','" + fine_label.getText() + "')";
                try {
                    PreparedStatement ppst = connection.prepareStatement(new_query);
                    ppst.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    public void combo_display(MouseEvent event) {
        ConnectivityClass connectivityClass = new ConnectivityClass();
        Connection connection = connectivityClass.getConnection();
        String query = "select Book_duration from books where Book_id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book_borrowed.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String due = resultSet.getString("Book_duration");
                duration_loaned.setText(due);
                duration_loaned.setEditable(false);
            }
    }
    catch (Exception e){
        e.printStackTrace();}
    }
}

