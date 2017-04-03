
package converters;

import domain.Participes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.ParticipesRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToParticipesConverter implements Converter<String, Participes> {

	@Autowired
	ParticipesRepository participesRepository;


	@Override
	public Participes convert(String text) {
		Participes result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = participesRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
