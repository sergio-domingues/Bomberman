package bomberman.connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;

public class ConnectionId extends Thread {
	public static int nextId = 1;
	protected Socket clientSocket = null;
	private int id;
	private boolean isConnected = false;

	private PrintWriter out;
	private BufferedReader in;

	private String lastMessage, ip;

	ConnectionId(Socket clientSocket) {
		id = nextId;
		try {

			clientSocket.setSoTimeout(10000);

			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.clientSocket = clientSocket;
		ip = clientSocket.getInetAddress().getHostAddress();
		nextId++;
	}

	public String getIp() {
		return ip;
	}

	public int getIdConnection() {
		return id;
	}

	public void run() {
		while (!isConnected) {

			try {
				String received = in.readLine();
				System.out.println(received);
				
				if (received.equals("ligar")) {
					out.println("ACK");
					out.flush();
					isConnected=true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		while (isConnected) {
			try {
				lastMessage = in.readLine();
				System.out.println(id + ": " + lastMessage);

			} catch (SocketTimeoutException e) {
				System.err.println("Socket Desconectada");
				// this.closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		try {
			isConnected = false;
			this.clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public boolean isConnected() {
		return isConnected;
	}

}
