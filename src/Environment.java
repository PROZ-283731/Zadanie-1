import java.util.ArrayList;
import java.util.List;

public class Environment {
	String name;
	List<String> users;
	
	public Environment(String name) {
		this.name = name;
		users = new ArrayList<>();
	}
	
	public void addUser(String username) {
		users.add(username);
	}
}