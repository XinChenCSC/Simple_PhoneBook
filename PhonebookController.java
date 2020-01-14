package application;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PhonebookController {
	@FXML
	private TextField name;

	@FXML
	private TextField state;

	@FXML
	private TextField phone;

	@FXML
	private Button minus;

	@FXML
	private Button plus;

	@FXML
	private Button left;

	@FXML
	private Button right;

	@FXML
	private Button load;

	@FXML
	private Button serilalize;

	@FXML
	private Label filename;
	@FXML
	private Button exit;
	@FXML
	private Label counter;

	private int moveControl;
	private int count;
	RecordList b;

	public void initialize() {
		b = new RecordList();
		count = 0;
		moveControl = 0;
		name.setDisable(true);
		state.setDisable(true);
		phone.setDisable(true);
		minus.setDisable(true);
		plus.setDisable(true);
		left.setDisable(true);
		right.setDisable(true);
		serilalize.setDisable(true);
	}

	static boolean answer;
	static boolean answer1;

	public boolean ConfirmBox(String title, String message) {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label = new Label();
		label.setText(message);
		Button yesButton = new Button("Yes");
		Button noButton = new Button("Cancel");
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

		return answer;

	}

	public boolean AlertBox(String title, String message) {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label = new Label();
		label.setText(message);
		Button okButton = new Button("Ok");
		okButton.setOnAction(e -> window.close());
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, okButton);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

		return answer1;

	}

	public void Enable(ActionEvent event) {
		plus.setDisable(false);
	}

	public void exit(ActionEvent event) {

		Boolean confirm = ConfirmBox("Exit", "Are you sure you want to exit");
		if (confirm) {
			System.exit(0);
		}
	}

	public void addRecord(ActionEvent event) {
		Record record = new Record();
		if (name.getText().matches("([A-Z][a-zA-Z]{2,}\\s*)+")) {
			record.setName(name.getText());
		} else {
			AlertBox("Invalid value",
					"Invalid Name.Names should start with an uppercase letter followed by at least two character");
		}
		if (state.getText().matches("([A-Z][a-zA-Z]{2,}\\s[A-Z][a-zA-Z]{2,})|[A-Z][a-zA-Z]{2,}")) {
			record.setState(state.getText());
		} else {
			AlertBox("Invalid value", "Invalid State.State should consist of one or two words");
		}
		if (phone.getText().matches("\\([1-9][0-9]{2}\\)\\s[1-9][0-9]{2}\\s\\-\\s[0-9]{4}")) {
			record.setPhone(phone.getText());
		} else {
			AlertBox("Invalid value", "Invalid Phone number.Ex (212) 555 - 1234");
		}
		b.setList(record);
		count++;
		moveControl=count-1;
		counter.setText(count + " of " + count);
		left.setDisable(false);
		
		
	}

	public void deleteRecord(ActionEvent event) {
		ArrayList<Record> d = b.getList();
		d.remove(count - 1);
		
		b.setList2(d);
		count--;
		moveControl--;
		name.setText(d.get(count - 1).getName());
		state.setText(d.get(count - 1).getState());
		phone.setText(d.get(count - 1).getPhone());

		counter.setText(count + " of " + count);
	}

	public void goRight(ActionEvent event) {
		right.setDisable(false);
		ArrayList<Record> d = b.getList();
		moveControl++;
		if(moveControl+1 == count)
		{
			right.setDisable(true);
		}
	
		name.setText(d.get(moveControl).getName());
		state.setText(d.get(moveControl).getState());
		phone.setText(d.get(moveControl).getPhone());
		left.setDisable(false);
		
		counter.setText(moveControl+1 + " of " + count);
	
	}

	public void goLeft(ActionEvent event) {
		right.setDisable(false);
		if(moveControl == 1)
		{
			left.setDisable(true);
		}
		ArrayList<Record> d = b.getList();
		
		moveControl--;
	
		name.setText(d.get(moveControl).getName());
		state.setText(d.get(moveControl).getState());
		phone.setText(d.get(moveControl).getPhone());

		counter.setText(moveControl+1 + " of " + count);
	}
	public void saveFile(ActionEvent event) {
		FileChooser a = new FileChooser();
		ArrayList<Record> d = b.getList();
		a.setTitle("Save File");
		a.setInitialFileName("My File");
		a.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"),
				new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File file = a.showSaveDialog(null);
		a.setInitialDirectory(file.getParentFile());
		try {
			FileWriter fw = new FileWriter(file);
			for(int x=0; x < count; x++) {
			
				fw.write(d.get(x).getName());
				fw.write(d.get(x).getState());
				fw.write(d.get(x).getPhone());
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void getFile(ActionEvent event) throws IOException {
		
		FileChooser a = new FileChooser();

		boolean fileError = false;

	
		a.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"),
				new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File file = a.showOpenDialog(null);
		BufferedReader br = new BufferedReader(new FileReader(file));
		if (file != null) {
			filename.setText("File selected: " + file.getName());

			String st;

			while ((st = br.readLine()) != null) {
				Record c = new Record();
				String[] arr = st.split(",");
				for (int x = 0; x < 3; x++) {
			
					if (arr[x].matches("([A-Z][a-zA-Z]{2,}\\s*)+") && c.getName() == null) {
						c.setName(arr[x]);
					} else if (arr[x].matches("([A-Z][a-zA-Z]{2,}\\s[A-Z][a-zA-Z]{2,})|[A-Z][a-zA-Z]{2,}")) {
						c.setState(arr[x]);
					} else if (arr[x].matches("\\([1-9][0-9]{2}\\)\\s[1-9][0-9]{2}\\s\\-\\s[0-9]{4}")) {
						c.setPhone(arr[x]);
					} else {
						AlertBox("Invalid input", "There is a error in your file");
						fileError = true;
					}
				}

				b.setList(c);
			
				count++;
				
				
			}

		} else {
			System.out.println("Error");
		}
		br.close();
		ArrayList<Record> d = b.getList();
		
		name.setText(d.get(0).getName());
		state.setText(d.get(0).getState());
		phone.setText(d.get(0).getPhone());

			
		
		
		if (!fileError) {
			counter.setText("1 of " + count);
			name.setDisable(false);
			state.setDisable(false);
			phone.setDisable(false);
			minus.setDisable(false);
			serilalize.setDisable(false);
			plus.setDisable(false);
			right.setDisable(false);
		}

	}

}
