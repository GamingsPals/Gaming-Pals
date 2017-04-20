package converters;

import domain.Award;
import domain.Game;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class GameToStringConverter implements Converter<Game, String> {
    @Override
    public String convert(Game game) {
        String result;

        if (game == null) {
            result = null;
        } else {
            result = String.valueOf(game.getId());
        }
        return result;

    }
}
