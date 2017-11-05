import javafx.util.StringConverter;

public class EnvironmentConverter extends StringConverter<Environment>{
	public Environment fromString(String src) {
		return new Environment(src);
	}

	public String toString(Environment src) {
		return src.name;
	}
}
