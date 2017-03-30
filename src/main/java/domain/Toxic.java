package domain;

import java.util.Collection;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Toxic extends DomainEntity{
	
	// Constructors
	public Toxic() {
		super();
	}
	
	// Attributes
	private Collection<String> keywords;
	
	@NotNull
	@ElementCollection
	public Collection<String> getKeywords(){
		return keywords;
	}
	public void setKeywords(Collection<String> keywords){
		this.keywords=keywords;
	}

}
