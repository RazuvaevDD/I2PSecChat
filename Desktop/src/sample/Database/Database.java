package sample.Database;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.text.MessageFormat;

public class Database {

    private Connection connect() {
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
        return  conn;
    }

//    public void create_database(String fileName) {
//
//        String dir = System.getProperty("user.dir");
//         this.url = "jdbc:sqlite:" + dir + "\\src\\sample\\Database\\sql_database\\" + fileName;
//
//        SQLiteConfig config = new SQLiteConfig();
//        config.enforceForeignKeys(true);
//
//        try (Connection conn = DriverManager.getConnection(this.url, config.toProperties())) {
//            if (conn != null) {
//                DatabaseMetaData meta = conn.getMetaData();
//                Statement s = conn.createStatement(); s.executeUpdate("PRAGMA foreign_keys = ON");
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    };

    public void create_all_tables() {
        create_user_table();
        create_room_table();
        create_rooms_table();
        create_message_table();
    }

    public void print_all_tables() {
        System.out.println("Users:");
        print_users();
        System.out.println("Rooms:");
        print_rooms();
        System.out.println("Users in rooms:");
        print_rooms_users();
        System.out.println("Messages");
        print_messages();
    }

    private void create_user_table() {
        String create_user_table = "create table if not exists `user` (\n"
                + "`id` integer not null primary key autoincrement,\n"
                + "`name` char(50) not null,\n"
                + "`private_key` char(50) not null,\n"
                + "`info` char(100)\n"
                + ");";
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_user_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void create_room_table() {
        String create_room_table = "create table if not exists `room` (\n"
                + "`id` integer not null primary key autoincrement,\n"
                + "`name` char(50) not null,\n"
                + "`info` char(100),\n"
                + "`delete_message_time` int,\n"
                + "`aes_key` char(50)\n"
                + ");";
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_room_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void create_rooms_table() {
        String create_rooms_table = "create table if not exists `rooms` (\n"
                + "`room_id` int not null,\n"
                + "`user_id` int not null,\n"
                + "constraint `fk_room_id` foreign key (`room_id`) references `room` (`id`) on delete cascade on update cascade,\n"
                + "constraint `fk_user_id`foreign key (`user_id`) references `user` (`id`) on delete cascade on update cascade\n"
                + ");";
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_rooms_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void create_message_table() {
        String create_message_table = "create table if not exists `message` (\n"
                + "`id` integer not null primary key autoincrement,\n"
                + "`room_id` int not null,\n"
                + "`user_id` int not null,\n"
                + "`text` char(200) not null,\n"
                + "`time` text not null,\n"
                + "foreign key (`room_id`) references `room` (`id`) on delete cascade on update cascade,\n"
                + "foreign key (`user_id`) references `user` (`id`) on delete cascade on update cascade\n"
                + ");";
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement()) {
            statement.execute(create_message_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void add_user(String name, String private_key, String info) {
        String add_user = "insert into user values (null, ?, ?, ?)";
        try (Connection connection = this.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(add_user)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, private_key);
            preparedStatement.setString(3, info);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void add_room(String name, int delete_message_time, String info, String aes_key) {
        String add_room = "insert into room values (null, ?, ?, ?, ?)";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(add_room)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, info);
            preparedStatement.setInt(3, delete_message_time);
            preparedStatement.setString(4, aes_key);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void register_message(int room_id, int user_id, String text, String date) {
        String add_room = "insert into message values (null, ?, ?, ?, ?)";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(add_room)) {
            preparedStatement.setInt(1, room_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setString(3, text);
            preparedStatement.setString(4, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void add_user_to_room(int room_id, int user_id) {
        String add_room = "insert into rooms values (?, ?)";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(add_room)) {
            preparedStatement.setInt(1, room_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void print_users() {
        String get_table_values = MessageFormat.format("select * from {0}", "user");
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_table_values)) {

            while (result.next()) {
                System.out.println(result.getInt("id") + "\t" +
                        result.getString("name") + "\t" +
                        result.getString("private_key") + "\t" +
                        result.getString("info"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void print_rooms() {
        String get_table_values = MessageFormat.format("select * from {0}", "room");
        try (Connection connection = this.connect();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(get_table_values)) {

            while (result.next()) {
                System.out.println(result.getInt("id") + "\t" +
                        result.getString("name") + "\t" +
                        result.getString("info") + "\t" +
                        result.getInt("delete_message_time") + "\t" +
                        result.getString("aes_key"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void print_messages() {
        String get_table_values = MessageFormat.format("select * from {0}", "message");
        try (Connection connection = this.connect();
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

    private void print_rooms_users() {
        String get_table_values = MessageFormat.format("select * from {0}", "rooms");
        try (Connection connection = this.connect();
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

    public void delete_user(int user_id) {
        String delete_user = "delete from user where id = ?";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(delete_user)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete_room(int room_id) {
        String delete_room = "delete from room where id = ?";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(delete_room)) {
            preparedStatement.setInt(1, room_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void change_user_info(int user_id, String new_info) {
        String change_user_info = "update user set info = ? where id = ?";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(change_user_info)) {
            preparedStatement.setString(1, new_info);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void drop_table() {
        String drop_table = "drop table room";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(drop_table)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
