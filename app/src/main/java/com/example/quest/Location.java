package com.example.quest;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Location {
    private String locationId;
    private String description;
    ArrayList<Action> actions;
    public Location(String locationId, String description, ArrayList<Action> actions) {
        this.locationId = locationId;
        this.description = description;
        this.actions = actions;
    }
    public static Location importFromJSON(JsonObject jsonObject) {
        ArrayList<Action> actions = new ArrayList<>();
        String locationId = jsonObject.get("locationId").getAsString();
        String description = jsonObject.get("description").getAsString();
        JsonArray jsonArray = jsonObject.getAsJsonArray("actions");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject action = jsonArray.get(i).getAsJsonObject();
            actions.add(Action.importFromJSON(action));
            System.out.println(actions.get(i));
        }
        return new Location(locationId, description, actions);
    }
    public static Location importFromXML(Node node) {
        ArrayList<Action> actions = new ArrayList<>();
        StringBuilder chapterDescription = new StringBuilder();
        String chapterId = node.getAttributes().getNamedItem("id").getNodeValue();
        NodeList messages = ((Element)node).getElementsByTagName("message");
        NodeList choices = ((Element)node).getElementsByTagName("choice");

        for (int i = 0; i < messages.getLength(); i++) {
            Node message = messages.item(i);
            String description = message.getTextContent();
            description = description.replace("[", "");
            description = description.replace("]", "");
            NamedNodeMap attributes = message.getAttributes();
            String messageId = attributes.getNamedItem("id").getNodeValue();
            String roleId = attributes.getNamedItem("roleId").getNodeValue();
            chapterDescription.append(description).append(". ");
        }
        for (int i = 0; i < choices.getLength(); i++) {
            Node choice = choices.item(i);
            String description = "";
            NodeList nodeList = choice.getChildNodes();
            for (int j = 0; j < nodeList.getLength(); j++) {
                description = nodeList.item(0).getTextContent();
            }
            if (description.equals("") || description.equals("Автопереход.")) {
                description = "Дальше...";
            }
            NamedNodeMap attributes = choice.getAttributes();
            String choiceId = attributes.getNamedItem("id").getNodeValue();
            Node targetChapter = ((Element)choice).
                    getElementsByTagName("targetChapter").item(0);
            String nextChapterId = "END";
            if (targetChapter != null) {
                nextChapterId = targetChapter.getTextContent();
            }
            actions.add(new Action(description, "", nextChapterId));

        }
        if (actions.size() == 0) {
            actions.add(new Action("В начало", "", "1"));
        }
        return new Location(chapterId, chapterDescription.toString(), actions);
    }

    public String getLocationId() {
        return locationId;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId='" + locationId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }
}
