package services;

import java.util.Collection;

import javax.transaction.Transactional;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Rating;
import repositories.RatingRepository;

@Service
@Transactional
public class RatingService {
	
		@Autowired
	 	private RatingRepository ratingRepository;
	 
	 	@Autowired
	 	private UserService	userService;
	 
	 
	 	public RatingService() {
	 		super();
	 	}
	 
	 	public Rating create() {
	 
	 		Rating result = new Rating();
	 
	 		result.setRatedUser(userService.create());
	 		result.setRatingUser(userService.create());
	 
	 		return result;
	 	}
	 
	 	public Rating save(Rating rating) {
	 
	 		Assert.notNull(rating);
	 		return ratingRepository.save(rating);
	 
	 	}
	 
	 	public void delete(Rating rating) {
	 
	 		Assert.notNull(rating);
	 		ratingRepository.delete(rating);
	 
	 	}
	 
	 	public Rating findOne(int ratingId) {
	 
	 		Rating result=ratingRepository.findOne(ratingId);
	 
	 		Assert.notNull(result);
	 
	 		return result;
	 
	 	}
	 
	 	public Collection<Rating> findAll() {
	 
	 		Collection<Rating> result;
	 		result = ratingRepository.findAll();
	 
	 		Assert.notNull(result);
	 
	 		return result;
	 
	 	}

    public void rateUser(Rating rating, User user) {
        User u = userService.findByPrincipal();
        Assert.notNull(rating);
        Assert.notNull(user);
        Assert.notNull(u);
        Rating r = ratingRepository.findRatingFromUserToUser(u,user);
        if (r==null){
            rating.setRatedUser(user);
            rating.setRatingUser(u);
            save(rating);
        }else{
            r.setAttitude(rating.getAttitude());
            r.setComment(rating.getComment());
            r.setSkill(rating.getSkill());
            r.setKnowledge(rating.getKnowledge());
            save(r);
        }
    }
}
