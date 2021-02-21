package sample.gui;

import javafx.scene.image.Image;
import sample.I2PConnector.I2PConnector;
import sample.Objects.Account;
import sample.Objects.Message;
import sample.Objects.Room;
import sample.Objects.TypeOfMessage;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sample.Database.Database;
import sample.Utils.Utils;

import javax.xml.crypto.Data;

/**
 * This class realizing logic of interacting with GUI.
 * @author Lev Averin
 **/

public class MainFrameLogic {

    private static MainFrameLogic uniqueInstance;

    private MainFrameLogic() {}

    public static synchronized MainFrameLogic getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MainFrameLogic();
        }
        return uniqueInstance;
    }

    private Account currentUser;

    private int currentUserId;

    private Room currentRoom;

    private int currentRoomId;

    public void setCurrentUser(Account account) {
        /**
         * Setter method that sets current user.
         * TODO: ACHTUNG! Method is private for protect against changing user.
         *       I think it should be set only one time after log in.
         *       And maybe it's necessary to create new singleton object as User instead Account currentUser.
         * @param account Account of current user.
         */
        currentUser = account;
        currentUserId = Database.get_id("user", account.name);
    }

    protected Account getCurrentUser() {
        /**
         * Getter function that returns current user as Account object.
         * @return current user as Account.
         */
        return currentUser;
    }

    protected int getCurrentUserId() {
        return currentUserId;
    }

    protected void setCurrentRoom(Room room) {
        /**
         * Setter method that sets current room.
         * @param room current room as Room object.
         */
        System.out.print("Will be switched to room " + room.getName());
        currentRoomId = Database.getRoomIdbyHash(room.getAESKey());
        currentRoom = room;
    }

    protected Room getCurrentRoom() {
        /**
         * Getter function that returns current room.
         * @return current room as Room.
         */
        return currentRoom;
    }

    protected int getCurrentRoomId() {
        return currentRoomId;
    }

    /**
     * Function that returns messages for current room.
     * TODO: Maybe we should find out how to protect this method from unauthorized access to hidden rooms.
     *       Hint: you can check is current user are participant of room from parameter.
     * @return List of Message object.
     */
    public List<Message> getMessagesList() {

        if (currentRoomId == 0) {
            List<Message> emptyList = new ArrayList<>();
            return emptyList;
        }

        List<List<Object>> messages = Database.getMessagesInRoom(currentRoomId);
        List<Message> messagesList = new ArrayList<>();
        List<List<Object>> allUsers = Database.getAllUsers();
        for (List<Object> message : messages) {
            String receiverName = null;
            String receiverDestination = null;
            if ((int)message.get(5) != 0) {
                for (List<Object> user : allUsers) {
                    if (message.get(5) == user.get(0)) {
                        receiverName = user.get(1).toString();
                        receiverDestination = user.get(3).toString();
                    }
                }
            }
            messagesList.add(new Message(new Account(message.get(0).toString(), message.get(1).toString()), new Account(receiverName, receiverDestination), message.get(6).toString(), TypeOfMessage.StringMessage, message.get(3).toString() ,message.get(7).toString()));
        }
        return messagesList;
    }

    public List<Room> getRoomsList() {
        /**
         * Function that returns list of rooms for current user.
         * @return List of Room object.
         */
        List<List<Object>> allRooms = Database.getAllRooms();
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < allRooms.size(); i++) {
            String filepath = "";
            try {
                filepath = allRooms.get(i).get(6).toString();
            }
            catch(NullPointerException e) {
                filepath = "";
            }
            roomList.add(new Room(allRooms.get(i).get(1).toString(), allRooms.get(i).get(2).toString(), (int)allRooms.get(i).get(3), allRooms.get(i).get(5).toString(), filepath));
            //System.out.println(i);
        }
        return roomList;
    }

    protected List<Account> getParticipantsList() {
        /**
         * Function that returns participants of current room.
         * @return List of Account objects.
         */
        if (currentRoomId == 0) {
            List<Account> emptyList = new ArrayList<>();
            return emptyList;
        }
        List<List<Object>> usersInRoom = Database.getUsersInRoom(currentRoomId);
        List<Account> participantsList = new ArrayList<>();
        for (List<Object> user: usersInRoom) {
            participantsList.add(new Account(user.get(1).toString(), user.get(3).toString()));
        }
        return participantsList;
    }

    public List<Account> getContactsList() {
        /**
         * Function that returns contacts of user.
         * @return List of Account objects.
         *TODO: So as you can see there is no parameter with User whose contacts we should get like in previous method.
         *      It means that we should find out how to get contacts personal for logged user.
         *      There is shouldn't be any ways to get contacts of another user.
         */
        if (currentUserId == 0) {
            List<Account> emptyList = new ArrayList<>();
            return emptyList;
        }
        List<List<Object>> allUsers = Database.getAllUsers();
        List<String> userContacts = Database.getUsersContacts(currentUserId);
        List<Account> contactsList = new ArrayList<>();
        String userPublicKey = "";
        for (String contact: userContacts) {
            for (List<Object> user: allUsers) {
                if (contact.equals(user.get(1).toString())) {
                    userPublicKey = user.get(3).toString();
                    break;
                }
            }
            contactsList.add(new Account(contact, userPublicKey));
        }
        return contactsList;
    }

    protected String getPublicKey() {
        /**
         * Function that returns Public Key of user.
         * @return public key as String.
         */
        return I2PConnector.getMyAccount().destination;
    }

    protected String getDate() {
        /**
         * Function that generates current date in String format.
         * @return String with current date in dd.mm.yyyy format.
         */
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy h:mm a");
        Date date = new Date();
        return formatter.format(date);
    }

    protected void addNewMessage(String message) {
        /**
         * Method that adds messages to database.
         * @param message String with text of message.
         */
        if (currentRoomId != 0) {
            Database.register_message(currentRoomId, currentRoom.getAESKey(), currentUserId, 0, message, getDate());
        }
    }

    public void addNewRoom(Room room) {
        /**
         * Method that adds room to database.
         * @param room Room object.
         */
        Database.add_room(room.getName(), room.getDeleteMessageTime(), room.getInfo(), room.getAESKey(), room.getAESKey(), room.getFilepath());
    }

    protected void addNewMember(Account account) {
        /**
         * Method that adds new member to current room.
         * @param account Account object that will be added to room.
         */
        if (currentRoomId != 0) {
            notifyUsersAboutNewMember();
            Database.add_user_to_room(currentRoomId, currentUserId);
        }
    }

    protected void setRoomAvatarPath(String path) {
        Database.update_picture("room", 1, path);
    }

    protected Image getRoomAvatar() {
        /**
         * Method that adds room avatar path to the database.
         * @param roomAvatarPath String object.
         */
        if (currentRoomId == 0) {
            return null;
        }
        String path = "";
        List<List<Object>> rooms = Database.getAllRooms();
        for (int i = 0; i < rooms.size(); i++) {
            if ((int)rooms.get(i).get(0) == currentRoomId){
                path = Utils.bytesToImagePath((byte[])rooms.get(i).get(6));
                break;
            }
        }
        File file = new File(path);
        Image roomAvatar = new Image(file.toURI().toString());
        return roomAvatar;
    }

    protected void setUserAvatarPath(String path) {
        if (currentUserId != 0) {
            Database.update_picture("user", currentUserId, path);
        }
    }

    protected void escapeFromRoom() {
        if (currentRoomId != 0) {
            Database.deleteUserFromRoom(currentRoomId, currentUserId);
            currentRoomId = 0;
            currentRoom = null;
            I2PConnector.sendMessage(new Message(currentUser, null, "User left chat", TypeOfMessage.StringMessage, currentRoom.getAESKey(), getDate()));
            Database.register_message(currentRoomId, currentRoom.getAESKey(), currentUserId, 0, "User left chat", getDate());
        }
    }

    protected void notifyUsersAboutNewMember() {
        if (currentRoomId != 0) {
            I2PConnector.sendMessage(new Message(currentUser, null, "New user has come", TypeOfMessage.StringMessage, currentRoom.getAESKey(), getDate()));
            Database.register_message(currentRoomId, currentRoom.getAESKey(), currentUserId, 0, "New user has come", getDate());
        }
    }

    protected Image getUserAvatar() {

        /**
         * Method that adds user avatar path to the database.
         * @param userAvatarPath String object.
         */

        if (currentUserId == 0)
        {
            return null;
        }

        String path = "";
        List<List<Object>> users = Database.getAllUsers();
        for(int i = 0; i < users.size(); i++) {
            if ((int)users.get(i).get(0) == currentUserId){
                path = Utils.bytesToImagePath((byte[])users.get(i).get(5));
                break;
            }
        }
        File file = new File(path);
        Image userAvatar = new Image(file.toURI().toString());
        return userAvatar;
    }
}
