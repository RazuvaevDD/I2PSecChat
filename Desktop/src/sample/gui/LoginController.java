package sample.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private Button loginFrameSignInButton;

    @FXML
    private Button loginFrameHelpButton;

    @FXML
    private TextField loginFramePasswordField;

    @FXML
    private Text helpText;

    @FXML
    private VBox messagesVBox;

    private String passwordForCheck = "admin";

    private boolean isPasswordValid(String password) {
        if (password.trim().length() == 0) {
            System.out.println("Password field is empty");
            loginFramePasswordField.setText("");
            return false;
        } else if (password.equals(passwordForCheck)){
            System.out.println("Passwords equals");
            return true;
        } else {
            return false;
        }
    }

    @FXML
    void initialize() {
        loginFrameHelpButton.setOnAction(event -> {
            System.out.println("Button HELP was pressed");
            helpText.setVisible(true);
        });

        loginFrameSignInButton.setOnAction(event -> {
            System.out.println("Button SIGN IN was pressed");

            String password = loginFramePasswordField.getText();
            if (isPasswordValid(password)) {
                System.out.println("Here will open MainFrame");
                loginFrameSignInButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("fxml/MainFrame.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.setTitle("I2PSeсChat");
                stage.showAndWait();
            }
        });
    }

}
