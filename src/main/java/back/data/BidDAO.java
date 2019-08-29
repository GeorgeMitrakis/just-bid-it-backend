package back.data;

import back.model.Bid;

import java.util.Optional;

public interface BidDAO {

    Optional<Bid> getBidById(long id);

    void storeBid(Bid bid);
}
