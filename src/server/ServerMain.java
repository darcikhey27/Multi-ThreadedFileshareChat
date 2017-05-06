package server;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class ServerMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLdocumentServer.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("ServerApplication.css").toExternalForm());
			primaryStage.setTitle("Server");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
