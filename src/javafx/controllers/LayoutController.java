package javafx.controllers;

import javafx.models.PasswordModel;
import javafx.scene.control.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.math.BigInteger;
import java.security.MessageDigest;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LayoutController {
    PasswordModel passwordModel;
    IOHelper io;

    @FXML
    private GridPane loginGrid;
    @FXML
    private GridPane addNewGrid;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
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

    public class IOHelper {
        public void getPasswordsFromFile() {
            File file = new File("./src/javafx/passwords.json");
            JSONParser parser = new JSONParser();

            try (FileReader fr = new FileReader(file)) {
                Object parsed = parser.parse(fr);
                JSONObject passwordObj = (JSONObject) parsed;

                JSONArray passwords = (JSONArray) passwordObj.get("passwords");
                passwords.forEach(p -> passwordModel.getPasswordsList().add((JSONObject) p));


            } catch (ParseException pe) {
                System.out.println("Parsine exception: " + pe.getMessage());
            } catch (IOException ex) {
                System.out.println("IO Excepton" + ex.getMessage());
            }

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

        public String buildJsonFromPasswords() {
            JSONObject json = new JSONObject();
            JSONArray arr = new JSONArray();

            json.put("masterPassword", encryptPassword(passwordModel.getMASTER_PASSWORD()));
            passwordModel.getPasswordsList().forEach(p -> arr.add(p));

            json.put("passwords", arr);

            return json.toJSONString();
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

    public LayoutController() {
        io = new IOHelper();
        passwordModel = new PasswordModel();

        io.getPasswordsFromFile();
    }

    public void handleSignIn() {
        if (passwordField.getText().equals(passwordModel.getMASTER_PASSWORD())) {
            addPasswordsInListView();
            loginGrid.setVisible(false);
            addNewGrid.setVisible(true);
        } else {
            errorLabel.setText("Password is incorrect. * (Hint its: CS420)");
            errorLabel.setTextFill(Color.RED);
        }
    }

    public void addPasswordsInListView() {
        passwordsListView.getItems().clear();


        passwordModel.getPasswordsList().forEach(p -> {
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

            passwordModel.getPasswordsList().add(item);

            addNameField.clear();
            addLoginField.clear();
            addPasswordField.clear();

            addPasswordsInListView();
            saveButton.setDisable(false);
        }
    }

    public void saveFileJson() {
        io.savePasswordsToFile();
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


}
