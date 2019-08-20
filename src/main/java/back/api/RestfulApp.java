package back.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * The Restlet App, mapping URL patterns to ServerSideResources.
 */
public class RestfulApp extends Application {

	@Override
	public synchronized Restlet createInboundRoot() {

		Router router = new Router(getContext());

		//GET
		router.attach("/hello", HelloWorldResource.class);

		//POST
		router.attach("/login", LoginResource.class);

		//POST
		router.attach("/signup", SignupResource.class);

//		//GET
//		router.attach("/search", );

//		//GET, POST (admin)
//		router.attach("/admin/users", );

//		//GET, PUT (admin)
//		router.attach("/admin/users/{username}", );

		//GET, POST (common user)
		router.attach("/items", ItemsResource.class);

//		//GET, PUT, DELETE (common user)
//		router.attach("/items/{id}", );

//		//POST (common user)
//		router.attach("/items/{id}/bid", );

//		//POST (common user)
//		router.attach("/items/{id}/buy", );

//		//GET (common user)
//		router.attach("/bids", );

//		//GET (common user)
//		router.attach("/messages", );

//		//GET (common user)
//		router.attach("/messages/sent", );

//		//GET (common user)
//		router.attach("/messages/received", );

//		//GET (common user)
//		router.attach("/messages/{id}", );

//		//GET (common user)
//		router.attach("/messages/{username}", );

//		//POST (common user)
//		router.attach("/messages/{username}/send", );

		return router;
	}

}