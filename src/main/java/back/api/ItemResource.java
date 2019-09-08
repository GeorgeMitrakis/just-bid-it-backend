package back.api;

import back.conf.Configuration;
import back.data.CategoryDAO;
import back.data.ItemDAO;
import back.model.Item;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ItemResource extends ServerResource {
    private final ItemDAO itemDAO = Configuration.getInstance().getItemDAO();
    //private final CategoryDAO categoryDAO = Configuration.getInstance().getCategoryDAO();

    @Override
    protected Representation put(Representation entity) throws ResourceException {

        int itemId = Integer.parseInt(getAttribute("id"));
        Form form = new Form(entity);
        System.err.println(form);
        //categories
        ArrayList<String> newCategories = new ArrayList<>();
        for (int i=0; i<form.size() ; i++){
            if(form.get(i).getName().contains("categories")){
                System.err.println(form.get(i).getValue());
                newCategories.add(form.get(i).getValue());
            }
        }
        //check the values
        if (    form.getFirstValue("userId") == null || form.getFirstValue("userId").isEmpty()
                ||form.getFirstValue("name") == null || form.getFirstValue("name").equals("")
                ||newCategories.isEmpty()
                ||form.getFirstValue("location") == null || form.getFirstValue("location").equals("")
                ||form.getFirstValue("country") == null || form.getFirstValue("country").equals("")
                ||form.getFirstValue("buy_price") == null
                ||form.getFirstValue("first_bid") == null
                ||form.getFirstValue("end") == null || form.getFirstValue("end").equals("")
                ||form.getFirstValue("description") == null || form.getFirstValue("description").equals("")
        ){
            System.err.println(form);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "missing or empty parameters");
        }

        //extract the values
        long userId = Long.parseLong(form.getFirstValue("userId"));
        String name = form.getFirstValue("name");
        float buyPrice = Float.parseFloat(form.getFirstValue("buy_price"));
        float firstBid = Float.parseFloat(form.getFirstValue("first_bid"));
        String location = form.getFirstValue("location");
        Double latitude = null;
        Double longitude = null;
        String country = form.getFirstValue("country");
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String end = LocalDateTime.parse(form.getFirstValue("end")).format(myFormatObj).toString();//end time
        String description = form.getFirstValue("description");

        if(form.getFirstValue("latitude") == null || form.getFirstValue("latitude").equals("")
                ||form.getFirstValue("longitude") == null || form.getFirstValue("longitude").equals("")){
            System.err.println("coordinates are not given.");
        }
        else{
            latitude = Double.parseDouble(form.getFirstValue("latitude"));
            longitude = Double.parseDouble(form.getFirstValue("longitude"));
        }

        Item item;

        try{
            Optional<Item> itemOptional = itemDAO.getItemById(itemId);
            if(!itemOptional.isPresent()){
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Item does not exist");
            }
            item = itemOptional.get();
            if(!item.isRunning()){
                throw  new ResourceException(Status.CLIENT_ERROR_EXPECTATION_FAILED, "Auction is closed");
            }
            if(item.getNumberOfBids()>0){
                throw new ResourceException(Status.CLIENT_ERROR_EXPECTATION_FAILED, "Auction has bids and cannot be edited");
            }

            item.setName(name);
            item.setBuyPrice(buyPrice);
            item.setFirstBid(firstBid);
            item.setCurrentBid(firstBid);
            item.setLocation(location);
            item.setLatitude(latitude);
            item.setLongitude(longitude);
            item.setCountry(country);
            item.setEnd(end);
            item.setDescription(description);

            itemDAO.updateItem(item);

            List<String> oldCategories = item.getCategories();
            for (String c: oldCategories) {
                if(!newCategories.contains(c)){
                    itemDAO.removeCategoryFromItem(item, c);
                }
            }
            for (String c: newCategories) {
                if(!oldCategories.contains(c)){
                    itemDAO.addCategoryToItem(item, c);
                }
            }
            item.setCategories(newCategories);
        }
        catch (DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "item update in database failed");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("item", item);

        return new JsonMapRepresentation(map);
    }


    @Override
    protected Representation delete() throws ResourceException{
        long itemId = Long.parseLong(getAttribute("id"));
         Optional<Item> itemOptional = itemDAO.getItemById(itemId);
         if(!itemOptional.isPresent()){
             throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "item does not exist");
         }

         Item item = itemOptional.get();
         if(!item.isRunning()){
             throw new ResourceException(Status.CLIENT_ERROR_EXPECTATION_FAILED, "item auction is closed");
         }
         if(item.getNumberOfBids()>0){
             throw new ResourceException(Status.CLIENT_ERROR_EXPECTATION_FAILED, "item auction has bids and cannot be deleted");
         }

         try{
             itemDAO.deleteItem(itemId);
         }
         catch (DataAccessException e){
             throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "item deletion in database failed");
         }

        Map<String,Object> map = new HashMap<>();
        map.put("item", item);

        return new JsonMapRepresentation(map);
    }
}
