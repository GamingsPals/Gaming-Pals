package converters;

import domain.feedback.Feedback;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class FeedbackToStringConverter implements Converter<Feedback, String> {
    @Override
    public String convert(Feedback feedback) {
        String result;

        if (feedback == null) {
            result = null;
        } else {
            result = String.valueOf(feedback.getId());
        }
        return result;

    }
}
