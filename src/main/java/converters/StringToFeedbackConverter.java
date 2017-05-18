
package converters;

import domain.feedback.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.feedback.FeedbackRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToFeedbackConverter implements Converter<String, Feedback> {

	@Autowired
	FeedbackRepository feedbackRepository;


	@Override
	public Feedback convert(String text) {
		Feedback result;
		int id;
		System.out.println(text);
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = feedbackRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
