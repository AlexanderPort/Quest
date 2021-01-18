package com.example.quest;

import com.google.gson.JsonObject;

public class Action {
    String description;
    String consequences;
    String nextLocationId;

    public Action(String description, String consequences, String nextLocationId) {
        this.description = description;
        this.consequences = consequences;
        this.nextLocationId = nextLocationId;
    }

    public String getNextLocationId() {
        return nextLocationId;
    }

    public String getDescription() {
        return description;
    }

    public String getConsequences() {
        return consequences;
    }

    @Override
    public String toString() {
        return "Action{" +
                "description='" + description + '\'' +
                ", locationId=" + nextLocationId +
                '}';
    }

    public static Action importFromJSON(JsonObject jsonObject) {
        String description = jsonObject.get("description").getAsString();
        String consequences = jsonObject.get("consequences").getAsString();
        String nextLocationId = jsonObject.get("nextLocationId").getAsString();

        //description = description.substring(1, description.length() - 1);
        //consequences = consequences.substring(1, consequences.length() - 1);
        //nextLocationId = nextLocationId.substring(1, nextLocationId.length() - 1);
        return new Action(description, consequences, nextLocationId);
    }
}
