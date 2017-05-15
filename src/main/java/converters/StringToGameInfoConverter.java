
package converters;

import domain.Game;
import domain.GameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.GameInfoRepository;
import repositories.GameRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToGameInfoConverter implements Converter<String, GameInfo> {

	@Autowired
	GameInfoRepository gameInfoRepository;


	@Override
	public GameInfo convert(String text) {
		GameInfo result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = gameInfoRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
