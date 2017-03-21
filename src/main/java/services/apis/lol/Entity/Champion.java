package services.apis.lol.Entity;

public class Champion {
	private Integer id;
	private String name;
	private String urlImage;
	
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
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	@Override
	public String toString() {
		return "Champion [id=" + id + ", name=" + name + ", urlImage=" + urlImage + "]";
	}
}
