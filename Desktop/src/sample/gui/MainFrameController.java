package sample.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//import javax.swing.text.html.ImageView;
import javafx.scene.image.ImageView;
//import java.awt.*;
import javafx.scene.image.Image;
import sample.I2PConnector.I2PConnector;
import sample.Objects.Account;
import sample.Objects.Message;
import sample.Objects.Room;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
    private TextField roomNameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField keyTextField;

    @FXML
    private ImageView userAvatarImageView;

    @FXML
    private ImageView roomAvatarImageView;

    @FXML
    private VBox messagesVBox;

    @FXML
    private ListView<Label> roomVBox;

    @FXML
    private VBox participantsVBox;

    private File chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JPG", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = new Stage();
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * Method that changing avatar for current room.
     **/
    private void changeRoomAvatar() {
        File roomAvatar = chooseImage();
        if (roomAvatar != null) {
            System.out.println("Opening file..." + roomAvatar.getPath());
            MainFrameLogic.getInstance().setRoomAvatarPath(roomAvatar.getPath());
        }
    }

    /**
     * Method that changing avatar for user.
     */
    private void changeUserAvatar() {
        File userAvatar = chooseImage();
        if (userAvatar != null) {
            System.out.println("Opening file..." + userAvatar.getPath());
            MainFrameLogic.getInstance().setUserAvatarPath(userAvatar.getPath());
        }
    }

    /**
     * Method that sending message to current room.
     */
    private void sendMessage() {
        String message = messageTextField.getText();
        if (message != null && !message.equals("")) {
            MainFrameLogic.getInstance().addNewMessage(message);
            messageTextField.setText("");
            System.out.println("LOG EVENT: message has been sent");
        } else {
            System.out.println("LOG EVENT: message sending error");
        }
    }

    /**
     * Method copied public key to clipboard
     */
    private void getPublicKey() {
        StringSelection stringSelection = new StringSelection(MainFrameLogic.getInstance().getPublicKey());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /**
     * Method filling GUI message area in current room with list of messages.
     * @param messagesList: List of Message objects.
     */
    private void fillMessagesArea(List<Message> messagesList) {
        messagesVBox.getChildren().clear();
        for (Message message : messagesList) {
            Label label = new Label(message.time + ", " + message.from.name + ": " + message.message);
            messagesVBox.getChildren().add(label);
        }
    }

    /**
     * Method filling GUI rooms area in UI with list of rooms.
     * @param roomsList: List of Room objects.
     */
    private void fillRooms(List<Room> roomsList) {
        roomVBox.getItems().clear();
        for (Room room : roomsList) {
            Label label = new Label(room.getName());
            roomVBox.getItems().add(label);
        }
        roomVBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String nameOfRoom = roomVBox.getSelectionModel().getSelectedItem().getText();
                System.out.println("CLICKED on " + nameOfRoom);
                for (Room room : roomsList) {
                    if (room.getName().equals(nameOfRoom)) {
                        MainFrameLogic.getInstance().setCurrentRoom(room);
                    }
                }
            }
        });
    }

    @FXML public void handleMouseClick(MouseEvent arg0) {
        System.out.println("clicked on " + roomVBox.getSelectionModel().getSelectedItem());
    }

    /**
     * Method filling GUI participants area in current room with list of participants.
     * @param participantsList: List of Account objects.
     */
    private void fillParticipants(List<Account> participantsList) {
        participantsVBox.getChildren().clear();
        for (Account account : participantsList) {
            Label label = new Label(account.name);
            participantsVBox.getChildren().add(label);
        }
    }

    /**
     * Method filling Avatar area of current room.
     * @param roomAvatarImage: Avatar image.
     */
    private void fillRoomAvatar(Image roomAvatarImage) {
        if (roomAvatarImage != null) {
            this.roomAvatarImageView.setImage(roomAvatarImage);
        }
    }

    /**
     * Method filling name of current room.
     * @param roomName: String with name of room.
     */
    private void fillRoomName(String roomName) {
        this.roomName.setText(roomName);
    }

    /**
     * Method filling name of current room.
     * @param roomName: String with name of room.
     */
    private void fillUserName(String roomName) {
        this.Username.setText(roomName);
    }

    /**
     * Method filling date in GUI.
     * @param date: String with current date.
     */
    private void fillDate(String date) {
        timeText.setText(date);
    }

    void escapeCurrentRoom() {
        MainFrameLogic.getInstance().escapeFromRoom();
    }

    void addParticipant() {
        String partName = nameTextField.getText();
        String partKey = keyTextField.getText();

        if (partName != null && !partName.equals("")) {
            if (partKey != null && !partKey.equals("")) {
                Account account = new Account(partName, partKey);
                MainFrameLogic.getInstance().addNewMember(account);
            } else {
                nameTextField.promptTextProperty().setValue("Неправильный ключ");
            }
        } else {
            nameTextField.promptTextProperty().setValue("Неправильное имя");
        }
    }

    void createRoom() throws NoSuchAlgorithmException {
        String roomName = roomNameTextField.getText();
        if (roomName != null && !roomName.equals("")) {
            List<String> listOfNames = new ArrayList<>();

            for (Room room : MainFrameLogic.getInstance().getRoomsList()) {
                listOfNames.add(room.getName());
                //заменить на перебор всех имен и спавнивать с текстовым полем. Если хоть раз совпадет, то врейк
            }
            if (!listOfNames.contains(roomName)) {
                Room room = new Room(roomName, "", 999999999, "sample/gui/resources/gear.png");
                MainFrameLogic.getInstance().addNewRoom(room);
            } else {
                roomNameTextField.promptTextProperty().setValue("Такая комната уже есть");
            }
        } else {
            roomNameTextField.promptTextProperty().setValue("Неправильное имя");
        }
    }

    void fillUserAvatar(Image userAvatarImage) {
        if (userAvatarImage != null) {
            this.roomAvatarImageView.setImage(userAvatarImage);
        }
    }

    @FXML
    void update() {
        System.out.println("LOG EVENT: screen updated");

        fillDate(MainFrameLogic.getInstance().getDate());

        fillMessagesArea(MainFrameLogic.getInstance().getMessagesList());

        fillParticipants(MainFrameLogic.getInstance().getParticipantsList());

        fillRooms(MainFrameLogic.getInstance().getRoomsList());

        fillUserAvatar(MainFrameLogic.getInstance().getUserAvatar());

        fillRoomAvatar(MainFrameLogic.getInstance().getRoomAvatar());

        fillRoomName(MainFrameLogic.getInstance().getCurrentRoom().getName());

        fillUserName(MainFrameLogic.getInstance().getCurrentUser().name);
    }

    @FXML
    void initialize() {

        MainFrameLogic.getInstance().setCurrentUser(I2PConnector.getMyAccount());
        MainFrameLogic.getInstance().setCurrentRoom(MainFrameLogic.getInstance().getRoomsList().get(0));

        update();

        sendButton.setOnAction(event -> {
            System.out.println("LOG EVENT: Button SEND was pressed");
            sendMessage();
            update();
        });

        publicKeyButton.setOnAction(event -> {
            System.out.println("Button KEY was pressed");
            getPublicKey();
            update();
        });

        changeRoomAvatarButton.setOnAction(event -> {
            System.out.println("Button CHANGE ROOM AVATAR was pressed");
            changeRoomAvatar();
            update();
        });

        changeUserAvatarButton.setOnAction(event -> {
            System.out.println("Button CHANGE USER AVATAR was pressed");
            changeUserAvatar();
            update();
        });

        leaveRoomButton.setOnAction(event -> {
            System.out.println("Button LEAVE ROOM was pressed");
            escapeCurrentRoom();
            update();
        });

        addParticipantButton.setOnAction(event -> {
            System.out.println("Button ADD PARTICIPANT was pressed");
            addParticipant();
            update();
        });

        createRoomButton.setOnAction(event -> {
            System.out.println("Button CREATE ROOM was pressed");
            try {
                createRoom();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            update();
        });
    }
}
