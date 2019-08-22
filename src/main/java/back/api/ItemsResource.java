package back.api;

import back.data.ItemDAO;
import back.data.Limits;
import back.model.Item;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import back.conf.Configuration;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsResource extends ServerResource {

    private final ItemDAO itemDAO = Configuration.getInstance().getItemDAO();


    @Override
    protected Representation get() throws ResourceException {
        if(getQueryValue("userId").isEmpty()){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"error in userId parameter");
        }
        int userId = Integer.parseInt(getQueryValue("userId"));

        Limits limits = new Limits(0, 100);
        List<Item> items = itemDAO.getItems(userId, limits);

        Map<String, Object> map = new HashMap<>();
        map.put("start", limits.getStart());
        map.put("count", limits.getCount());
        map.put("total", limits.getTotal());
        map.put("results", items);

        return new JsonMapRepresentation(map);
    }


    @Override
    protected Representation post(Representation entity) throws ResourceException {
    //NOTE: parameters needed to be passed as raw, for now
        //Create a new restlet form
        Form form = new Form(entity);
        System.err.println(form);
        // categories
        ArrayList<String> categories = new ArrayList<>();
        for (int i=0; i<form.size() ; i++){
            if(form.get(i).getName().equals("categories[]")){
                System.err.println(form.get(i).getValue());
                categories.add(form.get(i).getValue());
            }
        }

        //validate the values
        if (    form.getFirstValue("userId") == null || form.getFirstValue("userId").isEmpty()
                ||form.getFirstValue("name") == null || form.getFirstValue("name").equals("")
                ||categories.isEmpty()
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
        double latitude = ((!(form.getFirstValue("latitude") == null)) && (!(form.getFirstValue("latitude").isEmpty()))) ? Double.parseDouble(form.getFirstValue("latitude")) : Double.NaN;
        double longitude = ((!(form.getFirstValue("longitude") == null)) && (!(form.getFirstValue("longitude").isEmpty())))? Double.parseDouble(form.getFirstValue("longitude")) : Double.NaN;
        String country = form.getFirstValue("country");

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String start = myDateObj.format(myFormatObj);//start time
        String end = form.getFirstValue("end");//end time
        String description = form.getFirstValue("description");

        if(Double.isNaN(latitude) || Double.isNaN(longitude)){
            System.err.println("coordinates are not given.");
        }

        Item item = new Item(0, userId, true, name, firstBid, firstBid, buyPrice, 0, location, latitude, longitude, country, start,end, description);
        try{
            itemDAO.storeItem(item);
        }
        catch (DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "item insertion in database failed");
        }

        try{
            itemDAO.storeItemCategories(item.getId(), categories);
        }
        catch (DataAccessException e){
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "categories insertion in database failed");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("item", item);
        map.put("categories", categories.toArray());
        return new JsonMapRepresentation(map);
    }
}

