
package converters;

import domain.notifications.TeamInvitationNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.notifications.TeamInvitationNotificationRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToTeamInvitationConverter implements Converter<String, TeamInvitationNotification> {

	@Autowired
	TeamInvitationNotificationRepository teamInvitationNotificationRepository;


	@Override
	public TeamInvitationNotification convert(String text) {
		TeamInvitationNotification result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = teamInvitationNotificationRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
