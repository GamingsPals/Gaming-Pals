package converters;

import domain.ReportMatch;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ReportMatchToStringConverter implements Converter<ReportMatch, String> {
    @Override
    public String convert(ReportMatch reportMatch) {
        String result;

        if (reportMatch == null) {
            result = null;
        } else {
            result = String.valueOf(reportMatch.getId());
        }
        return result;

    }
}
