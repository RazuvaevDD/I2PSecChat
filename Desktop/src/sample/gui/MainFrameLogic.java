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

    protected List<Message> getMessagesList(Room room) {
        /**
         * Function that return messages for current room.
         * TODO: Maybe we should find out how to protect this method from unauthorized access to hidden rooms.
         *       Hint: you can check is current user are participant of room from parameter.
         * @param room current room as Room object.
         * @return List of Message object.
         */
        return null;
    }

    protected List<Room> getRoomsList(Account account) {
        /**
         * Function that returns list of rooms for current user.
         * @param account current user account as Account object.
         * @return List of Room object.
         */
        return null;
    }

    protected List<Account> getParticipantsList(Room room) {
        /**
         * Function that return participants of current room.
         * @param room: Room object.
         * @return List of Account objects.
         */
        return null;
    }

    protected List<Account> getContactsList() {
        /**
         * Function that return contacts of user.
         * @return List of Account objects.
         *TODO: So as you can see there is no parameter with User whose contacts we should get like in previous method.
         *      It means that we should find out how to get contacts personal for logged user.
         *      There is shouldn't be any ways to get contacts of another user.
         */
        return null;
    }

    protected String getPublicKey() {
        /**
         * Function that return Public Key of user.
         * @return public key as String.
         */
        return null;
    }

    protected String getDate() {
        /**
         * Function that generate current date in String format.
         * @return String with current date in dd.mm.yyyy format.
         */
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    protected void addNewMessage(String message) {
        /**
         * Method that add message to database.
         * @param message String with text of message.
         */
    }

    protected void addNewRoom(Room room) {
        /**
         * Method that add room to database.
         * @param room Room object.
         */
    }

    protected void addNewMember(Account account, Room room) {
        /**
         * Method that adding new member to current room.
         * @param account Account object that will be added to room.
         * @param room current room.
         */
    }

    @Deprecated
    protected void setRoomAvatarPath(String roomAvatarPath) {
        /**
         * Method that add room avatar path to the database.
         * @param roomAvatarPath String object.
         */
    }

    @Deprecated
    protected void setUserAvatarPath(String userAvatarPath) {
        /**
         * Method that add user avatar path to the database.
         * @param userAvatarPath String object.
         */
    }
}
