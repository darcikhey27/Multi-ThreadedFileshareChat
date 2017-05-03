package server;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ServerChatAppController implements Initializable{

	private Button startServer;
	
	@FXML
	public void startUDPServer(ActionEvent event) {
		UDPServer server = new UDPServer();
		try {
			server.startUDPServer();
			System.out.print("Sever is started");
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong with the server "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
