
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Language;
import repositories.LanguageRepository;

@Service
@Transactional
public class LanguageService {

	@Autowired
	private LanguageRepository languageRepository;


	public LanguageService() {
		super();
	}

	public Language create() {
		Language lang = new Language();
		return lang;
	}

	public Language save(Language language) {
		Assert.notNull(language);
		languageRepository.save(language);
		return language;
	}

	public void delete(Language language) {

		Assert.notNull(language);
		languageRepository.delete(language);

	}

	public Language findOne(int id) {

		Language result = languageRepository.findOne(id);

		Assert.notNull(result);

		return result;
	}

	public Collection<Language> findAll() {

		Collection<Language> result;
		result = languageRepository.findAll();

		Assert.notNull(result);

		return result;

	}
}
