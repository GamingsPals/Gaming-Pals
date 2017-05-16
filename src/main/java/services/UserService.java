
package services;

import java.util.*;
import java.util.concurrent.TimeUnit;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import domain.notifications.FollowNotification;
import forms.EditProfileForm;
import forms.SearchForm;
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
	private UserRepository			userRepository;

	@Autowired
	private GameService				gameService;

	@Autowired
	private LanguageService			languageService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;

	@Autowired
	FollowNotificationService		followNotificationService;

	@Autowired
    TournamentService tournamentService;


	public UserService() {
		super();
	}

	public User create() {
		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.USER);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);

		final User res = new User();
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

	public User save(final User user) {
		Assert.notNull(user);
		if (user.getPicture() == null)
			user.setPicture(this.configurationService.getConfiguration().getDefaultAvatar());
		if (user.getHeader() == null)
			user.setHeader(this.configurationService.getConfiguration().getDefaultHeader());
		return this.userRepository.save(user);
	}

	public User findOne(final int userId) {
		return this.userRepository.findOne(userId);
	}

	@Cacheable("allusers")
	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	public User register(final User user) {

		Assert.notNull(user);
		UserAccount userAccount;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount = user.getUserAccount();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));
		user.setUserAccount(userAccount);
		if (user.getPicture() == null)
			user.setPicture(this.configurationService.getConfiguration().getDefaultAvatar());
		if (user.getHeader() == null)
			user.setHeader(this.configurationService.getConfiguration().getDefaultHeader());
		return this.save(user);
	}

	public User edit(final User user) {

		Assert.notNull(user);
		final User logged = this.findByPrincipal();
		Assert.isTrue(user.equals(logged));

		return this.save(user);
	}

	public User findByPrincipal() {
		UserAccount userAccount;
		User user;
		userAccount = LoginService.getPrincipal();
		user = this.userRepository.findByPrincipal(userAccount.getId());

		return user;
	}

	//Follow & Unfollow.

	public void followUser(User user) {
		User actor = this.findByPrincipal();
		Assert.notNull(user);
		Assert.isTrue(actor.getId() != user.getId());
		Assert.isTrue(!actor.getFollowingUsers().contains(user));
		Assert.isTrue(!user.getFollowerUsers().contains(actor));

		final Collection<User> following = actor.getFollowingUsers();
		final Collection<User> followers = user.getFollowerUsers();

		following.add(user);
		followers.add(actor);

		user = this.save(user);
		actor = this.save(actor);

		Assert.isTrue(following.contains(user));
		Assert.isTrue(followers.contains(actor));
		if (!this.followNotificationService.isNotification(actor, user)) {
			final FollowNotification followNotification = new FollowNotification();
			followNotification.setFollower(actor);
			followNotification.setFollowing(user);
			followNotification.setActor(user);
			this.followNotificationService.save(followNotification);
		}
	}

	public void unfollowUser(User user) {
		User actor = this.findByPrincipal();
		Assert.notNull(user);
		Assert.isTrue(actor.getId() != user.getId());
		Assert.isTrue(actor.getFollowingUsers().contains(user));
		Assert.isTrue(user.getFollowerUsers().contains(actor));

		final Collection<User> following = actor.getFollowingUsers();
		final Collection<User> followers = user.getFollowerUsers();

		following.remove(user);
		followers.remove(actor);

		user = this.save(user);
		actor = this.save(actor);

		Assert.isTrue(!following.contains(user));
		Assert.isTrue(!followers.contains(actor));
	}

	public List<User> findBestRanked() {
		updateRatingsAvg();
		final List<User> userList = this.userRepository.findBestRanked();
		return new ArrayList<>(userList.subList(0, Math.min(userList.size(), 3)));
	}

	private void updateRatingsAvg() {
		List<User>users = userRepository.findAll();
		for(User e:users){
			if(e.getRatingAvg()==0){
				e.onUpdate();
				save(e);
			}
		}
	}

	public void followOrUnfollowUser(final User user) {
		Assert.notNull(user);
		final User actor = this.findByPrincipal();
		if (!actor.getFollowingUsers().contains(user) && !user.getFollowerUsers().contains(actor))
			this.followUser(user);
		else
			this.unfollowUser(user);
	}

	public User findByUserAccountUsername(final String string) {
		final User u = this.userRepository.findByUserAccountUsername(string);
		Assert.notNull(u);

		return u;
	}


	public Collection<User> search(final SearchForm searchForm) {
		try {
			final List<User> users = this.userRepository.findAll();
			final List<Language> languages = searchForm.getLanguages();
			final List<Game> games = searchForm.getGames();
			final List<User> result = new ArrayList<>();
			for (final User e : users) {
				final List<Game> userGames = new ArrayList<>();
				for (final GameInfo gameInfo : e.getGameInfo())
					userGames.add(gameInfo.getGame());
				if (games != null)
					if (!userGames.containsAll(games))
						continue;
				if (languages != null)
					if (!e.getLanguages().containsAll(languages))
						continue;
				if (searchForm.getUsername() != null) {
					final Collection<User> byName = this.userRepository.usersByName("%" + searchForm.getUsername() + "%");
					if (!(byName.contains(e)))
						continue;
				}
				if (searchForm.getRatingAvg() != null)
					if (e.getRatingAvg() < searchForm.getRatingAvg())
						continue;

				result.add(e);
			}

			return result;
		} catch (final Exception e) {
			return this.userRepository.findAll();
		}
	}

	public List<User> getRelatedUsers(final User user) {
		Assert.notNull(user);
		final RelatedUsers relatedUsers = new RelatedUsers(user);
		relatedUsers.calculate();

		return relatedUsers.getRelatedUsers();
	}

	public User findUserByEmail(final String email) {
		return this.userRepository.findUserByEmail(email);
	}

	public void editForm(final EditProfileForm signupForm) {
		final User user = this.findByPrincipal();
		Assert.notNull(user);
		user.setAge(signupForm.getAge());
		System.out.println(signupForm.getPassword());
		if (signupForm.getPassword() != null) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			user.getUserAccount().setPassword(encoder.encodePassword(signupForm.getPassword(), null));
		}
		user.getUserAccount().setUsername(signupForm.getUsername());
		user.setPicture(signupForm.getPicture());
		user.setEmail(signupForm.getEmail());
		user.setHeader(signupForm.getHeader());
		final List<Language> languages = new ArrayList<>();
		for (final String e : signupForm.getLanguages().split(","))
			languages.add(this.languageService.findOne(Integer.valueOf(e)));
		user.setLanguages(languages);
		this.save(user);
	}

	public Collection<User> findAllNotBanned() {

		return this.userRepository.findAllNotBanned();
	}

	public void postpaypal() {
		final User user = this.findByPrincipal();
		user.setVerify(true);
		user.setLastpaid(new Date(System.currentTimeMillis() - 1000));
		this.save(user);
	}

    @Cacheable("maindata")
    public Map<String,Object> mainStatData(){
		List<User> users = findBestRanked();
		List<Tournament> tournaments = tournamentService.findLatest(5);
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("games", gameService.findAll());
		result.put("bestRatedUsers", users);
		result.put("lastTournaments",tournaments);

		return result;
    }
}
