
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.User;
import repositories.UserRepository;

@Component
@Transactional
public class StringToUserConverter implements Converter<String, User> {

	@Autowired
	UserRepository userRepository;


	@Override
	public User convert(String text) {
		User result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				result = userRepository.findOne(Integer.valueOf(text));
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
