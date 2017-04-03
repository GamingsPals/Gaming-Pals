package converters;

import domain.Confrontation;
import domain.Participes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ParticipesToStringConverter implements Converter<Participes, String> {
    @Override
    public String convert(Participes participes) {
        String result;

        if (participes == null) {
            result = null;
        } else {
            result = String.valueOf(participes.getId());
        }
        return result;

    }
}
