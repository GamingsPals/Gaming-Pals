package domain.feedback;

import domain.Actor;
import domain.DomainEntity;
import domain.User;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
public class Likes extends DomainEntity {

    private Feedback feedback;
    private Boolean liked;
    private Actor author;

    public Likes(){
        this.liked = false;
    }

    @JsonIgnore
    @ManyToOne
    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @ManyToOne
    public Actor getAuthor() {
        return author;
    }

    public void setAuthor(Actor author) {
        this.author = author;
    }
}
