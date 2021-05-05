# Password Keychain
### _"The Ultimate Password Manager"_

___

## What it is
The **Password Keychain** is a general purpose login credentials storage solution. Use if for storing passwords to your favorite website, logging into your friend's WiFi, or any login/password combination. 

## What it does
Upon fist launch, this application prompts you with a secure "Master Password". This password can be configured in `models/PasswordModel.java`.
You can then fill in each field to add the login credentials to the list. Once satisfied, you may click the "Save" button to save your passwords to disk. 
Saving will create a `passwords.json` containing an SHA-256 encrypted hex version of your master password as well as a list of all the login credentials entered. 
After saving, you may close and reload the application, log into the application, and your login credentials will be displayed. 

## Technical Implementation Details
The **JavaFX** library is used along with the **FXML** templating syntax.
Templates are placed in the `/views` directory. There is one `root.fxml` with a nested `layout.fxml` which contains most
of the template code. The templates are styled using custom CSS in the `/resources` directory. 

**File IO** is used to store and retrieve password from a `passwords.json` file found in `/src/javafx`. 
A `FileReader` class is used in the `getPasswordsFromFile()` method found under the `IOHelper` inner class in the
`LayoutController`. Also in the inner class we find the methods for writer and two helper methods for JSON conversion. 
`savePasswordsToFile()` uses a `BufferedWriter` class to overwrite the existing JSON file with the update passwords list as well as the
encrypted `MASTER_PASSWORD`.

The `buildJsonFromPasswords()` is a helper method for writing to file which 
creates a JSON object. It includes the encrypted
master password and an array of login credential objects.
Each object has the title, login and password field.

The `Collection` used to store password data was the `ArrayList`.
This data lives in `/models/PasswordModel.java` and is manipulated with accessor methods.
Several lambda functions are used for the purposes of displaying 
each login item on the `ListView`.

`SHA-256` is used to encrypt the master password before it is stored in the JSON file.
This is accomplished with the help of the `MessageDigest` class. The method `encryptPassword()`
is called when we save the project and handles the business logic for encryption and hashing.

**Main class**: the main class is found in `/src/javafx/Main.java`. This is the
entrypoint for the application.

## How to Run
1. Open solution in IntelliJ
2. Let all packages install (json-simple)
3. Run from entry point Main found in `src/javafx/Main.java`
