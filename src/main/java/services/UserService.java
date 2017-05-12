
package services;

import java.util.*;
import java.util.stream.Collectors;

import domain.*;
import domain.notifications.FollowNotification;
import forms.EditProfileForm;
import forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.notifications.FollowNotificationService;
import utilities.RelatedUsers;

@Service
@Transactional
public class UserService {

	// Managed Repository
	@Autowired
	private UserRepository	userRepository;

	@Autowired
    private GameService gameService;

	@Autowired
    private LanguageService languageService;

	@Autowired
    private ConfigurationService configurationService;

	@Autowired
	private Validator		validator;

	@Autowired
    FollowNotificationService followNotificationService;


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
		res.setVerify(false);
		res.setTeams(new ArrayList<>());
		res.setRatingsReceived(new ArrayList<>());
		res.setRatingsDone(new ArrayList<>());
		res.setUserAccount(userAccount);
		res.setFollowerUsers(new ArrayList<>());
		res.setFollowingUsers(new ArrayList<>());
		res.setReceived(new ArrayList<>());
		res.setSended(new ArrayList<>());
		res.setLanguages(new ArrayList<>());
		res.setGameInfo(new ArrayList<>());
		res.setRatingAvg(0.0);
		res.setAttitudeAvg(0.0);
		res.setKnowledgeAvg(0.0);
		res.setSkillAvg(0.0);
		return res;
	}

	public User save(User user) {
		Assert.notNull(user);
        if(user.getPicture()==null){
            user.setPicture(configurationService.getConfiguration().getDefaultAvatar());
        }
        if(user.getHeader()==null){
            user.setHeader(configurationService.getConfiguration().getDefaultHeader());
        }
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
		if(user.getPicture()==null){
            user.setPicture(configurationService.getConfiguration().getDefaultAvatar());
		}
        if(user.getHeader()==null){
		    user.setHeader(configurationService.getConfiguration().getDefaultHeader());
        }
		return save(user);
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
        if(!followNotificationService.isNotification(actor,user)) {
            FollowNotification followNotification = new FollowNotification();
            followNotification.setFollower(actor);
            followNotification.setFollowing(user);
            followNotification.setActor(user);
            followNotificationService.save(followNotification);
        }
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

		return userList.subList(0, Math.min(userList.size(), 5));
	}

	public void followOrUnfollowUser(User user) {
		Assert.notNull(user);
		User actor = findByPrincipal();
		if (!actor.getFollowingUsers().contains(user) && !user.getFollowerUsers().contains(actor)) {
			followUser(user);
		} else {
			unfollowUser(user);
		}
	}

	public User findByUserAccountUsername(String string) {
		User u = userRepository.findByUserAccountUsername(string);
		Assert.notNull(u);

		return u;
	}

	public Collection<User> usersForGameTag(String tag) {
		Collection<User> users = userRepository.usersForGameTag(tag);
		Assert.notNull(users);
		return users;
	}

	public Collection<User> usersForGameTag() {
		Collection<User> users = userRepository.usersForGameTag();
		Assert.notNull(users);
		return users;
	}

	public Collection<User> usersFromGameAndTier(String gameTag, String tier) {
		Collection<User> users = userRepository.usersFromGameAndTier(gameTag, tier);
		Assert.notNull(users);
		return users;
	}

	public Collection<User> usersForLanguage() {
		Collection<User> users = userRepository.usersForLanguage();
		Assert.notNull(users);
		return users;
	}

	public Collection<User> usersForLanguage(String language) {
		Collection<User> users = userRepository.usersForLanguage(language);
		Assert.notNull(users);
		return users;
	}

	public Collection<User> userFromUsernameAndTagGame() {
		Collection<User> users = userRepository.userFromUsernameAndTagGame();
		Assert.notNull(users);
		return users;
	}

	public Collection<User> search(SearchForm searchForm) {
	    try {
            List<User> users = userRepository.findAll();
            List<Language> languages = searchForm.getLanguages();
            List<Game> games = searchForm.getGames();
            List<User> result = new ArrayList<>();
            for (User e : users) {
                List<Game> userGames = new ArrayList<>();
                for (GameInfo gameInfo : e.getGameInfo()) {
                    userGames.add(gameInfo.getGame());
                }
                if(games!=null){
                    if (!userGames.containsAll(games)) continue;
                }
                if(languages!=null){
                    if (!e.getLanguages().containsAll(languages)) continue;
                }
                if (searchForm.getUsername() != null) {
                	Collection<User> byName = userRepository.usersByName("%"+searchForm.getUsername()+"%");
                	if(!(byName.contains(e))) continue;
                }
                if (searchForm.getRatingAvg() != null) {
                    if (e.getRatingAvg() < searchForm.getRatingAvg()) continue;
                }

                result.add(e);
            }

            return result;
        } catch (Exception e){
	        return userRepository.findAll();
        }
	}

	public List<User> getRelatedUsers(User user){
	    Assert.notNull(user);
        RelatedUsers relatedUsers = new RelatedUsers(user);
        relatedUsers.calculate();

        return relatedUsers.getRelatedUsers();
    }

    public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
    }

	public void editForm(EditProfileForm signupForm) {
		User user = findByPrincipal();
		Assert.notNull(user);
		user.setAge(signupForm.getAge());
		if(!signupForm.getPassword().equals("") || signupForm.getPassword()!=null){
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			user.getUserAccount().setPassword(encoder.encodePassword(signupForm.getPassword(), null));
		}
		user.getUserAccount().setUsername(signupForm.getUsername());
		user.setPicture(signupForm.getPicture());
		user.setEmail(signupForm.getEmail());
		user.setHeader(signupForm.getHeader());
		List<Language> languages = new ArrayList<>();
		for(String e: signupForm.getLanguages().split(",")){
			languages.add(languageService.findOne(Integer.valueOf(e)));
		}
		user.setLanguages(languages);
		save(user);
	}

    public Collection<User> findAllNotBanned() {

		return userRepository.findAllNotBanned();
    }
}
