package javafx.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PasswordModel {
    private String MASTER_PASSWORD ;
    private ArrayList<JSONObject> passwordsList;

    public PasswordModel () {
        MASTER_PASSWORD = "CS420";
        passwordsList = new ArrayList<>();
    }

    public String getMASTER_PASSWORD() {
        return MASTER_PASSWORD;
    }

    public ArrayList<JSONObject> getPasswordsList() {
        return passwordsList;
    }

    public void setPasswordsList(ArrayList<JSONObject> passwordsList) {
        this.passwordsList = passwordsList;
    }
}
