package com.razuvaevdd.secchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Base64InputStream;
import android.widget.TextView;

import net.i2p.client.*;
import net.i2p.client.streaming.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text1);

        try{


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //String PrivateKeyBase64 = "GgjBDrQal27OoUNVuBb3W8g-sG8KN1mlR68sldBpgNSk84Wu4zAvMox~40nMOB2DSGuNJXbcKRadhIKmKKYiMp9yOf0PRrgcExt2uy4FCr1BHxtDe4HYVM-WEpDJ~HoMKH4eU3grJGhPxZw8pUGleZpTvePumN9175V4bomuOUzoPVqwogkCTTy0RHB3ERoMfLkFxbzfYGJKLtg-gwYv7Hmvz5l5nYGnahoLiCaSgFm8O3qWEZRHO-KQQuVus7IVwzQCblTvy2UWFUEz4y2gWWXDVLVUGzcaVN-xDTGPT5kezRV8KFse1vg4RZptzJ-grDqLE7aZrXXDaKn35iDrEz9g1IL4fGeC5iKkwUK~CfSnWDHwXyAIB23zdcAlTE6p0iAb-ebpkS95o2Sciag2oxMgnaNWlxT2phrmmRWy0~B1avYD3PW~5ZLGfXuymeztUdD2AIKjeuHT9Vd9SRPRrs2O46kSeuy-uudpkqxZyb3TFVmlLMSZXYM2jrW6yMBaAAAA";
        //Initialize application
        I2PSocketManager manager = I2PSocketManagerFactory.createManager();//new Base64InputStream(new ByteArrayInputStream(PrivateKeyBase64.getBytes()), Base64.NO_WRAP));

        I2PServerSocket serverSocket = manager.getServerSocket();

        I2PSession session = manager.getSession();
        //Print the base64 string, the regular string would look like garbage.

        textView.setText(//"MyKey:"+PrivateKeyBase64+
                " getPrivateKey:"+session.getPrivateKey().toBase64()+" getDecryptionKey:"+session.getDecryptionKey().toBase64()+
                " getMyDestination:"+session.getMyDestination().toBase64());
        я
        //The additional main method code comes here...
        }catch (Exception e){
            textView.setText(e.getMessage());
        }
    }
}
