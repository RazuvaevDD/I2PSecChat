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
        System.out.println("Начало передачи сообщения клиентом...");

        String destinationString;

        destinationString = msg.to.destination;
        Destination destination;
        try {
            destination = new Destination(destinationString);
        } catch (DataFormatException ex) {
            System.out.println("Destination string incorrectly formatted.");
            return;
        }
        I2PSocket socket;
        try {
            socket = manager.connect(destination);
        } catch (I2PException ex) {
            System.out.println("General I2P exception occurred!");
            return;
        } catch (ConnectException ex) {
            System.out.println("Failed to connect!");
            return;
        } catch (NoRouteToHostException ex) {
            System.out.println("Couldn't find host!");
            return;
        } catch (InterruptedIOException ex) {
            System.out.println("Sending/receiving was interrupted!");
            return;
        }
        try {
            //Write to server
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(msg.type.ordinal()+"<<SYSTEM_X>>" +msg.from.destination+"<<SYSTEM_X>>" + msg.message);
            //Flush to make sure everything got sent
            bw.flush();

            socket.close();
            System.out.println("Сообщение отправлено!");
        } catch (IOException ex) {
            System.out.println("Error occurred while sending/receiving!");
        }
    }
}
