
package converters;

import domain.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.AwardRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToAwardConverter implements Converter<String, Award> {

	@Autowired
	AwardRepository awardRepository;


	@Override
	public Award convert(String text) {
		Award result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = awardRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
