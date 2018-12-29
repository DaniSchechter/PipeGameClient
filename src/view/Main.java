package view;
	
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import model.MyModel;
import settings.SettingsWrapper;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//System.out.println("1");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
			BorderPane root = (BorderPane)loader.load();
			GUIController guiController=loader.getController();//view
			Controller c = new Controller();//controller
			MyModel m = new MyModel();//model
			c.setModel(m);
			c.setView(guiController);
			guiController.addObserver(c);
			m.addObserver(c);
			//System.out.println("2");
			Scene scene = new Scene(root,400,400);
			//System.out.println("3");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//System.out.println("4");
			primaryStage.setScene(scene);
			//System.out.println("5");
			primaryStage.show();
			//System.out.println("6");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
