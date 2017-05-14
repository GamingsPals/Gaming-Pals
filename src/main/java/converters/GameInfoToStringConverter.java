package converters;

import domain.Game;
import domain.GameInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class GameInfoToStringConverter implements Converter<GameInfo, String> {
    @Override
    public String convert(GameInfo gameInfo) {
        String result;

        if (gameInfo == null) {
            result = null;
        } else {
            result = String.valueOf(gameInfo.getId());
        }
        return result;

    }
}
