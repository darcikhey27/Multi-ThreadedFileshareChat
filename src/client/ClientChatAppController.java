package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientChatAppController implements Initializable {

	@FXML
	private TextArea messages;
	@FXML
	private TextField input;
	@FXML
	private Button send;
	@FXML
	private Button start;

	@FXML
	public void startClient(ActionEvent event) throws Exception {
		System.out.println("Enter sentence");
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		String line = input.getText();

		DatagramSocket clientSocket = new DatagramSocket();

		InetAddress IPAddress = InetAddress.getByName("localhost");

		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];

		String sentence = inFromUser.readLine();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		clientSocket.receive(receivePacket);

		String modifiedSentence = new String(receivePacket.getData());

		messages.appendText(modifiedSentence);
		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
	

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
