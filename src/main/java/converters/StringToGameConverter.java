
package converters;

import domain.Award;
import domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.AwardRepository;
import repositories.GameRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToGameConverter implements Converter<String, Game> {

	@Autowired
	GameRepository gameRepository;


	@Override
	public Game convert(String text) {
		Game result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = gameRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
