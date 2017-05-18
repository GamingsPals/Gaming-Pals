package services.feedback;

import domain.Actor;
import domain.feedback.Feedback;
import domain.feedback.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.feedback.FeedbackRepository;
import services.ActorService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    private LikesService likesService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ActorService actorService;

    public Collection<Feedback> findAll(){
        return feedbackRepository.findAll();
    }

    public Feedback findOne(int id){
        return feedbackRepository.findOne(id);
    }

    public String add(String body, Feedback parent) {
        Actor principal = actorService.findActorByPrincipal();
        Assert.notNull(principal);
        if(checkNotSpam()){
            return "You cannot post more than a feedback every 10 seconds";
        }
        Feedback feedback = new Feedback();
        feedback.setAuthor(principal);
        feedback.setBody(body);
        if(parent != null){
            if(parent.getId()!=0){
                feedback.setParent(parent);
            }
        }

        save(feedback);

        return null;
    }

    private boolean checkNotSpam() {
        Actor principal = actorService.findActorByPrincipal();
        Assert.notNull(principal);
        Boolean result = false;
        Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
        calendar.add(Calendar.SECOND, -10);
        Date now = new Date();
        Date after = calendar.getTime();
        for(Feedback e: principal.getFeedbacks()){
            if(e.getCreated().after(after)){
                result = true;
                break;
            }
        }

        return result;
    }

    public Feedback save(Feedback feedback) {
        Assert.notNull(feedback);

        return feedbackRepository.save(feedback);
    }

    public Object findAllParents() {

        return feedbackRepository.findAllParents();
    }

    public Boolean likeOrDislike(Feedback feedback, Boolean like) {
        Actor actor = actorService.findActorByPrincipal();
        Assert.notNull(actor);
        Assert.notNull(feedback);
        Assert.notNull(like);
        Likes userLike = feedbackRepository.findLikeByRepositoryAndUser(feedback,actor);
        if(userLike == null){
            createLikes(feedback,like);
            return true;
        }
        if(userLike.getLiked()!=like){
            userLike.setLiked(like);
            likesService.save(userLike);

            return true;
        }
        likesService.delete(userLike);

        return true;
    }

    private void createLikes(Feedback feedback, Boolean like) {
        Actor actor = actorService.findActorByPrincipal();
        Assert.notNull(actor);
        Assert.notNull(feedback);
        Assert.notNull(like);
        Likes likes = new Likes();
        likes.setLiked(like);
        likes.setAuthor(actor);
        likes.setFeedback(feedback);

        likesService.save(likes);
    }
}
