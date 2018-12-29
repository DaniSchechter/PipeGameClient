package view;

import java.io.File;
import javafx.beans.value.ObservableValue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.Level;
import settings.SettingsWrapper;

public class GUIController extends Observable implements Initializable, View{
	
	@FXML
	private MazeDisplayer mazeData;
	private Level level;
	private Pair<Double> lastCellClicked;
	private Pair<Double> currentWindowSize;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			mazeData.addTypeOfCell('L', new Image(new FileInputStream(mazeData.getPipeCurl())));
			mazeData.addTypeOfCell('F', RotateImage(new Image(new FileInputStream(mazeData.getPipeCurl())),90));
			mazeData.addTypeOfCell('7', RotateImage(new Image(new FileInputStream(mazeData.getPipeCurl())),180));
			mazeData.addTypeOfCell('J', RotateImage(new Image(new FileInputStream(mazeData.getPipeCurl())),270));
			mazeData.addTypeOfCell('-', new Image(new FileInputStream(mazeData.getPipeLine())));
			mazeData.addTypeOfCell('|', RotateImage(new Image(new FileInputStream(mazeData.getPipeLine())),90));
			mazeData.addTypeOfCell('s', new Image(new FileInputStream(mazeData.getPipeS())));
			mazeData.addTypeOfCell('g', new Image(new FileInputStream(mazeData.getPipeG())));
			mazeData.addTypeOfCell(' ', new Image(new FileInputStream(mazeData.getPipeWall())));
		}catch(FileNotFoundException e) {System.out.println("File error: "+e.getMessage());}
		
		this.level=new Level();
		mazeData.setLevel(level);
		this.lastCellClicked=new Pair<Double>();
		this.currentWindowSize=new Pair<Double>(mazeData.getWidth(),mazeData.getHeight());
		
		mazeData.addEventFilter(MouseEvent.MOUSE_CLICKED, e->mazeData.requestFocus());
		mazeData.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				lastCellClicked.setFirst(event.getX());
				lastCellClicked.setSecond(event.getY());
				setChanged();
				notifyObservers();
			}
		});
		mazeData.getParent().sceneProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue s, Object o, Object n) {
				if (o == null && n != null) {
					mazeData.widthProperty().bind(mazeData.getParent().getScene().widthProperty().divide(1.4));
					mazeData.heightProperty().bind(mazeData.getParent().getScene().heightProperty().divide(1.4));
					mazeData.getParent().getScene().widthProperty().addListener(new ChangeListener() {

						@Override
						public void changed(ObservableValue arg0, Object arg1, Object arg2) {
							currentWindowSize.setFirst(mazeData.widthProperty().doubleValue());
							mazeData.redraw();

						}
					});
					mazeData.getParent().getScene().heightProperty().addListener(new ChangeListener() {

						@Override
						public void changed(ObservableValue arg0, Object arg1, Object arg2) {
							currentWindowSize.setSecond(mazeData.heightProperty().doubleValue());
							mazeData.redraw();
							
						}
					});
				}

			}
		});
	
	}
	
	@Override
	public Pair<Double> getLastCellClicked() {return this.lastCellClicked;}
	@Override
	public Pair<Double> getCurrentWindowSize() {return this.currentWindowSize;}


	public void solve()
	{
		setChanged();
		notifyObservers("solve");
	}

	@Override
	public Level getLevel() {return this.level;}
	
	@Override
	public void setLevel(Level level) {	
		if(!(this.level.equals(level))) {//load
			this.level=level;
			mazeData.setLevel(level);
		}
	}
	
	private Image RotateImage(Image image, double degrees) {
        ImageView iv = new ImageView(image);
        iv.setRotate(degrees);
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        Image rotatedImage = iv.snapshot(sp, null);
        return rotatedImage;
    }
	@Override
	public void loadlevel()
	{  
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose a maze level");
		fc.setInitialDirectory(new File(SettingsWrapper.getSetting().getInitialDirectory()));
		fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Pipe Level","*" + SettingsWrapper.getSetting().getFileChooserExtention()));
		File chosen = fc.showOpenDialog(null);
		setChanged();
		this.notifyObservers(chosen.toString());
	}

}
