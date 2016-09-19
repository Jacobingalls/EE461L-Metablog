package edu.utexas.ece.metablog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.mail.Transport;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	private static Map<String, String> customer_List = new HashMap();
	
	
	private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		try {
////				_logger.info("Cron Job has been executed");
//				Properties props = new Properties();
//				Session session = Session.getDefaultInstance(props, null);
//				Message msg = new MimeMessage(session);
//				msg.setFrom(new InternetAddress("jokerjoker216@gmail.com"));
//				msg.addRecipient(Message.RecipientType.TO, new InternetAddress("leonduantian@hotmail.com"));
//				msg.setSubject("Test");
//				msg.setText("Test");
//				Transport.send(msg);
//			}
//		catch (AddressException e) 
//		{
//			e.printStackTrace();
//			_logger.warning("Email failed");
//		} 
//		catch (MessagingException e) 
//		{
//			e.printStackTrace();
//			_logger.warning("Email failed");
//		} 
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			_logger.warning("Email failed");
//		}
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		doGet(req, resp);
	}
}