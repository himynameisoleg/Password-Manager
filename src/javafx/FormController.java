package javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FormController {
    private ArrayList<String> passwordList;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button signInButton;

    public FormController() {
        getPasswordsFromFile();
    }

    public void handleSignIn(ActionEvent event) {
        if (passwordField.getText().equals(passwordList.get(0))) {
            passwordLabel.setVisible(false);
            errorLabel.setVisible(false);
            signInButton.setVisible(false);
            passwordField.setVisible(false);
        } else {
            errorLabel.setText("Password is incorrect.");
            errorLabel.setTextFill(Color.RED);
        }
    }

    public void getPasswordsFromFile() {
        File file = new File("./src/javafx/passwords.txt");
        passwordList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                this.passwordList.add(line);
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
