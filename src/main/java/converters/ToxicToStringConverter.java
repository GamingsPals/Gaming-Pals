package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Toxic;

@Component
@Transactional
public class ToxicToStringConverter implements Converter<Toxic, String>{
	public String convert(Toxic toxic) {
		String result;
		if(toxic==null){
			result = null;
		}else{
			result = String.valueOf(toxic.getId());
		}
		return result;
	}

}
