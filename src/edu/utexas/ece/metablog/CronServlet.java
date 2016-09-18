package edu.utexas.ece.metablog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try
		{
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("leonduantian@hotmail.com", "Leon Duan"));
			msg.addRecipient(Message.RecipientType.TO,
	                   new InternetAddress("leonduantian@hotmail.com", "Mr. User"));
			msg.setSubject("Daily Digest");
			msg.setText("Test");
			Transport.send(msg);
	    } catch (AddressException e) {
	    	  // ...
	    } catch (MessagingException e) {
	      // ...
	    } catch (UnsupportedEncodingException e) {
	      // ...
	    }
	}
	
}
