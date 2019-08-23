package back.data;

import back.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDAO {

    List<Item> getItems(int userId, Limits limits);

    Optional<Item> getById(long id);

    void storeItem(Item item);

    //void storeItemCategories(long itemId);
}
