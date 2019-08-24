package back.data.jdbc;

import back.data.CategoryDAO;
import back.model.Category;

import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {

    private final DataAccess dataAccess;

    public CategoryDAOImpl(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public List<String> getCategoryNamesByStartOfName(String substring){
        return dataAccess.getCategoryNamesByStartOfName(substring);
    }
}
