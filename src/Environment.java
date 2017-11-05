import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Environment class with authentication data
 * 
 * @author Michał Dziewulski (M.Dziewulski@stud.elka.pw.edu.pl)
 * @version 1.0
 */
public class Environment {
	String name;
	ObservableList<String> users;
	Map<String, String> passwords;

	/**
	 * Environment constructor
	 * @param name  environment name string
	 */
	public Environment(String name) {
		this.name = name;
		users = FXCollections.observableArrayList();
		passwords = new HashMap<>();
	}
	
	/**
	 * Add user data to correct structures
	 * @param username  username String
	 * @param password  password String
	 */
	public void addUser(String username, String password) {
		users.add(username);
		passwords.put(username, password);
	}
	
	/**
	 * Username list getter
	 * @return
	 */
	public ObservableList<String> getUsers() {
		return users;
	}
	
	@Override
	public String toString() {
		return name;
	}
}