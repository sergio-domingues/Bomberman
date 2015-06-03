package bomberman.connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ConnectionId extends Thread {
	public static int nextId = 1;
	protected Socket clientSocket = null;
	private int id;
	boolean isConnected = true;
	InputStream input;
	OutputStream output;
	private Date lastConn;
	private String lastMessage;

	ConnectionId(Socket clientSocket) {
		id = nextId;
		lastConn = new Date();
		this.clientSocket = clientSocket;
		nextId++;
	}

	public int getIdConnection() {
		return id;
	}

	public void updateLastConn() {
		lastConn = new Date();
	}

	public void run() {
		while (isConnected) {

			try {
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				lastMessage = in.readLine();
				System.out.println(id + ": " + lastMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		try {
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getLastMessage() {
		return lastMessage;
	}
}
