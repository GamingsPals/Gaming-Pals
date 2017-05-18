package services.feedback;

import domain.Actor;
import domain.feedback.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.feedback.LikesRepository;
import services.ActorService;

import javax.transaction.Transactional;

@Service
@Transactional
public class LikesService {

    @Autowired
    ActorService actorService;

    @Autowired
    private LikesRepository likesRepository;

    public Likes save(Likes likes){
        Assert.notNull(likes);

        return likesRepository.save(likes);
    }

    public void delete(Likes likes){
        Actor actor = actorService.findActorByPrincipal();
        Assert.notNull(actor);
        Assert.notNull(likes);
        Assert.isTrue(likes.getAuthor().equals(actor));

        likesRepository.delete(likes);
    }
}
