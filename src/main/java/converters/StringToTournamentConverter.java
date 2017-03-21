
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Tournament;
import repositories.TournamentRepository;

@Component
@Transactional
public class StringToTournamentConverter implements Converter<String, Tournament> {

	@Autowired
	TournamentRepository tournamentRepository;


	@Override
	public Tournament convert(String text) {
		Tournament result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = tournamentRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
