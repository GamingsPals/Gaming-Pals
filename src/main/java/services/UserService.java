
package services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.CreditCard;
import domain.Rating;
import domain.ReportUser;
import domain.Team;
import domain.User;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class UserService {

	// Managed Repository
	@Autowired
	private UserRepository	userRepository;

	@Autowired
	private Validator		validator;


	public UserService() {
		super();
	}

	public User create() {
		UserAccount userAccount = new UserAccount();
		Authority aut = new Authority();
		aut.setAuthority(Authority.USER);
		Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);

		User res = new User();
		res.setTeams(new ArrayList<Team>());
		res.setRatingsReceived(new ArrayList<Rating>());
		res.setRatingsDone(new ArrayList<Rating>());
		res.setUserAccount(userAccount);
		res.setCreateReports(new ArrayList<ReportUser>());
		res.setFollowerUsers(new ArrayList<User>());
		res.setFollowingUsers(new ArrayList<User>());

		return res;
	}

	public User save(User user) {
		Assert.notNull(user);
		return userRepository.save(user);
	}

	public User findOne(int userId) {
		return userRepository.findOne(userId);
	}
	public Collection<User> findAll() {
		return userRepository.findAll();
	}

	public User register(User user) {

		Assert.notNull(user);
		UserAccount userAccount;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount = user.getUserAccount();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));
		user.setUserAccount(userAccount);

		return save(user);
	}

	public User findByName(String string){
		User u = userRepository.findByName(string);
		Assert.notNull(u);

		return u;
	}

	public User edit(User user) {

		Assert.notNull(user);
		User logged = findByPrincipal();
		Assert.isTrue(user.equals(logged));

		return save(user);
	}

	public User findByPrincipal() {
		UserAccount userAccount;
		User user;
		userAccount = LoginService.getPrincipal();
		user = userRepository.findByPrincipal(userAccount.getId());
		Assert.isTrue(user.getUserAccount().equals(userAccount));
		return user;
	}
	public Boolean isUser() {
		Boolean res = true;
		try {
			LoginService.getPrincipal().getAuthorities().contains(Authority.USER);
		} catch (Exception e) {
			res = false;
		}

		return res;
	}

	public User reconstruct(User user, BindingResult bindingResult) {

		User res;

		if (user.getId() == 0) {

			res = user;

		} else {

			res = findOne(user.getId());

			CreditCard aux = res.getCreditCard();
			CreditCard c = user.getCreditCard();
			aux.setBrandName(c.getBrandName());
			aux.setCvvCode(c.getCvvCode());
			aux.setExpirationMonth(c.getExpirationMonth());
			aux.setExpirationYear(c.getExpirationYear());
			aux.setHolderName(c.getHolderName());
			aux.setNumber(c.getNumber());

			res.setCreditCard(aux);

			validator.validate(res, bindingResult);

		}

		return res;

	}

	public boolean hasValidCreditCard(User user) {
		CreditCard c = user.getCreditCard();
		//Assert.isTrue(contest.getOpening().getTime()>(System.currentTimeMillis()-86400000));
		//Date expiration = new Cale
		Calendar calendar = new GregorianCalendar(c.getExpirationYear(), c.getExpirationMonth(), 1);
		Date expiration = calendar.getTime();

		return expiration.getTime() > (System.currentTimeMillis() + 86400000 * 7);
	}

	//Follow & Unfollow.

	public void followUser(User user) {
		User actor = findByPrincipal();
		Assert.notNull(user);
		Assert.isTrue(actor.getId() != user.getId());
		Assert.isTrue(!actor.getFollowingUsers().contains(user));
		Assert.isTrue(!user.getFollowerUsers().contains(actor));

		Collection<User> following = actor.getFollowingUsers();
		Collection<User> followers = user.getFollowerUsers();

		following.add(user);
		followers.add(actor);

		user = save(user);
		actor = save(actor);

		Assert.isTrue(following.contains(user));
		Assert.isTrue(followers.contains(actor));

	}

	public void unfollowUser(User user) {
		User actor = findByPrincipal();
		Assert.notNull(user);
		Assert.isTrue(actor.getId() != user.getId());
		Assert.isTrue(actor.getFollowingUsers().contains(user));
		Assert.isTrue(user.getFollowerUsers().contains(actor));

		Collection<User> following = actor.getFollowingUsers();
		Collection<User> followers = user.getFollowerUsers();

		following.remove(user);
		followers.remove(actor);

		user = save(user);
		actor = save(actor);

		Assert.isTrue(!following.contains(user));
		Assert.isTrue(!followers.contains(actor));
	}

    public List<User> findBestRanked() {
	    List<User> userList = userRepository.findBestRanked();

		return userList.subList(0,Math.min(userList.size(), 5));
    }

    public void followOrUnfollowUser(User user) {
		Assert.notNull(user);
		User actor = findByPrincipal();
		if (!actor.getFollowingUsers().contains(user) && !user.getFollowerUsers().contains(actor)){
		    followUser(user);
        }else{
		    unfollowUser(user);
        }
    }
}
