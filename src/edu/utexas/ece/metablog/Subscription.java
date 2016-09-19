package edu.utexas.ece.metablog;

import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Subscription implements Comparable<Subscription> {

    @Id Long id;
    User user;
    Date date;
    Boolean subscribed;
    
    private Subscription() {
    	
    }
    
    public Subscription(User user, Boolean subscribed) {
        this.user = user;
        this.subscribed = subscribed;
        date = new Date();
        
    }

    public User getUser() {
        return user;
    }
    
    public Boolean getSubscribed() {
    	return subscribed;
    }
    
    public String getDate() {
        return date.toString();
    }

    @Override
    public int compareTo(Subscription other) {
        if (date.after(other.date)) {
            return -1;
        } else if (date.before(other.date)) {
            return 1;
        }
        return 0;
    }
}