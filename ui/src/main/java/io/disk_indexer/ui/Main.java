package io.disk_indexer.ui;

import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@FXMLViewFlowContext
	private ViewFlowContext flowContext;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Hi");

			Flow flow = new Flow(SampleController.class);
			DefaultFlowContainer container = new DefaultFlowContainer();
			this.flowContext = new ViewFlowContext();
			this.flowContext.register("Stage", primaryStage);
			flow.createHandler(this.flowContext).start(container);

			Scene scene = new Scene(container.getView(), 800, 600);
			container.getView().setPrefSize(800, 600);

			scene.getStylesheets().add(getClass().getResource("/css/jfoenix-design.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
