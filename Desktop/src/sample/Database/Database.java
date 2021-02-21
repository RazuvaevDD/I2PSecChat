package sample.Database;

import org.cybergarage.upnp.device.ST;
import org.sqlite.SQLiteConfig;

import java.io.*;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sample.Utils.Utils.readFile;

public class Database {

    private static Connection connect() {
        String dir = System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + dir + "\\src\\sample\\Database\\sql_database\\sec_chat_database.db";

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, config.toProperties());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void create_all_tables() {
        create_user_table();
        create_room_table();
        create_rooms_table();
        create_message_table();
        create_contacts_table();
    }

//    public void print_all_tables() {
//        System.out.println("Users:");
//        System.out.println("Rooms:");
//        print_rooms();
//        System.out.println("Users in rooms:");
//        print_rooms_users();
//        System.out.println("Messages");
//        print_messages();
//    }

    private static void create_user_table() {
        String create_user_table = "create table if not exists `user` (\n"
                + "`id` integer not null primary key autoincrement,\n"
                + "`name` char(50) not null unique,\n"
                + "`private_key` char(256) not null unique,\n"
                + "`public_key` char(256) not null unique,\n"
                + "`info` char(100),\n"
                + "`picture` blob\n"
                + ");";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_user_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void create_room_table() {
        String create_room_table = "create table if not exists `room` (\n"
                + "`id` integer not null primary key autoincrement,\n"
                + "`name` char(50) not null unique,\n"
                + "`info` char(100),\n"
                + "`delete_message_time` int,\n"
                + "`private_key` char(256) not null unique,\n"
                + "`hash` char(256) not null unique,\n"
                + "`picture` blob\n"
                + ");";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_room_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void create_rooms_table() {
        String create_rooms_table = "create table if not exists `rooms` (\n"
                + "`room_id` int not null,\n"
                + "`user_id` int not null,\n"
                + "unique (`room_id`, `user_id`),\n"
                + "constraint `fk_room_id` foreign key (`room_id`) references `room` (`id`) on delete cascade on update cascade,\n"
                + "constraint `fk_user_id`foreign key (`user_id`) references `user` (`id`) on delete cascade on update cascade\n"
                + ");";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_rooms_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void create_message_table() {
        String create_message_table = "create table if not exists `message` (\n"
                + "`id` integer not null primary key autoincrement,\n"
                + "`room_id` int,\n"
                + "`room_hash` char(256),\n"
                + "`sender_id` int,\n"
                + "`receiver_id` int,\n"
                + "`text` char(200),\n"
                + "`time` text,\n"
                + "foreign key (`room_id`) references `room` (`id`) on delete cascade on update cascade,\n"
                + "foreign key (`room_hash`) references `room` (`hash`) on delete cascade on update cascade,\n"
                + "foreign key (`sender_id`) references `user` (`id`) on delete cascade on update cascade,\n"
                + "foreign key (`receiver_id`) references `user` (`id`) on delete cascade on update cascade\n"
                + ");";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_message_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void create_contacts_table() {
        String create_contacts_table = "create table if not exists `contacts` (\n"
                + "`user_id` int not null,\n"
                + "`user_contact_id` int not null,\n"
                + "unique (`user_id`, `user_contact_id`),\n"
                + "foreign key (`user_id`) references `user` (`id`) on delete cascade on update cascade,\n"
                + "foreign key (`user_contact_id`) references `user` (`id`) on delete cascade on update cascade\n"
                + ");";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_contacts_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add_user(String name, String private_key, String public_key, String info, String file_name) {
        String add_user = "insert into user values (null, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(add_user)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, private_key);
            preparedStatement.setString(3, public_key);
            preparedStatement.setString(4, info);
            preparedStatement.setBytes(5, readFile(file_name));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("add_user db func");
            System.out.println(e.getMessage());
        }
    }

    public static void add_new_contact(int user_id, int new_contact_id) {
        String add_new_contact = "insert into contacts values (?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(add_new_contact)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, new_contact_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add_room(String name, int delete_message_time, String info, String private_key, String hash, String file_name) {
        String add_room = "insert into room values (null, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(add_room)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, info);
            preparedStatement.setInt(3, delete_message_time);
            preparedStatement.setString(4, private_key);
            preparedStatement.setString(5, hash);
            preparedStatement.setBytes(6, readFile(file_name));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void register_message(int room_id, String room_hash, int sender_id, int receiver_id, String text, String time) {
        String add_room = "insert into message values (null, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(add_room)) {
            if (receiver_id != 0) {
                preparedStatement.setInt(1, room_id);
                preparedStatement.setString(2, room_hash);
                preparedStatement.setInt(3, sender_id);
                preparedStatement.setInt(4, receiver_id);
                preparedStatement.setString(5, text);
                preparedStatement.setString(6, time);
                preparedStatement.executeUpdate();
            }
            else {
                preparedStatement.setInt(1, room_id);
                preparedStatement.setString(2, room_hash);
                preparedStatement.setInt(3, sender_id);
                preparedStatement.setNull(4, Types.INTEGER);
                preparedStatement.setString(5, text);
                preparedStatement.setString(6, time);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("register_message db func");
            System.out.println(e.getMessage());
        }
    }

    public static int get_id(String table_name, String name){
        int id = 0;
        String get_id = String.format("select id from %s where name = '%s'", table_name, name);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_id)) {

            while (result.next()) {
                id = result.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return id;
    }

    public static int getIdByHash(String table_name, String hash){
        int id = 0;
        String get_id = String.format("select id from %s where private_key = '%s'", table_name, hash);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_id)) {

            while (result.next()) {
                id = result.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return id;
    }

    public static void add_user_to_room(int room_id, int user_id) {
        String add_room = "insert into rooms values (?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(add_room)) {
            preparedStatement.setInt(1, room_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("add_user_to_room db func");
            System.out.println(e.getMessage());
        }
    }

    public static List<List<Object>> getAllUsers() {
        List<List<Object>> users = new ArrayList<>();;
        String get_table_values = MessageFormat.format("select * from {0}", "user");
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_table_values)) {

            List<Object> user;
            while (result.next()) {
                user = new ArrayList<>();
                user.add(result.getInt("id"));
                user.add(result.getString("name"));
                user.add(result.getString("private_key"));
                user.add(result.getString("public_key"));
                user.add(result.getString("info"));
                user.add(result.getBytes("picture"));
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("get_all_users db func");
            System.out.println(e.getMessage());
        } return  users;
    }

    public static List<List<Object>> getAllRooms() {
        List<List<Object>> rooms = new ArrayList<>();;
        String get_table_values = MessageFormat.format("select * from {0}", "room");
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_table_values)) {

            List<Object> room;
            while (result.next()) {
                room = new ArrayList<>();
                room.add(result.getInt("id"));
                room.add(result.getString("name"));
                room.add(result.getString("info"));
                room.add(result.getInt("delete_message_time"));
                room.add(result.getString("private_key"));
                room.add(result.getString("hash"));
                room.add(result.getBytes("picture"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return  rooms;
    }

    private static void print_messages() {
        String get_table_values = MessageFormat.format("select * from {0}", "message");
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_table_values)) {

            while (result.next()) {
                System.out.println(result.getInt("id") + "\t" +
                        result.getInt("room_id") + "\t" +
                        result.getInt("user_id") + "\t" +
                        result.getString("text") + "\t" +
                        result.getString("time"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void print_rooms_users() {
        String get_table_values = MessageFormat.format("select * from {0}", "rooms");
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_table_values)) {

            while (result.next()) {
                System.out.println(result.getInt("room_id") + "\t" +
                        result.getInt("user_id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void delete_user(int user_id) {
        String delete_user = "delete from user where id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(delete_user)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void delete_room(int room_id) {
        String delete_room = "delete from room where id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(delete_room)) {
            preparedStatement.setInt(1, room_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void change_user_info(String table_name, int id, String new_info) {
        String change_user_info = String.format("update %s set info = ? where id = ?", table_name);
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(change_user_info)) {
            preparedStatement.setString(1, new_info);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void drop_table(String table_name) {
        String drop_table = String.format("drop table %s", table_name);
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(drop_table)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void update_picture(String table_name, int user_id, String filename) {
        String update_picture = String.format("update %s set picture = ? where id = ?", table_name);
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(update_picture)) {
            preparedStatement.setBytes(1, readFile(filename));
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<List<Object>> getUsersInRoom(int room_id) {
        List<List<Object>> users_in_room = new ArrayList<>();
        String get_users_in_room = String.format("select rooms.user_id, user.name, user.private_key, user.public_key from user, rooms where user.id = rooms.user_id and rooms.room_id = %x;", room_id);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_users_in_room)) {

            List<Object> user;
            while (result.next()) {
                user = new ArrayList<>();
                user.add(result.getInt("user_id"));
                user.add(result.getString("name"));
                user.add(result.getString("private_key"));
                user.add(result.getString("public_key"));
                users_in_room.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users_in_room;
    }

    public static List<List<Object>> getUserRooms(int user_id) {
        List<List<Object>> user_rooms = new ArrayList<>();
        String get_user_rooms = String.format("select rooms.room_id, room.name, room.delete_message_time, room.private_key, room.hash from room, rooms where room.id = rooms.room_id and rooms.user_id = %x;", user_id);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_user_rooms)) {

            List<Object> room;
            while (result.next()) {
                room = new ArrayList<>();
                room.add(result.getInt("room_id"));
                room.add(result.getString("name"));
                room.add(result.getInt("delete_message_time"));
                room.add(result.getString("private_key"));
                room.add(result.getString("hash"));
                user_rooms.add(room);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user_rooms;
    }

    public static List<String> getUsersContacts(int user_id) {
        List<String> user_contacts = new ArrayList<>();
        String get_user_contacts = String.format("select user.name from user, contacts where user.id = contacts.user_id and contacts.user_id = %x;", user_id);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_user_contacts)) {

            while (result.next()) {
                user_contacts.add(result.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user_contacts;
    }

    public static List<List<Object>> getMessagesInRoom(int room_id) {
        List<List<Object>> messages_in_room = new ArrayList<>();
        String get_messages_in_room = String.format("select user.name, user.public_key, message.room_id, message.room_hash, message.sender_id, message.receiver_id, message.text, message.time from user, message where user.id = message.sender_id and message.room_id = %x;", room_id);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_messages_in_room)) {

            List<Object> message;
            while (result.next()) {
                message = new ArrayList<>();
                message.add(result.getString("name"));
                message.add(result.getString("public_key"));
                message.add(result.getInt("room_id"));
                message.add(result.getString("room_hash"));
                message.add(result.getInt("sender_id"));
                message.add(result.getInt("receiver_id"));
                message.add(result.getString("text"));
                message.add(result.getString("time"));
                messages_in_room.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages_in_room;
    }

    public static byte[] getPicture(String table_name, int id) {
        byte[] picture = null;

        String get_pic = String.format("SELECT picture FROM %s where id = %x;", table_name, id);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_pic)) {

            picture = result.getBytes("picture");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return picture;
    }

    public static void changeName(String table_name, int user_id, String new_name) {
        String update_name = String.format("update %s set name = ? where id = ?", table_name);
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(update_name)) {
            preparedStatement.setString(1, new_name);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUserFromRoom(int room_id, int user_id) {
        String delete_user_from_room = "delete from rooms where room_id = ? and user_id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(delete_user_from_room)) {
            preparedStatement.setInt(1, room_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getRoomIdbyHash(String hash) {
        int id = 0;
        String getRoomByHash = String.format("select id from room where hash = \"%s\";", hash);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(getRoomByHash)) {

            id = result.getInt("id");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return id;
    }
}
