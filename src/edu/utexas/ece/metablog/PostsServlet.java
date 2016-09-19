package edu.utexas.ece.metablog;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.*;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class PostsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.setContentType("application/json");
		
		
		ObjectifyService.register(Greeting.class);
		List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   
		   
		Collections.sort(greetings); 
		if (greetings.isEmpty()) {
			resp.getWriter().println("{"
					+ "\"posts\": []"
					+ "}");
			return;
		} else {
			int p = 0;
			try { p = Integer.parseInt(req.getParameter("page"));
			} catch (Exception e) { }
			
			if(p > (greetings.size() * 1.0) / (5.0 )) {
				resp.getWriter().println("{"
						+ "\"posts\": []"
						+ "}");
				return;
			}
			
			
			
			int i = p * 5;
			int max = i + 5;
			
			
			StringBuilder sb = new StringBuilder();
			
			while(i < greetings.size() && i < max) {
				Greeting g = greetings.get(i);
				String t = g.title.replaceAll("\"", "&quot;").replaceAll("\r\n", " ").replaceAll("\n", " ");
				String c = "[\"";
				
				String[] lines = g.getContent().replaceAll("\"", "&quot;").replaceAll("\r\n", "\n").split("\n");
				boolean first = true;
				for(int j = 0; j < lines.length; j++) {
					if(lines[j].length() > 0) {
						if(!first) {
							c += "\", \"";
						}
						
						first = false;
						c+= lines[j];
					}
				}
				c += "\"]";
				
				if(max-i != 5) {
					sb.append(",");
				}

				sb.append("{\"title\": \""+t+"\", \"author\": \""+g.user+"\", \"date\": \""+g.getDate()+"\", \"message\": "+c+", \"color\": \"teal\"}");
				i++;
			}
			
			
			
			
//			String post1 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author A\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"teal\" }";
//			String post2 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author B\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"orange\" }";
//			String post3 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author A\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"purple\" }";
//			String post4 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author A\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"teal\" }";
//			String post5 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author C\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"teal\" }";
			
			resp.getWriter().println("{"
					+ "\"posts\": ["
							+ sb.toString()
						+ "]"
					+ "}");
		}
	}
}
