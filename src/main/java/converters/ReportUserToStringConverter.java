
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ReportUser;

@Component
@Transactional
public class ReportUserToStringConverter implements Converter<ReportUser, String> {

	@Override
	public String convert(ReportUser reportUser) {
		String result;

		if (reportUser == null) {
			result = null;
		} else {
			result = String.valueOf(reportUser.getId());
		}
		return result;

	}
}
