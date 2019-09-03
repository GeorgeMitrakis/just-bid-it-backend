package back.data;

import back.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDAO {

    List<Item> getItems(int userId, Limits limits);

    Optional<Item> getItemById(long id);

    void storeItem(Item item);

    //void storeItemCategories(long itemId);

    List<Item> searchItems(String searchTerm, String category);

//    void closeAuction(long itemId);

    void updateCurrentBid(long itemId, float amount);
}
