
package converters;

import domain.Participes;
import domain.ReportMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import repositories.ReportMatchRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToReportMatchConverter implements Converter<String, ReportMatch> {

	@Autowired
	ReportMatchRepository reportMatchRepository;


	@Override
	public ReportMatch convert(String text) {
		ReportMatch result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = reportMatchRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
