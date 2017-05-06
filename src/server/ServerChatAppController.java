package server;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class ServerChatAppController implements Initializable {

	DatagramSocket serverSocket;
	DatagramPacket receivePacket;
	
	@FXML
	private Button startServer;
	@FXML
	private Button stopServer;
	@FXML
	private TextArea textArea;
	@FXML
	private Label statusLabel;

	@FXML
	public void startUDPServer(ActionEvent event) throws IOException {
		System.out.println("Starting server");
		

		serverSocket = new DatagramSocket(9877);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		statusLabel.setText("Server is running");
		
		receivePacket = new DatagramPacket(receiveData, receiveData.length);
		
		serverSocket.receive(receivePacket);
		String sentence = new String(receivePacket.getData());
		InetAddress IPAddress = receivePacket.getAddress();

		int port = receivePacket.getPort();
		String capitalizedSentence = sentence.toUpperCase();
		sendData = capitalizedSentence.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

		serverSocket.send(sendPacket);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
