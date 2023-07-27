package org.gestore.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class IO
{
    private static final String HOME_PATH = System.getProperty("user.home");

    private static final Map<String, Image> cacheImg = new HashMap<>();

    public static Image loadImage(String resourcePath)
    {
        Image img = null;

        if(cacheImg.containsKey(resourcePath))
        {
            img = cacheImg.get(resourcePath);

            if(img != null)
                return img;
            else
                cacheImg.remove(resourcePath);
        }

        URL url = IO.class.getResource(resourcePath);

        if(url == null)
            return null;

        try
        {
            img = ImageIO.read(url);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(img != null)
            cacheImg.put(resourcePath, img);

        return img;
    }

    public static void writeJson(String fileName, String json) throws Exception
    {
        File directory = new File(HOME_PATH + "/.tanadeigoblin");

        directory.mkdirs();

        File jsonFile = new File(directory.getPath() + "/" + fileName);

        BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));
        writer.write(json);
        writer.close();
    }

    public static String readJson(String fileName) throws Exception
    {
        File directory = new File(HOME_PATH + "/.tanadeigoblin");

        if(!directory.exists() || !directory.isDirectory())
            return null;

        File jsonFile = new File(directory.getPath() + "/" + fileName);

        if(!jsonFile.isFile())
            return null;

        BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
        StringBuilder json = new StringBuilder();
        String row;
        while((row = reader.readLine()) != null)
        {
            json.append(row);
        }
        reader.close();

        return json.toString();
    }
}