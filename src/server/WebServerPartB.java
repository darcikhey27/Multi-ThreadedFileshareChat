/*
 * Darci K Saucedo 
 * Network Programming 
 * Server application which serves basic html and picture files
 * 
 */
package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServerPartB {

	public void startWebServer() {
		int port = 6789;
		ServerSocket serverSocket;
		Socket sessionSocket;

		try {
			// create the listen socket
			System.out.println("Server is running...");
			serverSocket = new ServerSocket(port);
			while (true) {
				// listen for a tcp session
				System.out.println("Waiting for TCP session...");
				sessionSocket = serverSocket.accept();

				// Construct an object to process the HTTP request message
				HttpRequest request = new HttpRequest(sessionSocket);

				// create a new thread to process the request
				Thread thread = new Thread(request);

				// start the thread
				thread.start();
			}
		}
		catch (Exception e) {
			System.out.println("problems with socket: " + e.getMessage());
		}
	}
}

class HttpRequest implements Runnable {
	Socket sessionSocket;
	final static String CRLF = "\r\n";

	public HttpRequest(Socket sessionSocket) throws Exception {
		this.sessionSocket = sessionSocket;
	}

	@Override
	public void run() {
		try {
			processRequest();
		}
		catch (Exception e) {
			System.out.println("Something went bad with sessionSocket: " + e.getMessage());
		}
	}

	private void processRequest() throws Exception {
		// get the sockets input and output stream
		InputStreamReader inputStream = new InputStreamReader(sessionSocket.getInputStream());
		DataOutputStream outStream = new DataOutputStream(sessionSocket.getOutputStream());

		BufferedReader buffReader = new BufferedReader(inputStream);

		// Get the request line of the HTTP request message.
		String requestLine = buffReader.readLine();

		System.out.println();
		System.out.println(requestLine);

		// Get and display the header lines.
		String headerLine = "";
		while ((headerLine = buffReader.readLine()).length() != 0) {
			System.out.println(headerLine);
		}
		// Extract the filename from the request line.
		StringTokenizer tokens = new StringTokenizer(requestLine);
		tokens.nextToken(); // skip over the method, which should be "GET"
		String fileName = tokens.nextToken();

		// Prepend a "." so that file request is within the current directory.
		fileName = "." + fileName;
		//System.out.println("fileName is: " + fileName);
		// open the requested file
		FileInputStream fileInputStream = null;
		boolean fileExists = true;
		try {
			fileInputStream = new FileInputStream(fileName);

		}
		catch (FileNotFoundException e) {
			fileExists = false;
			System.out.println("file " + fileName + " not found");
		}

		// Construct the response message.
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		if (fileExists) {
			statusLine = "HTTP/1.1 200 OK" + CRLF;
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
		}
		else {
			statusLine = "HTTP/1.1 404 Not Found";
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
			entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";
		}
		// send the status line
		outStream.writeBytes(statusLine);
		// send the content line
		outStream.writeBytes(contentTypeLine);
		outStream.writeBytes(CRLF);

		// Send the entity body
		if (fileExists) {
			sendBytes(fileInputStream, outStream);
			fileInputStream.close();
		}
		else {
			outStream.writeBytes(entityBody);
		}
		outStream.close();
		inputStream.close();
		sessionSocket.close();
	}

	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
		// Construct a 1K buffer to hold bytes on their way to the socket.
		byte[] buffer = new byte[1024];
		int bytes = 0;

		// Copy requested file into the socket's output stream.
		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName) {
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}
		else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		else if (fileName.endsWith(".gif")) {
			return "image/gif";
		}
		return "application/octet-stream";
	}
}
