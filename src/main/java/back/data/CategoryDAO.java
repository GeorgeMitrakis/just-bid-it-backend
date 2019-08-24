package back.data;

import back.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {

    List<String> getCategoryNamesByStartOfName(String substring);
}
