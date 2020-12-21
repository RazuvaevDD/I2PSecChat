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
        Database.update_picture("room", 1, newRoomAvatar);
        String path = "";
        List<List<Object>> rooms = Database.getAllRooms();
        for(int i = 0; i < rooms.size(); i++) {
            if ((int)rooms.get(i).get(0) == 1){
                path = Utils.bytesToImagePath((byte[])rooms.get(i).get(5));
                break;
            }
        }

        File file = new File(path);
        Image roomAvatar = new Image(file.toURI().toString());
        roomAvatarImageView.setImage(roomAvatar);
    }

    private void changeUserAvatar(String newUserAvatar) {
        File file = new File(newUserAvatar);
        Image roomAvatar = new Image(file.toURI().toString());
        userAvatarImageView.setImage(roomAvatar);
    }

    private void sendMessage(String userMessage) {
        System.out.println("Button SEND was pressed");
        if (!userMessage.equals("")) {
            System.out.println("USER MESSAGE: " + userMessage);
            Database.register_message(1, Database.get_id("user", I2PConnector.getMyAccount().name), userMessage, Utils.getDate());
            //register_message(int room_id, int user_id, userMessage, utils.getDate())
            messageTextField.setText("");
            //Account levsAccount = new Account("Lev", "oyR5qzaIzmB3ZshG0HKU-kt-PvWuzd5~GAWystPHjQLulG68FWWHaoj7hjEPCxuRFFIohNvhrv09AzA3LI1qEshwwtblUgB4KHuotczooaOYb~T3w3R8lXHgYjVVZPtLV~RtqSFHPF7RGf1-GlfnDkRkiCTvfkIxWFskkzLL3WBMAq6c4m7zQJpgp6NqQ~Xxe8Rq6jnwKJM~Q6vZbXoQFtlz6INyJP9m1v--hZ8dez5LGiuASlItDhlmapJh6H3c5S-FssFlcM3VLGY7RL5pENENjtg08~eaEjrjZhG9ua3wRBTTrApDeIiOwGeZZwmGtYZLKv~5-eTUgJLbUoiucVly36J2it2GP9nhzg6taZNycYZ8xCS5gL49PFNT4UUfbclEOiS--phprFqY4tCTRvhraJU9nE6C6fupz6zWYtb8GkkSak4Mo3JTeKfSn5anpTG0hU1-T-bo5Fb6-wjGacUZnvS7Y9yQx1sUvncEoYXEIKotADfvbBRONSeyGhfVAQAvMToyMDoyMDEyMjA6Ojo4NTJjNzA3ZTMzODk5M2IxOmM1NzMyNGYxOTE2MDRiMTI=");
            //Account danyasAccount = new Account("Danya", "Z0BTAJNKFiONfmeaMifz6IhpV9WIZgZ~7Fke0tr~GqZtcYdx7HuF~st5XDQavJvpEDbX-FOLuFgHojMJFwHhfILCLwB4WWD1ixMEppZA5QKKgsVKXeJsK~k8fFtaAqgUWZhFVt~pNGsMfuALYJ5LF8s2Mp7xksoh06jKK01BoWgnz6MDYX96fRm4813f6829yS7JLUSeFbdoFJSqni5IynEz30wACgDsSc35Y4c60RURwdfb4XDjU68JDSXVn1ryQ5s~Ep~GKYvyf8wq6JD5LqeCa9YaTluuOo~1LWg9O5-FQg0ghCsFSh8NonAu8JprdQxqExttBSHA98L~2RPYwQ1l~rlddnNH9Gov7m1T56MEu6Ls3G24ikgSv182XB8uU3YsjqnwSmAAY2WBgl2-7ZmHNjn~TqT~sdh0l9dipM3Ls4E4X0U2lA5ffOW4mbtd8iPM4z0IaOSsCMQfi5zw5tj4bxesaaKTMtLtbrq98JsGz2VJZl1j2CiTHn6TgI6HAQAvMToyMDoyMDEyMjA6OjpmNmNjZmE5NDAwYjBkZGIzOjUyYTFiY2MzYmRmODMzMmE=");

            Account mySecondAccount = new Account("Me", "LZoOnzo4QKrES8tZVnZLk65lfGTFw0kE~WAea5NEWo-MXC2nAOYaybW3Vyj-sxp7u636rb8s3Yn7F13pd-j83V70TcT9cx01vsEScRnZrCDGbrX9KAy6o3f5uU-PHgXS0OcR66BNTj0O7JxT6zdC1zJNwTASCaMbK8TNUnqk8O7QJxArK~VbMfB-bgwmISPskBg4G8a9teujomtyw-m7weoUNUlnjvAoX7VJPw8UTIdEoZ3SrGVJggeO9su61eSok-317vUYQFZbNCAHw-Dy8XkRAqJnGpZ--AFQnf2cOst9rW5ixPxiJ0FdoZrRJK5plyN~s4GIJQt~yleAvKFzjFgW9ryOkGtrpJbmguDlgWCe1ccO7sGIir20kEsOS~xD6c-wJwV-vCAxtSbXsHt7gMyX~ih8kUL7-nBFZ-kV7pcfIdZidCwUuvQfg9ikQztz3rtVS58Dnt0wuW5Hn9lkwWORaZ4LOS2HSD6wpphSHqvulvovCjsPAJiUW3UPFYBDAQAvMToyMDoyMDEyMjE6OjpiYzc4MWFlYzBjYzNlMzAzOmE4ZDY5NjNkYzgzYWU2YzY=");

            I2PConnector.sendMessage(new Message(I2PConnector.getMyAccount(),
                    mySecondAccount,
                    userMessage,
                    TypeOfMessage.StringMessage));
        }
    }

    private void fillMessages() {
        messagesVBox.getChildren().clear();
        List<List<String>> messagesList = Database.getMessagesInRoom(1);

        for (int i = 0; i < messagesList.size(); i++) {
            List<String> currentMessage = messagesList.get(i);
            //TODO: add as message layout
            Label label = new Label(currentMessage.get(0) + ": " + currentMessage.get(1));
            label.setLayoutX(30);
            messagesVBox.getChildren().add(label);
        }
    }

    private void fillRooms() {
        List<List<Object>> roomsList = Database.getAllRooms();

        for (int i = 0; i < roomsList.size(); i++) {
            List<Object> currentRoom = roomsList.get(i);
            //TODO: add as button
            Label label = new Label(currentRoom.get(1).toString());
            roomVBox.getChildren().add(label);
        }
    }

    private void fillParticipants() {

        List<String> participantsList = Database.getUsersInRoom(1);

        for (int i = 0; i < participantsList.size(); i++) {
            Label label = new Label(participantsList.get(i));
            participantsVBox.getChildren().add(label);
        }
    }

    @FXML
    void initialize() {
        Username.setText(I2PConnector.getMyAccount().name);

        fillMessages();
        fillRooms();
        fillParticipants();

        timeText.setText(Utils.getDate());

        File file = new File("./src/sample/gui/resources/userAvatar.jpg");
        Image image = new Image(file.toURI().toString());
        userAvatarImageView.setImage(image);

        //avatar loading processing
        String path = "";
        List<List<Object>> rooms = Database.getAllRooms();
        for(int i = 0; i < rooms.size(); i++) {
            if ((int)rooms.get(i).get(0) == 1){
                path = Utils.bytesToImagePath((byte[])rooms.get(i).get(5));
                break;
            }
        }

        file = new File(path);
        Image roomAvatar = new Image(file.toURI().toString());
        roomAvatarImageView.setImage(roomAvatar);

        sendButton.setOnAction(event -> {
            sendMessage(messageTextField.getText());
            fillMessages();
        });

        publicKeyButton.setOnAction(event -> {
            System.out.println("Button KEY was pressed");
            String destKey = I2PConnector.getMyAccount().destination;
            StringSelection stringSelection = new StringSelection(destKey);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection,stringSelection);

        });

        changeRoomAvatarButton.setOnAction(event -> {
            System.out.println("Button CHANGE ROOM AVATAR was pressed");

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Avatar");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("JPG", "*.jpg");
            fileChooser.getExtensionFilters().add(extFilter);
            Stage stage = new Stage();
            File fileAva = fileChooser.showOpenDialog(stage);
            if (fileAva != null) {
                System.out.println("Opening file..." + fileAva.getPath());
                changeRoomAvatar(fileAva.getPath());
            }
        });

        changeUserAvatarButton.setOnAction(event -> {
            System.out.println("Button CHANGE USER AVATAR was pressed");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Avatar");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("JPG", "*.jpg");
            fileChooser.getExtensionFilters().add(extFilter);
            Stage stage = new Stage();
            File fileAva = fileChooser.showOpenDialog(stage);
            if (fileAva != null) {
                System.out.println("Opening file..." + fileAva.getPath());
                changeUserAvatar(fileAva.getPath());
            }
        });

        leaveRoomButton.setOnAction(event -> {
            System.out.println("Button LEAVE ROOM was pressed");
        });

    }

}
