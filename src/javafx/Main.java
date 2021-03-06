package javafx;

import javafx.application.Application;
import javafx.controllers.LayoutController;
import javafx.fxml.FXMLLoader;
import javafx.models.PasswordModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/root.fxml"));
        primaryStage.setTitle("Password Keychain");
        root.getStylesheets().add("./javafx/resources/styles.css");

        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
