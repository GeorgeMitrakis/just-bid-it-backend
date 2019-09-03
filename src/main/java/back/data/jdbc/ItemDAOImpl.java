package back.data.jdbc;

import back.data.ItemDAO;
import back.data.Limits;
import back.data.ItemDAO;
import back.model.Item;

import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {

    private final DataAccess dataAccess;

    public ItemDAOImpl(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    @Override
    public List<Item> getItems(int userId, Limits limits){
        List<Item> items = dataAccess.getItems(userId, limits.getStart(), limits.getCount());
        limits.setTotal(items.size());

        return items;
    }

    @Override
    public Optional<Item> getItemById(long id) {
        return dataAccess.getItemById(id);
    }

    @Override
    public void storeItem(Item item){
        dataAccess.storeItem(item);
    }

//    @Override
//    public void storeItemCategories(long itemId){
//        dataAccess.storeItemCategories(itemId);
//    }

    @Override
    public List<Item> searchItems(String searchTerm, String category){
        return dataAccess.searchItems(searchTerm, category);
    }

//    @Override
//    public void closeAuction(long itemId){
//        dataAccess.closeAuction(itemId);
//    }

    @Override
    public void updateCurrentBid(long itemId, float amount){
        dataAccess.updateCurrentBid(itemId, amount);
    }
}
