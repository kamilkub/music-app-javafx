package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.sun.media.jfxmedia.MediaPlayer;

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

		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Pick some mp3");
			chooser.showOpenDialog(null);
			SONG_PATH = chooser.getSelectedFile();

			if (!SONG_PATH.getName().endsWith(".mp3")) {
				JOptionPane.showMessageDialog(null, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e) {

		}
	}

	public void start_click() throws FileNotFoundException, JavaLayerException {
               
		try {
            
			STREAM_MUSIC = new FileInputStream(SONG_PATH);
			MUSIC_PLAYER = new Player(STREAM_MUSIC);
            progressbar.setProgress(-1.0f);
			
			if (play.getOnAction() != null) {
				play.setDisable(true);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Choose File", "Error", JOptionPane.ERROR_MESSAGE);

		}

		new Thread() {
			public void run() {
                  
				try {
					MUSIC_PLAYER.play();
					SONG_TIME = MUSIC_PLAYER.getPosition();
					if (MUSIC_PLAYER.isComplete())
					STREAM_MUSIC.close();

				} catch (Exception e) {

				}
				progressbar.setProgress(SONG_TIME);
			}
		}.start();

	}

	public void previous_click() {

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
