package edu.utexas.ece.metablog;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.ObjectifyService;
import javax.mail.Transport;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	static final long DAY = 24 * 60 * 60 * 1000;
	private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		_logger.info("Cron Job has been executed");
		
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        ObjectifyService.register(Subscription.class);
		List<Subscription> subscriptions = ObjectifyService.ofy().load().type(Subscription.class).list();
		ObjectifyService.register(Greeting.class);
		List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   
		try {
			
			for(Subscription s: subscriptions){
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("leonduantian@utexas.edu"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(s.getUser().getEmail()));
				msg.setSubject("The Metablog Blog Daily Digest");
				Date d = new Date();
				String content = "";
			    for(Greeting g: greetings)
			    {
			    	if (d.getTime() < g.date.getTime() + DAY)
			    	{
			    		content = content.concat("\n" + g.title);
			    	}
			    	
			    }
			    if(content != null){
			    	msg.setText(content);
			    	Transport.send(msg);
			    }
			}
		}
		
		catch (AddressException e) 
		{
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			_logger.warning(errors.toString());
		}
		catch (MessagingException e) 
		{
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			_logger.warning(errors.toString());
		}
		catch (Exception e)
		{
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			_logger.warning(errors.toString());
		}
	}
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		doGet(req, resp);
	}
}