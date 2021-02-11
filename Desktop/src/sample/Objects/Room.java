package sample.Objects;

public class Room {

    public Room(int id, String name, String info, int deleteMessageTime, String AES_key, String filepath) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.deleteMessageTime = deleteMessageTime;
        this.AES_key = AES_key;
        this.filepath = filepath;
    }
    public int getId()
    {
        return this.id;
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

    private int id;
    private String name;
    private String info;
    private int deleteMessageTime;
    private String AES_key;
    private String filepath;
}
