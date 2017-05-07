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
import javafx.scene.control.TextField;


public class ServerChatAppController implements Initializable {

	DatagramSocket serverSocket;
	DatagramPacket receivePacket;

	@FXML private TextArea messages;
	@FXML private Button startServer;
	@FXML private Button stopServer;
	@FXML private Label statusLabel;
	@FXML private TextField  inputMsg;

	@FXML
	public void startUDPServer(ActionEvent event) throws IOException {
		System.out.println("Starting server");
		statusLabel.setText("server is running");

		Server server = new Server();
		Thread thread = new Thread(server);

		thread.start();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}

class Server implements Runnable {
	Socket sessionSocket;
	ServerSocket serverSocket;

	public Server() {

	}

	public void startServer() throws IOException {
		DatagramSocket udpSocket = new DatagramSocket(9876);

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		while (true) {

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			udpSocket.receive(receivePacket);
			
			String sentence = new String(receivePacket.getData());
			
			InetAddress IPAddress = receivePacket.getAddress();

			int port = receivePacket.getPort();

			String capitalizedSentence = sentence.toUpperCase();

			sendData = capitalizedSentence.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

			udpSocket.send(sendPacket);
		}
		//udpSocket.close();
	}

	@Override
	public void run() {
		//
		try {
			startServer();
		}
		catch (IOException e) {
			System.out.println("Error with starting server: "+e.getMessage());
		}
	}

}
