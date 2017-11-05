import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.util.Pair;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;


public class LogonDialog {
	
	// FIELDS
	Dialog<ButtonType> dialog;
	List<Environment> envs;
	ChoiceBox<Environment> cbxEnv;
	ComboBox<String> cbxUsers;
	PasswordField passField;
	
	// METHODS
	public LogonDialog(String windowName, String windowDesc) {
		Label envLabel = new Label("Œrodowisko:");
		Label userLabel = new Label("U¿ytkownik:");
		Label passLabel = new Label("Has³o:");
		
		cbxEnv = new ChoiceBox<>(FXCollections.observableArrayList(getEnvs()));
		cbxEnv.setConverter(new EnvironmentConverter());
		
		dialog = new Dialog<>();
		dialog.setHeaderText(windowName);
		dialog.setContentText(windowDesc);
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(loginButtonType);
		boolean disabled = false; // computed based on content of text fields, for example
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
	}
	
	private List<Environment> getEnvs() {
		List<Environment> tmp = new ArrayList<>();
		Environment prodEnv = new Environment("Produkcyjne");
		Environment testEnv = new Environment("Testowe");
		Environment devEnv = new Environment("Deweloperskie");
		
		prodEnv.addUser("produser1");
		testEnv.addUser("testuser1");
		devEnv.addUser("devuser1");
		
		tmp.add(prodEnv);
		tmp.add(testEnv);
		tmp.add(devEnv);

		return tmp;
	}
	
	public Optional<Pair<Environment, String>> showAndWait() {		
		Optional<ButtonType> result = dialog.showAndWait();
		return resultConverter(result);
	}
	
	private Optional<Pair<Environment, String>> resultConverter(Optional<ButtonType> result) {
		if (result == loginButtonType) {
			if (users.isPassCorrect(cbxEnv.getValue(), cbxUsers.getValue(), passField.getText())) {
				return new Optional<Pair<Environment, String>>(cbxEnv.getValue(),cbxUsers.getValue());
			}
		}
		return null;
	}
}