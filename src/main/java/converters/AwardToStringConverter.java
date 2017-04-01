package converters;

import domain.Award;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AwardToStringConverter implements Converter<Award, String> {
    @Override
    public String convert(Award award) {
        String result;

        if (award == null) {
            result = null;
        } else {
            result = String.valueOf(award.getId());
        }
        return result;

    }
}
