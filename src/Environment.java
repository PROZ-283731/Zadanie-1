import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environment {
	String name;
	ObservableList<String> users;
	Map<String, String> passwords;

	public Environment(String name) {
		this.name = name;
		users = FXCollections.observableArrayList();
		passwords = new HashMap<>();
	}
	
	public void addUser(String username, String password) {
		users.add(username);
		passwords.put(username, password);
	}
	
	public ObservableList<String> getUsers() {
		return users;
	}
	
	@Override
	public String toString() {
		return name;
	}
}