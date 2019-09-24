package back.data;

import back.model.Bid;

import java.util.List;
import java.util.Optional;

public interface BidDAO {

    Optional<Bid> getBidById(long id);

    List<Bid> getFullBidsInfoByItemId(long itemId);

    void storeBid(Bid bid, long bidderId);
}
