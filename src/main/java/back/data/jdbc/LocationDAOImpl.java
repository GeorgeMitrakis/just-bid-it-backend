package back.data.jdbc;

import back.data.LocationDAO;

import java.util.List;

public class LocationDAOImpl implements LocationDAO {

    private final DataAccess dataAccess;

    public LocationDAOImpl(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public List<String> getLocationNamesByStartOfName(String substring){
        return dataAccess.getLocationNamesByStartOfName(substring);
    }
}
