import java.util.Optional;
import javafx.util.Pair;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class
 * @author Michał Dziewulski (M.Dziewulski@stud.elka.pw.edu.pl)
 * @version 1.0
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		System.out.println("Program started.");
		Optional<Pair<Environment,String>> result = (new LogonDialog("Logowanie", "Logowanie do systemu STYLEman")).showAndWait();
		if (result.isPresent())
			System.out.println("Logged in.");
		else
			System.out.println("Login failed.");
	}
	
	@Override
	public void stop()
	{
		System.out.println("Program finished.");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}