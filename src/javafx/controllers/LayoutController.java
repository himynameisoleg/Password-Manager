package javafx.controllers;

import javafx.scene.control.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.math.BigInteger;
import java.security.MessageDigest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LayoutController {
    private final String MASTER_PASSWORD = "CS420";
    private String masterPasswordMD5;
    private ArrayList<JSONObject> passwordList;

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
    @FXML
    private TextField addNameField;
    @FXML
    private TextField addLoginField;
    @FXML
    private TextField addPasswordField;
    @FXML
    private Button saveButton;
    @FXML
    private Label saveError;


    public LayoutController() {
        getPasswordsFromFile();
    }

    public void handleSignIn() {
        if (passwordField.getText().equals(MASTER_PASSWORD)) {
            addPasswordsInListView();
            loginGrid.setVisible(false);
            addNewGrid.setVisible(true);
        } else {
            errorLabel.setText("Password is incorrect. * (Hint its: CS420)");
            errorLabel.setTextFill(Color.RED);
        }
    }

    public void getPasswordsFromFile() {
        passwordList = new ArrayList<>();
        File file = new File("./src/javafx/passwords.json");
        JSONParser parser = new JSONParser();

        try (FileReader fr = new FileReader(file)) {
            Object parsed = parser.parse(fr);
            JSONObject passwordObj = (JSONObject) parsed;

            JSONArray passwords = (JSONArray) passwordObj.get("passwords");
            passwords.forEach(p -> passwordList.add((JSONObject) p));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
    }

    public String buildJsonFromPasswords() {
        JSONObject json = new JSONObject();
        JSONArray arr = new JSONArray();

        json.put("masterPassword", encryptPassword(MASTER_PASSWORD));
        passwordList.forEach(p -> arr.add(p));

        json.put("passwords", arr);

        return json.toJSONString();
    }

    public void savePasswordsToFile() {
        File file = new File("./src/javafx/passwords.json");

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(file, false))) {
            wr.write(buildJsonFromPasswords());
            saveButton.setDisable(true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void addPasswordsInListView() {
        passwordsListView.getItems().clear();

        passwordList.forEach(p -> {
            String website = (String) p.get("name");
            String username = (String) p.get("login");
            String password = (String) p.get("password");

            String item =
                    "name: " + website + "\n" + "login: " + username + "\n" + "password: " + password;

            passwordsListView.getItems().add(item);
        });
    }

    public void addNewPassword() {
        if (!formInputHasErrors()) {
            JSONObject item = new JSONObject();

            item.put("name", addNameField.getText());
            item.put("login", addLoginField.getText());
            item.put("password", addPasswordField.getText());

            passwordList.add(item);

            addNameField.clear();
            addLoginField.clear();
            addPasswordField.clear();

            addPasswordsInListView();
            saveButton.setDisable(false);
        }
    }

    public boolean formInputHasErrors() {
        boolean hasErrors = false;
        saveError.setText("");
        ArrayList<TextField> list = new ArrayList<>(List.of(addLoginField, addNameField, addPasswordField));
        for (TextField field : list) {
            if (field.getText() == null || field.getText().isEmpty()) {
                saveError.setText("Please confirm all fields.");
                saveError.setTextFill(Color.RED);
                hasErrors = true;
            }
        }
        return hasErrors;
    }

    public String encryptPassword(String password) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha.digest(password.getBytes());

            BigInteger hex = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(hex.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }
}
