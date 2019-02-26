package ullaf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Image image1 = new Image("/images/logo.jpg");
        Parent root = FXMLLoader.load(getClass().getResource("fxml/authentication.fxml"));
        primaryStage.getIcons().add(image1);
        primaryStage.setTitle("ULLAF");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
