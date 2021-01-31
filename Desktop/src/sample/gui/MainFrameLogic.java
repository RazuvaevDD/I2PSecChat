package sample.gui;

import sample.Objects.Account;
import sample.Objects.Message;
import sample.Objects.Room;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This class realizing logic of interacting with GUI.
 * @author Rustam Almukhametov
 * @author Vladimir Neizhko
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

    private Room currentRoom;

    private void setCurrentUser(Account account) {
        /**
         * Setter method that sets current user.
         * TODO: ACHTUNG! Method is private for protect against changing user.
         *       I think it should be set only one time after log in.
         *       And maybe it's necessary to create new singleton object as User instead Account currentUser.
         * @param account Account of current user.
         */
        currentUser = account;
    }

    protected Account getCurrentUser() {
        /**
         * Getter function that returns current user as Account object.
         * @return current user as Account.
         */
        return currentUser;
    }

    protected void setCurrentRoom(Room room) {
        /**
         * Setter method that sets current room.
         * @param room current room as Room object.
         */
        currentRoom = room;
    }

    protected Room getCurrentRoom() {
        /**
         * Getter function that returns current room.
         * @return current room as Room.
         */
        return currentRoom;
    }

    protected List<Message> getMessagesList() {
        /**
         * Function that returns messages for current room.
         * TODO: Maybe we should find out how to protect this method from unauthorized access to hidden rooms.
         *       Hint: you can check is current user are participant of room from parameter.
         * @return List of Message object.
         */
        return null;
    }

    protected List<Room> getRoomsList() {
        /**
         * Function that returns list of rooms for current user.
         * @return List of Room object.
         */
        return null;
    }

    protected List<Account> getParticipantsList() {
        /**
         * Function that returns participants of current room.
         * @return List of Account objects.
         */
        return null;
    }

    protected List<Account> getContactsList() {
        /**
         * Function that returns contacts of user.
         * @return List of Account objects.
         *TODO: So as you can see there is no parameter with User whose contacts we should get like in previous method.
         *      It means that we should find out how to get contacts personal for logged user.
         *      There is shouldn't be any ways to get contacts of another user.
         */
        return null;
    }

    protected String getPublicKey() {
        /**
         * Function that returns Public Key of user.
         * @return public key as String.
         */
        return null;
    }

    protected String getDate() {
        /**
         * Function that generates current date in String format.
         * @return String with current date in dd.mm.yyyy format.
         */
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    protected void addNewMessage(String message) {
        /**
         * Method that adds messages to database.
         * @param message String with text of message.
         */
    }

    protected void addNewRoom(Room room) {
        /**
         * Method that adds room to database.
         * @param room Room object.
         */
    }

    protected void addNewMember(Account account) {
        /**
         * Method that adds new member to current room.
         * @param account Account object that will be added to room.
         */
    }

    @Deprecated
    protected void setRoomAvatarPath(String roomAvatarPath) {
        /**
         * Method that adds room avatar path to the database.
         * @param roomAvatarPath String object.
         */
    }

    @Deprecated
    protected void setUserAvatarPath(String userAvatarPath) {
        /**
         * Method that adds user avatar path to the database.
         * @param userAvatarPath String object.
         */
    }
}
