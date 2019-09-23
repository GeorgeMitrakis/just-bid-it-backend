package back.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

import java.util.Arrays;
import java.util.HashSet;

/**
 * The Restlet App, mapping URL patterns to ServerSideResources.
 */
public class RestfulApp extends Application {

	public RestfulApp() {
		super();
		CorsService corsService = new CorsService();
		corsService.setAllowedOrigins(new HashSet<String>(Arrays.asList("*")));
		corsService.setAllowedCredentials(true);
		corsService.setSkippingResourceForCorsOptions(true);
		getServices().add(corsService);
	}

	@Override
	public synchronized Restlet createInboundRoot() {

		Router router = new Router(getContext());

		//GET
		router.attach("/hello", HelloWorldResource.class);

		//POST
		router.attach("/login", LoginResource.class);

		//POST
		router.attach("/signup", SignupResource.class);

		//GET
		router.attach("/search", SearchResource.class);

		//GET
		router.attach("/categories", CategoriesAutocompleteResource.class);

		//GET
		router.attach("/locations", LocationsAutocompleteResource.class);

		//GET, POST (admin)
		router.attach("/admin/users", UsersResource.class);

		//GET
		router.attach("/admin/download/json", DownloadJSONResource.class);

		//GET
		router.attach("/admin/download/xml", DownloadXMLResource.class);

//		//GET, PUT (admin)
//		router.attach("/admin/users/{username}", );

        //PUT
        router.attach("/admin/users/{username}/accept", GrantAccessToUserResource.class);

		//PUT
		router.attach("/admin/users/{username}/decline", DenyAccessToUserResource.class);

		//GET, POST (common user)
		router.attach("/items", ItemsResource.class);

		//GET, PUT, DELETE (common user)
		router.attach("/items/{id}", ItemResource.class);

		//POST (common user)
		router.attach("/items/{id}/bid", ItemBidResource.class);

		//POST (common user)
		router.attach("/items/{id}/buy", ItemBuyResource.class);

//		//GET (common user)
//		router.attach("/bids", );

//		//GET (common user)
//		router.attach("/messages", );

		//GET
		router.attach("/usernames", UsernameAutocompleteResource.class);

		//GET (common user)
		router.attach("/messages/sent", MessagesSentResource.class);

		//GET (common user)
		router.attach("/messages/received", MessagesReceivedResource.class);

//		//GET (common user)
//		router.attach("/messages/{id}", );

//		//GET (common user)
//		router.attach("/messages/{username}", );

		//POST (common user)
		router.attach("/messages/{username}/send", MessageSendResource.class);

		//DELETE
		router.attach("/messages/{id}/delete", MessageDeleteResource.class);

		//GET
		router.attach("/upload", UploadResource.class);

		return router;
	}

}