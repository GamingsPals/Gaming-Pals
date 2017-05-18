package domain.feedback;

import domain.Actor;
import domain.DomainEntity;
import domain.User;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Feedback  extends DomainEntity{

    private String body;
    private Actor author;
    private Collection<Likes> likes;
    private Collection<Feedback> feedbacks;
    private Feedback parent;


    public Feedback(){
        this.likes = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }
    
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne
    public Actor getAuthor() {
        return author;
    }

    public void setAuthor(Actor author) {
        this.author = author;
    }

    @OneToMany(mappedBy = "feedback",cascade = CascadeType.ALL)
    public Collection<Likes> getLikes() {
        return likes;
    }

    public void setLikes(Collection<Likes> likes) {
        this.likes = likes;
    }


    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    public Collection<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Collection<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @JsonIgnore
    @ManyToOne
    public Feedback getParent() {
        return parent;
    }

    public void setParent(Feedback parent) {
        this.parent = parent;
    }
}
