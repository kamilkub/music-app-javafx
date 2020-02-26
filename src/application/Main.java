package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Main extends Application implements Initializable {

	private File SONG_PATH;
	private List<File> LIST_OF_PREVIOUS_SONG_PATHS = new ArrayList<>();
	private int PREVIOUS_PATH_TRACKER = 0;
	private Player MUSIC_PLAYER;
	private FileInputStream STREAM_MUSIC;
	private double SONG_TIME;

	@FXML
	private JFXProgressBar progressbar;

	@FXML
	private ImageView platemusic;

	@FXML
	private JFXButton play;

	@FXML
	private JFXButton previous;

	@FXML
	private JFXButton stop;

	@FXML
	private JFXButton settings;

	@FXML
	private JFXButton close;

	@FXML
	private JFXButton source;

	@Override
	public void start(Stage primaryStage) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root, 354, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e -> exit_click());
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image IMAGE_1 = new Image("Music Record_104px.png");
		platemusic.setImage(IMAGE_1);

	}

	public void source_click() {

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Pick some mp3");
			chooser.showOpenDialog(null);
			SONG_PATH = chooser.getSelectedFile();
			LIST_OF_PREVIOUS_SONG_PATHS.add(SONG_PATH);
		System.out.println(LIST_OF_PREVIOUS_SONG_PATHS.size());

			if (!SONG_PATH.getName().endsWith(".mp3")) {
				JOptionPane.showMessageDialog(null, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
			}

	}

	public void start_click() throws FileNotFoundException, JavaLayerException {

		if(SONG_PATH != null) {
			STREAM_MUSIC = new FileInputStream(SONG_PATH);
			MUSIC_PLAYER = new Player(STREAM_MUSIC);
			progressbar.setProgress(-1.0f);

			if (play.getOnAction() != null) {
				play.setDisable(true);
			}

			new Thread(() -> {

				try {
					MUSIC_PLAYER.play();
					SONG_TIME = MUSIC_PLAYER.getPosition();
					if (MUSIC_PLAYER.isComplete())
						STREAM_MUSIC.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				progressbar.setProgress(SONG_TIME);
			}).start();

		} else {
			JOptionPane.showMessageDialog(null, "No file has been selected", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void previous_click() {
		PREVIOUS_PATH_TRACKER = LIST_OF_PREVIOUS_SONG_PATHS.size()-1;

		if(LIST_OF_PREVIOUS_SONG_PATHS.size() == 0)
			JOptionPane.showMessageDialog(null, "No previous music", "Error", JOptionPane.ERROR_MESSAGE);

		if(LIST_OF_PREVIOUS_SONG_PATHS.size() == 1){
			SONG_PATH = LIST_OF_PREVIOUS_SONG_PATHS.get(0);
		} else if(LIST_OF_PREVIOUS_SONG_PATHS.size() > 1) {
			PREVIOUS_PATH_TRACKER--;
			SONG_PATH = LIST_OF_PREVIOUS_SONG_PATHS.get(PREVIOUS_PATH_TRACKER);
		}

	}

	public void settings_click() {


	}

	public void stop_click() throws IOException {

		try {
			MUSIC_PLAYER.close();
			STREAM_MUSIC.close();
			progressbar.setProgress(0);
			play.setDisable(false);
		} catch (Exception ed) {
			JOptionPane.showMessageDialog(null, "Choose File", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}


	public void exit_click() {
		System.exit(0);
	}
	
	
		
	
	

}
