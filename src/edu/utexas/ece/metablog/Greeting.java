package edu.utexas.ece.metablog;

import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

 

 

@Entity
public class Greeting implements Comparable<Greeting> {

    @Id Long id;
    User user;
    String title;
    Text content;
    Date date;
    private Greeting() {
    	
    }
    
    public Greeting(User user, String title, Text content) {
        this.user = user;
        this.title = title;
        this.content = content;
        date = new Date();
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content.getValue();
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDate() {
        return date.toString();
    }

    @Override
    public int compareTo(Greeting other) {
        if (date.after(other.date)) {
            return -1;
        } else if (date.before(other.date)) {
            return 1;
        }
        return 0;
    }
}