package io.disk_indexer.ui;

import com.airhacks.afterburner.injection.Injector;

import io.disk_indexer.ui.fileicons.FileExtensionMapper;
import io.disk_indexer.ui.fileicons.FileMapper;
import io.disk_indexer.ui.pages.MainView;
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

	@Override
	public void stop() throws Exception {
		DataBridge dataBridge = Injector.instantiateModelOrService(DataBridge.class);
		dataBridge.closeConnection();

		Injector.forgetAll();
	}

	private static void configureServices() {
		Injector.setModelOrService(FileMapper.class, new FileExtensionMapper());
	}

	public static void main(String[] args) {
		configureServices();
		launch(args);
	}
}
