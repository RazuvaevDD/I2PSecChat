package sample.Objects;

import sample.Encryptor.Encryptor;

import java.security.NoSuchAlgorithmException;

public class Room {

    public Room(String name, String info, int deleteMessageTime, String AES_key, String filepath) {
        this.name = name;
        this.info = info;
        this.deleteMessageTime = deleteMessageTime;
        this.AES_key = AES_key;
        this.filepath = filepath;
    }

    public Room(String name, String info, int deleteMessageTime, String filepath) throws NoSuchAlgorithmException {
        this.name = name;
        this.info = info;
        this.deleteMessageTime = deleteMessageTime;
        this.AES_key = Encryptor.generateKey(name);
        this.filepath = filepath;
    }
    public String getName()
    {
        return this.name;
    }
    public String getInfo()
    {
        return this.info;
    }
    public int getDeleteMessageTime()
    {
        return this.deleteMessageTime;
    }
    public String getAESKey()
    {
        return this.AES_key;
    }

    public String getFilepath()
    {
        return this.filepath;
    }

    private String name;
    private String info;
    private int deleteMessageTime;
    private String AES_key;
    private String filepath;
}
