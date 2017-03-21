
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Tournament;

@Component
@Transactional
public class TournamentToStringConverter implements Converter<Tournament, String> {

	@Override
	public String convert(Tournament tournament) {
		String result;

		if (tournament == null) {
			result = null;
		} else {
			result = String.valueOf(tournament.getId());
		}
		return result;

	}
}
