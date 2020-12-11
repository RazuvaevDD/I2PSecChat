package sample.Encryptor;

public class LoginData
{
    public LoginData(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String getLogin()
    {
        return this.login;
    }

    public String getPassword()
    {
        return this.password;
    }

    private static String login;
    private static String password;
}
