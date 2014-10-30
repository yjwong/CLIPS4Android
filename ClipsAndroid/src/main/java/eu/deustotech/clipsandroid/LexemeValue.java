package eu.deustotech.clipsandroid;

public class LexemeValue extends PrimitiveValue {
	protected LexemeValue(String value) {
		super(value);
	}
	
	public String lexemeValue() {
		return (String) getValue();
	}
}
