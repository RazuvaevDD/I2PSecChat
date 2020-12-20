package sample.GUI_InfoShower;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class InfoShower {

    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    public static String bytesToImagePath(byte[] imageBytes) throws IOException {
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
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String filePath = resourcesPath + generatedString + ".jpg";
        ImageIO.write(bi, "jpg", new File(filePath));
        return filePath;
    }
}
