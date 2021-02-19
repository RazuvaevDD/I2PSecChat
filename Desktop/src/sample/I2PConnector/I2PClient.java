package sample.I2PConnector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import net.i2p.I2PException;
import net.i2p.client.streaming.I2PSocket;
import net.i2p.client.streaming.I2PSocketManager;
import net.i2p.client.streaming.I2PSocketManagerFactory;
import net.i2p.data.DataFormatException;
import net.i2p.data.Destination;
import sample.Objects.*;

public class I2PClient {

    public static void SendMsg(Message msg){
        I2PSocketManager manager = I2PSocketManagerFactory.createManager();
        System.out.println("[INFO] I2PClient: Начало передачи сообщения клиентом...");

        String destinationString;

        destinationString = msg.to.destination;
        Destination destination;
        try {
            destination = new Destination(destinationString);
        } catch (DataFormatException ex) {
            System.err.println("[UNCRITICAL ERROR] I2PClient: Destination имеет неверный формат!");
            return;
        }
        I2PSocket socket;
        try {
            socket = manager.connect(destination);
        } catch (I2PException ex) {
            System.err.println("[UNCRITICAL ERROR] I2PClient: Произошло общее исключение I2P! Сообщение не отправлено.");
            return;
        } catch (ConnectException ex) {
            System.err.println("[UNCRITICAL ERROR] I2PClient: Не удалось подключиться! Сообщение не отправлено.");
            return;
        } catch (NoRouteToHostException ex) {
            System.err.println("[UNCRITICAL ERROR] I2PClient: Не удалось найти хост! Сообщение не отправлено.");
            return;
        } catch (InterruptedIOException ex) {
            System.err.println("[UNCRITICAL ERROR] I2PClient: Отправка/получение было прервано! Сообщение не отправлено.");
            return;
        }
        try {
            //Write to server
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(msg.type.ordinal()+"<<SYSTEM_X>>" +msg.from.destination
                    +"<<SYSTEM_X>>" + msg.message +"<<SYSTEM_X>>" + msg.hashOfRoom +"<<SYSTEM_X>>" + msg.time);
            //Flush to make sure everything got sent
            bw.flush();

            socket.close();
            System.out.println("[INFO] I2PClient: Сообщение отправлено!");
        } catch (IOException ex) {
            System.out.println("[UNCRITICAL ERROR] Ошибка при отправке сообщения. Сообщение не отправлено.");
        }
    }
}
