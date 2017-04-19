package converters;

import domain.notifications.TeamInvitationNotification;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class TeamInvitationStringConverter implements Converter<TeamInvitationNotification, String> {
    @Override
    public String convert(TeamInvitationNotification teamInvitationNotification) {
        String result;

        if (teamInvitationNotification == null) {
            result = null;
        } else {
            result = String.valueOf(teamInvitationNotification.getId());
        }
        return result;

    }
}
