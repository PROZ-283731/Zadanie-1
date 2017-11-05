import java.util.Optional;
import javafx.util.Pair;
import javafx.application.Application;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Optional<Pair<Environment,String>> result = (new LogonDialog("Logowanie", "Logowanie do systemu STYLEman")).showAndWait();
		
		if (result.isPresent()) {
			
		}
		
		else {
			
		}
	}
	
	@Override
	public void stop() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}