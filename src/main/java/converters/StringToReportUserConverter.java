
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.ReportUser;
import repositories.ReportUserRepository;

@Component
@Transactional
public class StringToReportUserConverter implements Converter<String, ReportUser> {

	@Autowired
	ReportUserRepository reportUserRepository;


	@Override
	public ReportUser convert(String text) {
		ReportUser result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = reportUserRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
