package ie.gmit.sw;

/**
 * 
 * @author Darragh Lally, G00220290
 * @version 1.0
 * @since Java 1.8
 * 
 * This class is used to so we can pass objects into the blocking queue
 * 1) Dataset
 * 2) Poison
 *
 */
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

	/**
	 * Constructor takes in the Langauge Text and Language Type
	 * @param text
	 * @param language
	 * 
	 * text = Language text, ie before the '@' symbol from Dataset
	 * language = Language type, ie after the '@' symbol 
	 */
	public Query(String text, String language) {
		super();
		this.text = text;
		Language = language;
	}
	
}
