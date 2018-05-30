package io.disk_indexer.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			MainView appView = new MainView();
			Scene scene = new Scene(appView.getView());
			scene.getStylesheets().add(getClass().getResource("/css/jfoenix-design.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			stage.setTitle("Disk Catalogue");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
