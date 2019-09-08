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
    public List<Item> searchItems(String searchTerm, String category, String location, Float price){
        return dataAccess.searchItems(searchTerm, category, location, price);
    }

//    @Override
//    public void closeAuction(long itemId){
//        dataAccess.closeAuction(itemId);
//    }

    @Override
    public void updateCurrentBid(long itemId, float amount){
        dataAccess.updateCurrentBid(itemId, amount);
    }

    @Override
    public void updateItem(Item item){
        dataAccess.updateItem(item);
    }

    @Override
    public void addCategoryToItem(Item item, String category){
        dataAccess.addCategoryToItem(item, category);
    }

    @Override
    public void removeCategoryFromItem(Item item, String category){
        dataAccess.removeCategoryFromItem(item, category);
    }

    @Override
    public void deleteItem(long itemId){
        dataAccess.deleteItem(itemId);
    }
}
