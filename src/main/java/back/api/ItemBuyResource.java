package back.api;

import back.conf.Configuration;
import back.data.BidDAO;
import back.data.ItemDAO;
import back.data.UserDAO;
import back.model.Bid;
import back.model.CommonUser;
import back.model.Item;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemBuyResource extends ServerResource {
    private final ItemDAO itemDAO = Configuration.getInstance().getItemDAO();
    private final BidDAO bidDAO = Configuration.getInstance().getBidDAO();
    private final UserDAO userDAO = Configuration.getInstance().getUserDAO();

    @Override
    protected Representation post(Representation entity) throws ResourceException {

        //get id from resource URI
        long itemId = Long.parseLong(getAttribute("id"));

        //Create a new restlet form
        Form form = new Form(entity);

        if( form.getFirstValue("bidder_id") == null || form.getFirstValue("bidder_id").equals("")){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "missing or empty parameters");
        }

        //get Item
        Optional<Item> itemOptional = itemDAO.getItemById(itemId);
        if(!itemOptional.isPresent()){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "invalid item id");
        }
        Item item = itemOptional.get();

        long bidderId = Long.parseLong(form.getFirstValue("bidder_id"));
        if (item.getBuyPrice() == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "item cannot be bought");
        }
        float amount = item.getBuyPrice();


        //check if auction is completed
        if(!item.isRunning()){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "item auction is completed");
        }

        //get current time
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = myDateObj.format(myFormatObj);

        //create new bid object
        Bid bid = new Bid(0, itemId, time, amount);
        item.setEnd(time);
        item.setCurrentBid(item.getBuyPrice());

        //insert bid to db
        try{
            bidDAO.storeBid(bid, bidderId);
            itemDAO.updateItem(item);//change end date, since item will be bought now
        }
        catch(DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "buy-bid insertion in database failed");
        }

        //update item current bid
        try{
            itemDAO.updateCurrentBid(itemId, bid.getAmount());
            item.setRunning(false);
            item.setCurrentBid(amount);
            item.setNumberOfBids(item.getNumberOfBids()+1);
        }
        catch(DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "item update in database failed");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("bid", bid);
        map.put("item", item);

        return new JsonMapRepresentation(map);
    }
}
