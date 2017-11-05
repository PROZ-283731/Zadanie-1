import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Environment {
	String name;
	ObservableList<User> users;
	ObservableMap<String, User> usernames;

	public Environment(String name) {
		this.name = name;
		users = FXCollections.observableArrayList();
		usernames = FXCollections.observableHashMap();
	}
	
	public void addUser(String username, String password) {
		users.add(new User(username, password));
		usernames.put(username, new User(username, password));
	}
	
	public ObservableList<User> getUsers() {
		return users;
	}
	
	public ObservableMap<String, User> getUsernames() {
		return usernames;
	}
}