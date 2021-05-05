package javafx.controllers;

import javafx.scene.control.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.security.MessageDigest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
    private TextField addWebsiteField;
    @FXML
    private TextField addUsernameField;
    @FXML
    private TextField addPasswordField;


    public LayoutController() {
        getPasswordsFromFile();
    }

    public void handleSignIn() {
        if (passwordField.getText().equals(MASTER_PASSWORD)) {
            addPasswordsInListView();
            loginGrid.setVisible(false);
            addNewGrid.setVisible(true);
        } else {
            errorLabel.setText("Password is incorrect.");
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

        json.put("masterPassword", encryptPassowrd(MASTER_PASSWORD));
        passwordList.forEach(p -> arr.add(p));

        json.put("passwords", arr);

        return json.toJSONString();
    }

    public void savePasswordsToFile() {
        File file = new File("./src/javafx/passwords.json");

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(file, false))) {
            wr.write(buildJsonFromPasswords());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void addPasswordsInListView() {
        passwordsListView.getItems().clear();

        passwordList.forEach(p -> {
            String website = (String) p.get("website");
            String username = (String) p.get("username");
            String password = (String) p.get("password");

            String item =
                    "site: " + website + "\n" + "username: " + username + "\n" + "password: " + password;

            passwordsListView.getItems().add(item);
        });
    }

    public void addNewPassword() {
        if (!formInputHasErrors()) {
            JSONObject item = new JSONObject();

            item.put("website", addWebsiteField.getText());
            item.put("username", addUsernameField.getText());
            item.put("password", addPasswordField.getText());

            passwordList.add(item);

            addWebsiteField.clear();
            addUsernameField.clear();
            addPasswordField.clear();

            addPasswordsInListView();
        }
    }

    public boolean formInputHasErrors() {
//        TODO displauy error if any field is empty
        return false;
    }

    public String encryptPassowrd(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] message = md5.digest(password.getBytes());
            String hash = message.toString();
            System.out.println(hash);
            return hash;
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }
}
