package sample.gui;

import com.sun.corba.se.impl.orb.DataCollectorBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
//import javax.swing.text.html.ImageView;
import javafx.scene.image.ImageView;
//import java.awt.*;
import javafx.scene.image.Image;
import sample.Database.Database;
import sample.I2PConnector.I2PConnector;
import sample.Objects.Account;
import sample.Objects.Message;
import sample.Objects.Room;
import sample.Objects.TypeOfMessage;
import sample.Utils.Utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

/**
 * This class realizing controller for GUI of main frame.
 * @author Vladimir Neizhko
 **/

public class MainFrameController {

    @FXML
    private Label timeText;

    @FXML
    private Text Username;

    @FXML
    private Text roomName;

    @FXML
    private Button sendButton;

    @FXML
    private Button publicKeyButton;

    @FXML
    private Button changeRoomAvatarButton;

    @FXML
    private Button changeUserAvatarButton;

    @FXML
    private Button leaveRoomButton;

    @FXML
    private Button addParticipantButton;

    @FXML
    private Button createRoomButton;

    @FXML
    private TextField messageTextField;

    @FXML
    private ImageView userAvatarImageView;

    @FXML
    private ImageView roomAvatarImageView;

    @FXML
    private VBox messagesVBox;

    @FXML
    private VBox roomVBox;

    @FXML
    private VBox participantsVBox;

    private void changeRoomAvatar(String newRoomAvatar) {
        /**
         * Method that changing avatar for current room.
         * @param newRoomAvatar: String with path to avatar in local file system.
         **/
    }

    private void changeUserAvatar(String newUserAvatar) {
        /**
         * Method that changing avatar for user.
         * @param newUserAvatar: String with path to avatar in local file system.
         */
    }

    private void sendMessage(String userMessage) {
        /**
         * Method that sending message to current room.
         * @param userMessage: String with message.
         */
    }

    private void fillMessagesArea(List<Message> messagesList) {
        /**
         * Method filling GUI message area in current room with list of messages.
         * @param messagesList: List of Message objects.
         */
    }

    private void fillRooms(List<Room> roomsList) {
        /**
         * Method filling GUI rooms area in UI with list of rooms.
         * @param roomsList: List of Room objects.
         */
    }

    private void fillParticipants(List<Account> participantsList) {
        /**
         * Method filling GUI participants area in current room with list of participants.
         * @param participantsList: List of Account objects.
         */
    }

    private void fillRoomAvatar(String roomAvatarPath) {
        /**
         * Method filling Avatar area of current room.
         * @param roomAvatarPath: String with path to avatar in local file system.
         */
    }

    private void fillRoomName(String roomName) {
        /**
         * Method filling name of current room.
         * @param roomName: String with name of room.
         */
    }

    void fillDate(String date) {
        /**
         * Method filling date in GUI.
         * @param date: String with current date.
         */

        timeText.setText(date);
    }

    @FXML
    void initialize() {

        fillDate(MainFrameLogic.getInstance().getDate());

        sendButton.setOnAction(event -> {
            System.out.println("Button SEND was pressed");
        });

        publicKeyButton.setOnAction(event -> {
            System.out.println("Button KEY was pressed");
        });

        changeRoomAvatarButton.setOnAction(event -> {
            System.out.println("Button CHANGE ROOM AVATAR was pressed");
        });

        changeUserAvatarButton.setOnAction(event -> {
            System.out.println("Button CHANGE USER AVATAR was pressed");
        });

        leaveRoomButton.setOnAction(event -> {
            System.out.println("Button LEAVE ROOM was pressed");
        });

        addParticipantButton.setOnAction(event -> {
            System.out.println("Button ADD PARTICIPANT was pressed");
        });

        createRoomButton.setOnAction(event -> {
            System.out.println("Button CREATE ROOM was pressed");
        });
    }
}
