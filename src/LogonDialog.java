/**
 * LogonDialog window based on JavaFX Dialog<> class
 * 
 * @author Michał Dziewulski (M.Dziewulski@stud.elka.pw.edu.pl)
 * @version 1.0
 */

import java.util.List;
import java.util.Optional;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;


public class LogonDialog {
	
	// FIELDS
	Dialog<ButtonType> dialog;
	List<Environment> envs;
	ChoiceBox<Environment> cbxEnv;
	ComboBox<String> cbxUsers;
	PasswordField passField;
	ButtonType loginButtonType;
	ButtonType cancelButtonType;

	
	// METHODS
	/**
	 * LogonDialog constructor
	 * @param windowName  the string with the dialog window name, will be displayed on a bar
	 * @param windowDesc  the string with the dialog window description, will be displayed above the main content
	 */
	public LogonDialog(String windowName, String windowDesc) {
		dialog = new Dialog<>();
		cbxEnv = new ChoiceBox<>(getEnvs());
		cbxUsers = new ComboBox<>();
		passField = new PasswordField();		
		loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		cancelButtonType = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);
		
		draw(windowName, windowDesc);
	}
	
	/**
	 * Drawing the content of the dialog window
	 * @param windowName  the string with the dialog window name, will be displayed on a bar
	 * @param windowDesc  the string with the dialog window description, will be displayed above the main content
	 */
	private void draw(String windowName, String windowDesc) {
		// Labels
		Label envLabel = new Label("Środowisko:");
		Label userLabel = new Label("Użytkownik:");
		Label passLabel = new Label("Hasło:");
		
		// Choice box
		cbxEnv.valueProperty().addListener(
				(observable, oldVal, newVal) -> cbxEnv_Changed(newVal)
		);
		
		// Combo box
		cbxUsers.setEditable(true);
		cbxUsers.valueProperty().addListener(
				(observable, oldVal, newVal) -> cbxUsers_Changed(newVal)
		);
		
		// Password field
		passField.textProperty().addListener(
				(observable, oldVal, newVal) -> passField_Changed(newVal)
		);
		
		// Grid pane
		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(envLabel, 0, 0);
		grid.add(userLabel, 0, 1);
		grid.add(passLabel, 0, 2);
		grid.add(cbxEnv, 1, 0);
		grid.add(cbxUsers, 1, 1);
		grid.add(passField, 1, 2);
		
		
		// Dialog
		dialog.setHeaderText(windowDesc);
		dialog.setTitle(windowName);
		dialog.getDialogPane().setContent(grid);
		
		// Image
		Image image = new Image(ClassLoader.getSystemResourceAsStream("Login_64x.png"));
		ImageView imageView = new ImageView(image);
		dialog.setGraphic(imageView);
		
		// Buttons
		dialog.getDialogPane().getButtonTypes().add(cancelButtonType);
		dialog.getDialogPane().getButtonTypes().add(loginButtonType);
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);
	}
	
	// Event handlers
	
	/**
	 * Event handler for password field value change
	 * @param newVal  new password value
	 */
	private void passField_Changed(String newVal) {
		passField.setText(newVal);
		boolean userValue = cbxUsers.getValue() != null;
		if (userValue)
			userValue = cbxUsers.getValue().isEmpty();
		boolean disabled = userValue || cbxEnv.getSelectionModel().isEmpty() || newVal.isEmpty();
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
	}

	/**
	 * Event handler for username combo box value change
	 * @param newVal  new username value
	 */
	private void cbxUsers_Changed(String newVal) {
		cbxUsers.setValue(newVal);
		boolean userValue = newVal != null;
		if (userValue)
			userValue = newVal.isEmpty();
		boolean disabled = userValue || cbxEnv.getSelectionModel().isEmpty() || passField.getText().isEmpty();
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
	}

	/**
	 * Event handler for environment choice box value change
	 * @param newVal  new environment value
	 */
	private void cbxEnv_Changed(Environment newVal) {
		cbxEnv.setValue(newVal);
		cbxUsers.setItems(cbxEnv.getValue().users);
		cbxUsers.setValue("");
		boolean userValue = cbxUsers.getValue() != null;
		if (userValue)
			userValue = cbxUsers.getValue().isEmpty();
		boolean disabled = userValue || cbxEnv.getSelectionModel().isEmpty() || passField.getText().isEmpty();
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
	}

	// Data generation
	
	/**
	 * Generation of sample data for demo purposes
	 * @return ObservableList of Environment object
	 */
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
	
	// Logic
	
	/**
	 * Custom showAndWait method for the dialog window
	 * @return Optional pair of Environment and username String if login successful, else Optional.empty()
	 */
	public Optional<Pair<Environment, String>> showAndWait() {		
		Optional<ButtonType> result = dialog.showAndWait();
		return resultConverter(result);
	}
	
	/**
	 * Convert standard dialog.showAndWait return value to Optional Pair of Environment and String
	 * @param result  result of dialog.showAndWait() method
	 * @return Optional pair of Environment and username String if login successful, else Optional.empty()
	 */
	private Optional<Pair<Environment, String>> resultConverter(Optional<ButtonType> result) {
		if (result.get() == loginButtonType) {
			if (isPassCorrect(cbxEnv.getValue(), cbxUsers.getValue(), passField.getText())) {
				return Optional.of(new Pair<Environment, String>(cbxEnv.getValue(),cbxUsers.getValue()));
			}
		}
		return Optional.empty();
	}

	/**
	 * Validate login information
	 * @param env  Enviroment Object
	 * @param username  username String
	 * @param password  password String
	 * @return boolean value, true if data is correct
	 */
	private boolean isPassCorrect(Environment env, String username, String password) {
		String sourcePass = env.passwords.get(username);
		if (sourcePass == null)
			return false;
		return sourcePass.equals(password);
	}
}