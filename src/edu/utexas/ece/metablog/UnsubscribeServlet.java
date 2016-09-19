package edu.utexas.ece.metablog;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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

public class UnsubscribeServlet extends HttpServlet {
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
		if(found != null) {
			found.subscribed = false;
			ObjectifyService.ofy().save();
		}
        resp.sendRedirect("/");
    }
}
