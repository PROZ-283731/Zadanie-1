import java.util.List;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class LogonDialog {
	
	// FIELDS
	Dialog<ButtonType> dialog;
	List<Environment> envs;
	ChoiceBox<Environment> cbxEnv;
	ComboBox<User> cbxUsers;
	PasswordField passField;
	ButtonType loginButtonType;
	ButtonType cancelButtonType;
	boolean envFilled;
	boolean userFilled;
	boolean passFilled;
	
	// METHODS
	public LogonDialog(String windowName, String windowDesc) {		
		Label envLabel = new Label("Środowisko:");
		Label userLabel = new Label("Użytkownik:");
		Label passLabel = new Label("Hasło:");
		
		envFilled = false;
		userFilled = false;
		passFilled = false;
		
		cbxEnv = new ChoiceBox<>(getEnvs());
		cbxEnv.setConverter(new EnvironmentConverter());
		cbxEnv.valueProperty().addListener(
				(observable, oldVal, newVal) -> cbxEnv_Changed(newVal)
		);
		
		cbxUsers = new ComboBox<>();
		cbxUsers.setEditable(true);
		cbxUsers.valueProperty().addListener(
				(observable, oldVal, newVal) -> cbxUsers_Changed(newVal)
		);
		
		passField = new PasswordField();
		passField.textProperty().addListener(
				(observable, oldVal, newVal) -> passField_Changed(newVal)
		);
		
		GridPane grid = new GridPane();
		grid.addRow(0, envLabel, cbxEnv);
		grid.addRow(1, userLabel, cbxUsers);
		grid.addRow(2, passLabel, passField);
		
		dialog = new Dialog<>();
		dialog.setHeaderText(windowDesc);
		dialog.setTitle(windowName);
		dialog.getDialogPane().setContent(grid);
		
		Image image = new Image(ClassLoader.getSystemResourceAsStream("Login_64x.png"));
		ImageView imageView = new ImageView(image);
		dialog.setGraphic(imageView);
		
		loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		cancelButtonType = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(cancelButtonType);
		dialog.getDialogPane().getButtonTypes().add(loginButtonType);
		boolean disabled = cbxUsers.getSelectionModel().isEmpty() || cbxEnv.getSelectionModel().isEmpty() || passField.getSelectedText().isEmpty();
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
	}
	
	private void passField_Changed(String newVal) {
		
	}

	private void cbxUsers_Changed(User newVal) {

	}

	private void cbxEnv_Changed(Environment newVal) {
		cbxEnv.setValue(newVal);
		cbxUsers.setItems(cbxEnv.getValue().users);
	}

	private ObservableList<Environment> getEnvs() {
		ObservableList<Environment> tmp = FXCollections.observableArrayList();
		Environment prodEnv = new Environment("Produkcyjne");
		Environment testEnv = new Environment("Testowe");
		Environment devEnv = new Environment("Deweloperskie");
		
		prodEnv.addUser("produser1", "qwerty");
		testEnv.addUser("testuser1", "azerty");
		devEnv.addUser("devuser1", "qazwsx");
		
		tmp.add(prodEnv);
		tmp.add(testEnv);
		tmp.add(devEnv);

		return tmp;
	}
	
	/*public Optional<Pair<Environment, String>> showAndWait() {		
		Optional<ButtonType> result = dialog.showAndWait();
		return resultConverter(result);
	}
	
	private Pair<Environment, String> resultConverter(Optional<ButtonType> result) {
		if (result == loginButtonType) {
			if (users.isPassCorrect(cbxEnv.getValue(), cbxUsers.getValue(), passField.getText())) {
				return new Pair<Environment, String>(cbxEnv.getValue(),cbxUsers.getValue());
			}
		}
		return null;
	}*/
}