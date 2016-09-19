package edu.utexas.ece.metablog;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscribeServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        ObjectifyService.register(Subscription.class);
		List<Subscription> subscriptions = ObjectifyService.ofy().load().type(Subscription.class).list();   
    	
		Subscription found = null;
		for(int i =0; i < subscriptions.size(); i ++) {
			if(subscriptions.get(i).getUser().equals(user)) {
				found = subscriptions.get(i);
			}
		}
		
		if(found == null) {   
	        Key guestbookKey = KeyFactory.createKey("Guestbook", "metablog-subs");
	        Entity subscription = new Entity("Subscription", guestbookKey);
	        subscription.setProperty("user", user);
	        subscription.setProperty("date", new Date());
	        subscription.setProperty("subscribed", true);
	 
	        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	        datastore.put(subscription);
		} else {
			found.subscribed = true;
			ObjectifyService.ofy().save();
		}
		
		
        resp.sendRedirect("/");
    }
}
