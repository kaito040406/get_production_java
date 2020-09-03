package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import db.categoryData;
import db.ngWordData;
import db.priceData;
import db.searchLinkData;
import db.textData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws SecurityException, IOException {
		Logger logger = Logger.getLogger("GB");
//		Date now = new Date();
//		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-ddd");
//		String nowS = day.format(now);
//		Handler handler = new FileHandler("logs/" + nowS + ".log");
//		Formatter formatter =  new SimpleFormatter();
//		handler.setFormatter(formatter);
		logger.log(Level.INFO,"アプリケーション起動");
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,650,650);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			categoryData.startDbCon();
			ngWordData.startDbCon();
			searchLinkData.startDbCon();
			priceData.startDbCon();
			textData.startDbCon();
		} catch(Exception e) {
			logger.log(Level.WARNING,"アプリケーション起動失敗/n" + e);
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
