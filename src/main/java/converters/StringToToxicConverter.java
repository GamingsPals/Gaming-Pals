package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ToxicRepository;

import domain.Toxic;

@Component
@Transactional
public class StringToToxicConverter implements Converter<String, Toxic>{

	@Autowired
	ToxicRepository toxicRepository;

	public Toxic convert(String text) {
		Toxic result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
			id = Integer.valueOf(text);
			result = toxicRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
