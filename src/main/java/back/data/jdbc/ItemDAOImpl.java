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
    public List<Item> getItems(Limits limits){
        List<Item> items = dataAccess.getItems(limits.getStart(), limits.getCount());
        limits.setTotal(dataAccess.countItems());

        return items;
    }

    @Override
    public Optional<Item> getById(long id) {
        return dataAccess.getItem(id);
    }
}
