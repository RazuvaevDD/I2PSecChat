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

import javax.imageio.ImageIO;
//import javax.swing.text.html.ImageView;
import javafx.scene.image.ImageView;
import java.awt.*;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;

public class MainFrameController {

    @FXML
    private Button sendButton;

    @FXML
    private Button publicKeyButton;

    @FXML
    private Button changeRoomAvatarButton;

    @FXML
    private Button leaveRoomButton;

    @FXML
    private TextField messageTextField;

    @FXML
    private ImageView userAvatarImageView;

    @FXML
    private ImageView roomAvatarImageView;

    @FXML
    void initialize() {
        //load user Avatar

        File file = new File("./src/sample/gui/resources/userAvatar.jpg");
        Image image = new Image(file.toURI().toString());
        userAvatarImageView.setImage(image);

        file = new File("./src/sample/gui/resources/roomAvatar.jpg");
        image = new Image(file.toURI().toString());
        roomAvatarImageView.setImage(image);

        sendButton.setOnAction(event -> {
            System.out.println("Button SEND was pressed");
            String userMessage = messageTextField.getText();
            if (!userMessage.equals("")) {
                System.out.println("USER MESSAGE: " + userMessage);
                messageTextField.setText("");
            }
        });

        publicKeyButton.setOnAction(event -> {
            System.out.println("Button PUBLIC KEY was pressed");
        });

        changeRoomAvatarButton.setOnAction(event -> {
            System.out.println("Button CHANGE AVATAR was pressed");
        });

        leaveRoomButton.setOnAction(event -> {
            System.out.println("Button LEAVE ROOM was pressed");
        });

    }

}
