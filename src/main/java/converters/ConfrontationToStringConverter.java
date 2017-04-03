package converters;

import domain.Confrontation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ConfrontationToStringConverter  implements Converter<Confrontation, String> {
    @Override
    public String convert(Confrontation confrontation) {
        String result;

        if (confrontation == null) {
            result = null;
        } else {
            result = String.valueOf(confrontation.getId());
        }
        return result;

    }
}
