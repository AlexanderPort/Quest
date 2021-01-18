package com.example.quest;

import android.content.Context;
import android.content.res.AssetManager;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Repository implements IRepository {
    private HashMap<String, Location> locations;
    private Context context;
    private String filename;

    public Repository(Context context, String filename) throws FileNotFoundException {
        this.context = context;
        this.filename = filename;
        locations = new HashMap<>();
        importFromXML(context, filename);
        //importFromJSON(filename);
    }
    private void importFromJSON(String filename) throws FileNotFoundException {
        InputStreamReader streamReader = null;
        InputStream inputStream = null;
        try{
            AssetManager assetManager = context.getAssets();
            inputStream = assetManager.open(filename);
            streamReader = new InputStreamReader(inputStream);
            JsonElement jsonElement = JsonParser.parseReader(streamReader);
            JsonObject jsonObjects = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObjects.getAsJsonArray("locations");
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                Location location = Location.importFromJSON(jsonObject);
                locations.put(location.getLocationId(), location);
                System.out.println(location);
            }
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void importFromXML(Context context, String filename) {
        InputStream inputStream = null;
        try{
            AssetManager assetManager = context.getAssets();
            inputStream = assetManager.open(filename);
            DocumentBuilder documentBuilder = DocumentBuilderFactory
                    .newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            NodeList chapters = document.getElementsByTagName("chapter");

            for (int i = 0; i < chapters.getLength(); i++) {
                Node chapter = chapters.item(i);
                Location location = Location.importFromXML(chapter);
                locations.put(location.getLocationId(), location);
            }

        }
        catch (IOException | ParserConfigurationException | SAXException exception){
            exception.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public Location getLocation(String locationId) {
        return locations.get(locationId);
    }


}
