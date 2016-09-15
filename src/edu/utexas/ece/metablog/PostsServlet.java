package edu.utexas.ece.metablog;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class PostsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
//		try {
//			Thread.sleep(1000L);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		resp.setContentType("application/json");
		
		int p = 0;
		try { p = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) { }
		
		if(p > 2) {
			resp.getWriter().println("{"
					+ "\"posts\": []"
					+ "}");
			return;
		}
		
		int i = p * 5;
		
		String post1 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author A\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"teal\" }";
		String post2 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author B\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"orange\" }";
		String post3 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author A\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"purple\" }";
		String post4 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author A\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"teal\" }";
		String post5 = "{\"title\": \"Sample Title "+(i++)+"\", \"author\": \"Sample Author C\", \"date\": \"Sample Date\", \"message\": \"Sample message\", \"color\": \"teal\" }";
		
		resp.getWriter().println("{"
				+ "\"posts\": ["
						+ post1 +"," + post2 +"," + post3 +"," + post4 +"," + post5
					+ "]"
				+ "}");
	}
}
