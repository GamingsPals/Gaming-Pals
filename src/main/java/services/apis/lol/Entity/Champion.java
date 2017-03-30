package services.apis.lol.Entity;

public class Champion {
	private Integer id;
	private String name;
	private String key;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
        this.key = key;
    }
}
