package com.example.quest;

/*
{
  "locations": [
    {
      "locationId": "locationId 1",
      "description": "description 1",
      "actions": [
        {
          "nextLocationId": "nextLocationId 2",
          "description": "description 11",
          "consequences": "consequences 11"
        },
        {
          "nextLocationId": "nextLocationId 3",
          "description": "description 12",
          "consequences": "consequences 12"
        }
      ]
    },
    {
      "locationId": "locationId 2",
      "description": "description 2",
      "actions": [
        {
          "nextLocationId": "nextLocationId 1",
          "description": "description 21",
          "consequences": "consequences 21"
        },
        {
          "nextLocationId": "nextLocationId 3",
          "description": "description 22",
          "consequences": "consequences 22"
        }
      ]
    },
    {
      "locationId": "locationId 3",
      "description": "description 3",
      "actions": [
        {
          "nextLocationId": "nextLocationId 1",
          "description": "description 31",
          "consequences": "consequences 31"
        },
        {
          "nextLocationId": "nextLocationId 2",
          "description": "description 32",
          "consequences": "consequences 32"
        }
      ]
    }
  ]
}
 */

public interface IRepository {
    public Location getLocation(String locationId);
}
