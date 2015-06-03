package bomberman.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection extends Thread {
	protected ServerSocket serverSocket;
	protected ConnectionId connections[] = new ConnectionId[4];
	protected boolean isRunning = true;
	public final static int PORT = 4445;

	public Connection() {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		while (this.isRunning) {
			Socket clientSocket = null;
			if (ConnectionId.nextId < 4) {
				try {
					clientSocket = this.serverSocket.accept();
					connections[ConnectionId.nextId - 1] = new ConnectionId(clientSocket);
					connections[ConnectionId.nextId - 2].start();
				} catch (IOException e) {
					System.err.println("Erro Criar Cliente");
					e.printStackTrace();
				}
			} else if (ConnectionId.nextId > 4) {
				System.err.println("Maximo de CLientes Atingido");
			}

		}
		System.out.println("Server Stopped.");

	}

	public synchronized void stopServer() {
		this.isRunning = false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	public static void main(String[] arg) {
		new Connection().start();

	}

}