package back.data.jdbc;

import back.data.BidDAO;
import back.model.Bid;

import java.util.List;
import java.util.Optional;

public class BidDAOImpl implements BidDAO {

    private final DataAccess dataAccess;

    public BidDAOImpl(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Optional<Bid> getBidById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Bid> getFullBidsInfoByItemId(long itemId){
        return dataAccess.getFullBidsInfoByItemId(itemId);
    }
    @Override
    public void storeBid(Bid bid) {
        dataAccess.storeBid(bid);
    }
}
