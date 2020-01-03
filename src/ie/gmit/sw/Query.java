package ie.gmit.sw;

/**
 * This class is used to so we can pass objects into the blocking queue<br>
 * 1) Dataset<br>
 * 2) Poison<br>
 * @author Darragh Lally, G00220290
 * @version 1.0
 * @since Java 1.8
 * 
 */
public class Query {
	//Variables
	private String text;
	private String Language;
	
	//Accessor methods
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
	 * @param text Language text, ie before the '@' symbol from Dataset
	 * @param language Language type, ie after the '@' symbol 

	 */
	public Query(String text, String language) {
		super();
		this.text = text;
		Language = language;
	}
	
}
