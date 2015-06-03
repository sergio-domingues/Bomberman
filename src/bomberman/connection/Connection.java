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

	public enum ServerStatus {
		GETTINGCLIENT, RUNNING, STOPPED
	};

	protected ServerStatus status = ServerStatus.GETTINGCLIENT;
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

		while (status == ServerStatus.GETTINGCLIENT) {
			Socket clientSocket = null;
			if (ConnectionId.nextId <=5) {
				try {
					clientSocket = this.serverSocket.accept();
					if (!existsConnect(clientSocket.getInetAddress().getHostName())) {
						connections[ConnectionId.nextId - 1] = new ConnectionId(clientSocket);
						connections[ConnectionId.nextId - 2].start();
					}
				} catch (IOException e) {
					System.err.println("Erro Criar Cliente");
					e.printStackTrace();
				}
			} else if (ConnectionId.nextId >5) {
				System.err.println("Maximo de CLientes Atingido");
			}

		}

		while (status == ServerStatus.RUNNING) {
			for (int i = 0; i < 4; i++) {
				if (!connections[i].isConnected()) {
					connections[i] = null;
				}
			}
		}
		System.out.println("Server Stopped.");

	}

	private boolean existsConnect(String ip) {
		for (int i = 0; i < 4; i++) {
			if (connections[i] != null && connections[i].getIp() == ip) {
				return true;
			}
		}
		return false;
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

	public void changeStatus(ServerStatus status) {
		this.status = status;
	}

}