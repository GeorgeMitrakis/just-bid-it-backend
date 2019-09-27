package back.api;

import back.conf.Configuration;
import back.data.BidDAO;
import back.data.ItemDAO;
import back.data.UserDAO;
import back.model.Bid;
import back.model.Item;
import back.model.Items;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class UploadResource extends ServerResource {
    private UserDAO userDAO = Configuration.getInstance().getUserDAO();
    private ItemDAO itemDAO = Configuration.getInstance().getItemDAO();
    private BidDAO bidDAO = Configuration.getInstance().getBidDAO();


    @Override
    protected Representation get() throws ResourceException{
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Items items = (Items) unmarshaller.unmarshal(new FileReader("/home/george/Dropbox/10/Web_Technologies/ebay-data-2/items-0.xml"));
            System.err.println("[items-0.xml]: unmarshalled." );

            for (int i = 1; i < 40; i++) {
                items.addItems(((Items) unmarshaller.unmarshal(new FileReader("/home/george/Dropbox/10/Web_Technologies/ebay-data-2/items-"+i+".xml"))));
                System.err.println("[items-"+i+".xml]: unmarshalled." );
            }

            int cnt1 = 0;
            int cnt2 = 0;


            for (Item item: items.getItems()) {
                cnt2 = 0;
                userDAO.storeEbaySeller(item.getSeller());
                System.err.println("item["+cnt1+"][seller]: stored");
                itemDAO.storeEbayItem(item);
                System.err.println("item["+cnt1+"][item]: stored");
                for (Bid bid: item.getBids()) {
                    userDAO.storeEbayBidder(bid.getBidder());
                    System.err.println("item["+cnt1+"][bidder["+cnt2+"]]: stored");
                    bidDAO.storeEbayBid(bid, (int) item.getId());
                    System.err.println("item["+cnt1+"][bid["+cnt2+"]]: stored");
                    cnt2++;
                }
                cnt1++;
            }

            Map<String,Object> map = new HashMap<>();
            map.put("items", items.getItems());
            map.put("count", items.getItems().size());
            return new JsonMapRepresentation(map);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        }

    }
}
