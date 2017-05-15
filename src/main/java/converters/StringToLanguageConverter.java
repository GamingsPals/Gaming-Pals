
package converters;

import domain.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.LanguageRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToLanguageConverter implements Converter<String, Language> {

	@Autowired
	LanguageRepository languageRepository;


	@Override
	public Language convert(String text) {
		Language result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = languageRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
