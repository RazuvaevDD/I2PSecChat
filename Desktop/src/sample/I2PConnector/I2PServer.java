package sample.I2PConnector;

import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import net.i2p.I2PException;
import net.i2p.client.streaming.I2PSocket;
import net.i2p.data.PrivateKeyFile;
import net.i2p.util.I2PThread;

import net.i2p.client.I2PSession;
import net.i2p.client.streaming.I2PServerSocket;
import net.i2p.client.streaming.I2PSocketManager;
import net.i2p.client.streaming.I2PSocketManagerFactory;
import sample.Objects.Account;
import sample.Objects.Message;
import sample.Objects.TypeOfMessage;
/**
 * Этот класс является сервером API.
 * @author Razuvaev Daniil
 **/
public class I2PServer extends Thread{

    private static String myDestination = "";
    private static ArrayList<Message> messages = new ArrayList<>();

    public static String getMyDestination() {
        return myDestination;
    }

    public static ArrayList<Message> getNewMessages(){
        ArrayList<Message> m = new ArrayList<>(messages);
        messages.clear();
        return m;
    }

    public static boolean haveNewMessages(){
        System.out.println("[INFO] I2PServer: Проверка наличия сообщений...");
        return !(messages.isEmpty());
    }

    public void run() {
        try{
            String dir = System.getProperty("user.dir");
            String keyFilePath = dir + "\\src\\sample\\I2PConnector\\key.dat";

            if(!(new File(keyFilePath).exists())) { //если ключ не существует
                System.out.println("[INFO] I2PServer: Генерируем ключ...");
                PrivateKeyFile.main(new String[]{"-h", keyFilePath});//генерируем
            }else{
                System.out.println("[INFO] I2PServer: Ключ найден!");
            }

            File file = new File(keyFilePath);
            InputStream inputStream = new FileInputStream(file);

            System.out.println("[INFO] I2PServer: Подключаемся к I2P...");
            I2PSocketManager manager = I2PSocketManagerFactory.createManager(inputStream);

            I2PServerSocket serverSocket = null;
            try{
                serverSocket = manager.getServerSocket();
            }catch(Exception e){
                System.err.println("[CRITICAL ERROR] I2PServer: Не удалось подключиться к I2P сети.\n" +
                                   "                 Проверьте статус I2P сети по адресу http://localhost:7657/home");
                System.exit(0);
            }

            I2PSession session = manager.getSession();
            //Print the base64 string, the regular string would look like garbage.
            myDestination = session.getMyDestination().toBase64();
            System.out.println("[INFO] I2PServer: I2P адрес выделен!");
            //Create socket to handle clients
            I2PThread t = new I2PThread(new ClientHandler(serverSocket));
            t.setName("clienthandler1");
            t.setDaemon(false);
            System.out.println("[INFO] I2PServer: Запуск сервера...");
            t.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {

        private I2PServerSocket socket;

        public ClientHandler(I2PServerSocket socket) {
            this.socket = socket;
        }

        public void run() {
            System.out.println("[INFO] I2PServer: Сервер запущен!");
            while(true) {
                try {
                    I2PSocket sock = this.socket.accept();
                    if(sock != null) {
                        //Receive from clients
                        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                        String line = br.readLine();
                        if(line != null) {
                            String s[]; s = line.split("<<SYSTEM_X>>");
                            messages.add(new Message(
                                    new Account("From", s[1]),
                                    new Account("To", myDestination),
                                    s[2],
                                    TypeOfMessage.values()[Integer.parseInt(s[0])],
                                    s[3],
                                    s[4]
                                    )
                            );
                        }
                        sock.close();
                    }
                } catch (I2PException ex) {
                    System.out.println("[UNCRITICAL ERROR] I2PServer: Общее исключение I2P!");
                } catch (ConnectException ex) {
                    System.out.println("[UNCRITICAL ERROR] I2PServer: Ошибка подключения!");
                } catch (SocketTimeoutException ex) {
                    System.out.println("[UNCRITICAL ERROR] I2PServer: Timeout!");
                } catch (IOException ex) {
                    System.out.println("[UNCRITICAL ERROR] I2PServer: Общее исключение чтения/записи!");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("[UNCRITICAL ERROR] I2PServer: Получено сообщение неверного формата. " +
                            "Сообщение было проигнорировано.");
                }
            }
        }
    }
}
