package worldOfZuul;

import java.util.Set;
import java.util.HashMap;


public class Room 
{
    private Forest forest;
    private String description;
    private HashMap<String, Room> exits;

    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        this.forest = new Forest();
    }

    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return description;
    }

    public String getLongDescription(String setDescription)
    {
        return "You are in " + description + setDescription + ".\n" + getExitString();
    }

    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Forest getForest() {
        return this.forest;
    }


    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

