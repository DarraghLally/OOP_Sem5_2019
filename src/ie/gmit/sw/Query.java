package ie.gmit.sw;

public class Query {
	private String text;
	private String Language;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public Query(String text, String language) {
		super();
		this.text = text;
		Language = language;
	}
	
}
