package sample.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import sample.Config.ConfigParser;

public class Utils {

    public static String bytesToImagePath(byte[] imageBytes) {
        String currentDirectory = System.getProperty("user.dir");
        String resourcesPath = currentDirectory + "\\src\\sample\\gui\\resources\\";
        if (imageBytes == null) {
            return resourcesPath + "avatar.jpg";
        }
        ImageIcon imageIcon = new ImageIcon(imageBytes);
        Image img = imageIcon.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        String generatedString = getAlphaNumericString(10);
        String filePath = resourcesPath + generatedString + ".jpg";
        try {
            ImageIO.write(bi, "jpg", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return filePath;
    }

    public static boolean isPasswordCorrect(String password)
    {
        return ConfigParser.getPassword().equals(password);
    }

    private static String getAlphaNumericString(int n)
    {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

}
