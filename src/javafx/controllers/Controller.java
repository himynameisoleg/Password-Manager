package javafx.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


public class Controller {
    @FXML
    private GridPane root;

    public Controller() {
        loginAnimation();
    }

    public void loginAnimation() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), root);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }

}
