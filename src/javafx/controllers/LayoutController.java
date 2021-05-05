package javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

public class LayoutController extends Controller {
    private ArrayList<String> passwordList;

    @FXML
    private GridPane loginGrid;
    @FXML
    private GridPane addNewGrid;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button signInButton;
    @FXML
    private ListView<String> passwordsListView;

    public LayoutController() {
        getPasswordsFromFile();
    }

    public void handleSignIn(ActionEvent event) {
//        TODO MD5 hash password and validate
        if (passwordField.getText().equals(passwordList.get(0))) {
            addPasswordsInListView();
            loginGrid.setVisible(false);
            addNewGrid.setVisible(true);
        } else {
            errorLabel.setText("Password is incorrect.");
            errorLabel.setTextFill(Color.RED);
        }
    }

    public void getPasswordsFromFile() {
//        TODO grab from json
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

    public void addPasswordToFile() {
        File file = new File("./src/javafx/passwords.txt");

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(file, false))) {
            for (String l : passwordList) {
                wr.write(l);
                wr.newLine();
            }

        } catch (IOException ex ) {
            System.out.println(ex.getMessage());
        }

    }

    public void addPasswordsInListView() {
        passwordList.forEach(p -> {
           passwordsListView.getItems().add(p);
        });
    }
}
