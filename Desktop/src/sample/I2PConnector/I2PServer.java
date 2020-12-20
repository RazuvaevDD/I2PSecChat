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
        return !(messages.isEmpty());
    }

    public void run() {
        try{
            String dir = System.getProperty("user.dir");
            String keyFilePath = dir + "\\src\\sample\\I2PConnector\\key.dat";

            if(!(new File(keyFilePath).exists())) { //если ключ не существует
                System.out.println("Генерируем ключ...");
                PrivateKeyFile.main(new String[]{"-h", keyFilePath});//генерируем
            }else{
                System.out.println("Ключ найден!");
            }

            File file = new File(keyFilePath);
            InputStream inputStream = new FileInputStream(file);

            System.out.println("Подключаемся к I2P...");
            I2PSocketManager manager = I2PSocketManagerFactory.createManager(inputStream);


            I2PServerSocket serverSocket = manager.getServerSocket();
            I2PSession session = manager.getSession();
            //Print the base64 string, the regular string would look like garbage.
            myDestination = session.getMyDestination().toBase64();
            System.out.println("Destination = "+myDestination);


            //Create socket to handle clients
            I2PThread t = new I2PThread(new ClientHandler(serverSocket));
            t.setName("clienthandler1");
            t.setDaemon(false);
            System.out.println("Запуск сервера...");
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
            System.out.println("Сервер запущен!");
            while(true) {
                try {
                    I2PSocket sock = this.socket.accept();
                    if(sock != null) {
                        //Receive from clients
                        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                        String line = br.readLine();
                        if(line != null) {
                            String s[]; s = line.split("<<SYSTEM_X>>");
                            messages.add(new Message(new Account("From", s[1]), new Account("To", myDestination), s[2], TypeOfMessage.values()[Integer.parseInt(s[0])]));
                        }
                        sock.close();
                    }
                } catch (I2PException ex) {
                    System.out.println("General I2P exception!");
                } catch (ConnectException ex) {
                    System.out.println("Error connecting!");
                } catch (SocketTimeoutException ex) {
                    System.out.println("Timeout!");
                } catch (IOException ex) {
                    System.out.println("General read/write-exception!");
                }
            }
        }
    }
}
