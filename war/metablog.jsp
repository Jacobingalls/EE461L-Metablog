<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.utexas.ece.metablog.*" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
  <link href="/stylesheets/screen.css" media="screen, projection" rel="stylesheet" type="text/css" />
  <link href="/stylesheets/print.css" media="print" rel="stylesheet" type="text/css" />
  <!--[if IE]>
      <link href="/stylesheets/ie.css" media="screen, projection" rel="stylesheet" type="text/css" />
  <![endif]-->

  <link rel="stylesheet" href="/bower_components/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="/bower_components/prism/themes/prism-okaidia.css">
</head>

<div class="header">

    <h1><img src="/img/somelogo_teal.png" class="logo"> The Metablog Blog</h1>
    <h2>A blog about the making of a blog</h2>


<%

    String guestbookName = request.getParameter("guestbookName");
    if (guestbookName == null) {
        guestbookName = "default";
    }
    pageContext.setAttribute("guestbookName", guestbookName);
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
%>
    <br>
    <div class="links">
        <ul>
            <li><a href="#" class="createLink">Create</a></li>
            
            
            <%
            
            	ObjectifyService.register(Subscription.class);
				List<Subscription> subscriptions = ObjectifyService.ofy().load().type(Subscription.class).list();   
            	
            	boolean found = false;
				for(int i =0; i < subscriptions.size(); i ++) {
					if(subscriptions.get(i).getUser().equals(user)) {
						if(subscriptions.get(i).getSubscribed())
							found = true;
					}
				}
            
            	if(found) {
            %>
            
            <li><a href="/unsubscribe">Unsubscribe</a></li>
            
            <%
            } else {
            %>
            
            <li><a href="/subscribe">Subscribe</a></li>
            
            <%
            }
            %>
            
            <li><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Logout</a></li>
       	</ul>
    </div>
    <br>
<%

    } else {

%>
	<br>
    <div class="links">
        <ul>
        	<li><a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Login</a></li>
        </ul>
    </div>
    <br>
<%

    }

%>

</div>

<div class="body">

	
	<div class="postOverlay">
		<form action="/post" method="post">
			<input type="text" name="title" id="title" class="title" placeholder="TLDR">
			<textarea name="content" id="message" placeholder="What would you like to say?"></textarea>
			
			<input type="submit" name="submit" id="submit" class="submit" value="Post">
			<a href="#" class="cancel">Cancel</a>
			
			
			<input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
		</form>
	</div>

    <div class="posts">
    </div>

    <p class="pull">Scroll to load more</p>
    <p class="end">No more posts.</p>

</div>

<script src="/bower_components/jquery/dist/jquery.min.js"></script>
<script src="/bower_components/showdown/dist/showdown.min.js"></script>
<script src="/bower_components/prism/prism.js"></script>
<script src="/bower_components/prism/plugins/autoloader/prism-autoloader.min.js"></script>
<script>
Prism.plugins.autoloader.languages_path = '/bower_components/prism/components/';
</script>

<script src="/js/main.js"></script>

</html>
