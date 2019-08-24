package back.model;

import java.util.List;

public class Category {
    private final long id;
    private final String name;
    private List<Item> itemsInThisCategory;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
        this.itemsInThisCategory = null;
    }

    public Category(long id, String name, List<Item> itemsInThisCategory) {
        this.id = id;
        this.name = name;
        this.itemsInThisCategory = itemsInThisCategory;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItemsInThisCategory() {
        return itemsInThisCategory;
    }

    public void setItemsInThisCategory(List<Item> itemsInThisCategory) {
        this.itemsInThisCategory = itemsInThisCategory;
    }
}
