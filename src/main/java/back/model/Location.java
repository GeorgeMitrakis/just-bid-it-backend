package back.model;

import java.util.List;

public class Location {
    private final String name;
    private List<Item> itemsInThisLocation;
    private List<Item> usersInThisLocation;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItemsInThisLocation() {
        return itemsInThisLocation;
    }

    public List<Item> getUsersInThisLocation() {
        return usersInThisLocation;
    }

    public void setItemsInThisLocation(List<Item> itemsInThisLocation) {
        this.itemsInThisLocation = itemsInThisLocation;
    }

    public void setUsersInThisLocation(List<Item> usersInThisLocation) {
        this.usersInThisLocation = usersInThisLocation;
    }
}
