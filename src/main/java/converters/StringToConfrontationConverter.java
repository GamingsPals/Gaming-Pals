
package converters;

import domain.Confrontation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.ConfrontationRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToConfrontationConverter implements Converter<String, Confrontation> {

	@Autowired
	ConfrontationRepository confrontationRepository;


	@Override
	public Confrontation convert(String text) {
		Confrontation result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = confrontationRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
