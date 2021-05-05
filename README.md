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
TODO
section indicating classes, methods outlined in project requirements

## How to Run
1. Open solution in IntelliJ
2. Let all packages install
3. Entry point is in Main.java found in `src/javafx/Main.java`
