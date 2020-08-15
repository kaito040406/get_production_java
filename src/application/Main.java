package application;

import db.categoryData;
import db.ngWordData;
import db.searchLinkData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,650,650);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			categoryData.startDbCon();
			ngWordData.startDbCon();
			searchLinkData.startDbCon();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
