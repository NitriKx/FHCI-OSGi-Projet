package m2dl.osgi.editor.interfaces;

public interface Tokenizer {
	
	public enum Type {
		JAVA,
		CSS
	}
	
	public String tokenize(String textToTokenize);
	
	public Type getType();
}
