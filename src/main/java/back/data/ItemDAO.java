package back.data;

import back.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDAO {

    List<Item> getItems(Limits limits);

    Optional<Item> getById(long id);
}
