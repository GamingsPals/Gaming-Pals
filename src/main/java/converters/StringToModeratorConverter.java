
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Moderator;
import repositories.ModeratorRepository;

@Component
@Transactional
public class StringToModeratorConverter implements Converter<String, Moderator> {

	@Autowired
	ModeratorRepository moderatorRepository;


	@Override
	public Moderator convert(String text) {
		Moderator result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = moderatorRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
