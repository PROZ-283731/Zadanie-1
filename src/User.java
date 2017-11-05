
public class User {
	String username;
	String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public boolean isPassCorrect(String password) {
		return this.password == password;
	}
	
	public String toString() {
		return username;
	}
}
