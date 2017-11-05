import java.util.Optional;
import javafx.util.Pair;
import javafx.application.Application;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		//Optional<ButtonType> result = new LogonDialog("Logowanie", "Logowanie do systemu STYLEman").dialog.showAndWait();
		Optional<Pair<Environment,String>> result = (new LogonDialog("Logowanie", "Logowanie do systemu STYLEman")).showAndWait();
		if (result.isPresent())
			System.out.println("Logged in");
		else
			System.out.println("Login failed");
	}
	
	@Override
	public void stop() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}