package ullaf.controller;
import animatefx.animation.Shake;
import database_ullaf.ConnectivityClass;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class authentication implements Initializable {


    public CustomTextField show_pass;
    public CustomPasswordField user_pass;
    public CheckBox check_pass;
    public Label connect_feedback;
    public Button sign_up;
    public Button staff_log;
    public AnchorPane anchor_auth;
    private ActionEvent event;
    public CustomTextField staff_id;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        show_pass.setManaged(false);
        show_pass.setVisible(false);
        show_pass.managedProperty().bind(check_pass.selectedProperty());
        show_pass.visibleProperty().bind(check_pass.selectedProperty());

        user_pass.managedProperty().bind(check_pass.selectedProperty().not());
        user_pass.visibleProperty().bind(check_pass.selectedProperty().not());

        show_pass.textProperty().bindBidirectional(user_pass.textProperty());
        sign_up.isFocused();



    }




    public void signup(MouseEvent mouseEvent) throws IOException {

        Parent change = FXMLLoader.load(getClass().getResource("../fxml/staffreg.fxml"));
        Scene change_scene = new Scene(change);
        Stage stage_app = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage_app.setScene(change_scene);
        stage_app.show();
    }


    public void login(ActionEvent actionEvent) {
        ConnectivityClass connectionClass=new ConnectivityClass();
        Connection connection= connectionClass.getConnection();

        try {
            Statement statement=connection.createStatement();
            String sql="SELECT staff_id,password FROM staff_registration WHERE staff_id ='"+staff_id.getText()+"' AND password ='"+user_pass.getText()+"';";
            ResultSet resultSet=statement.executeQuery(sql);

            if (resultSet.next()){


        FXMLLoader change = new  FXMLLoader(getClass().getResource("../fxml/ullafmain.fxml"));
                Parent root = null;
                try {
                    root = change.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        Stage stage_app = new Stage();
        stage_app.setScene(new Scene(root));
        stage_app.setResizable(false);
        stage_app.setMaximized(true);
        stage_app.show();
        stage_app.setOnCloseRequest(event1 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
        alert.setTitle("Close ULLAF");
        alert.setContentText("Do you want to exit");
        alert.showAndWait().filter(e->e!=ButtonType.OK).ifPresent(e->event1.consume());});
        Controller c = change.getController();
        c.retrive_id(staff_id.getText());
        entry enter = new entry();
        enter.close(anchor_auth);

           }else {
                connect_feedback.setText("Cannot log in check details");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    }



