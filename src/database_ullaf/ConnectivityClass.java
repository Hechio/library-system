package database_ullaf;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectivityClass {
    public Connection connection;
    public Connection getConnection(){
        String dbName="ullaf";
        String userName="root";
        String password="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
             connection = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connection");
            alert.setContentText("Cannot connect to database! Check your connection");
            alert.setHeaderText(null);
            alert.showAndWait();

            //e.printStackTrace();
        }
        return connection;
    }
}
