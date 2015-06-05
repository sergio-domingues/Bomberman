package bomberman.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection extends Thread {
	protected static Connection instance = null;
	protected static int maxConnection = 1;

	protected ServerSocket serverSocket;
	protected ConnectionId connections[];

	public enum ServerStatus {
		GETTINGCLIENT, RUNNING, STOPPED
	};

	protected ServerStatus status;

	protected boolean isRunning = true;
	public final static int PORT = 4445;

	public Connection() {
		connections = new ConnectionId[maxConnection];
		status = ServerStatus.GETTINGCLIENT;
		
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

	public static Connection getInstance() {
		if (instance == null) {
			instance = new Connection();
			instance.start();
		}
		return instance;
	}

	public void run() {

		while (status == ServerStatus.GETTINGCLIENT) {
			if (ConnectionId.nextId <= maxConnection) {
				try {
					Socket clientSocket = this.serverSocket.accept();
					if (!existsConnect(clientSocket.getInetAddress().getHostName())) {
						connections[ConnectionId.nextId - 1] = new ConnectionId(clientSocket);
						new Thread(connections[ConnectionId.nextId - 2]).start();

					}
				} catch (IOException e) {
					System.err.println("Erro Criar Cliente");
					e.printStackTrace();
				}
			} else if (ConnectionId.nextId > maxConnection + 1) {
				System.err.println("Maximo de CLientes Atingido");
			}
			
			for (int i = 0; i < maxConnection; i++) {
				if (!connections[i].isConnected()) {
					break;
				}
				if (i == maxConnection - 1) {
					this.status = ServerStatus.RUNNING;
				}

			}

		}

		System.out.println(this.status);
		while (status == ServerStatus.RUNNING) {
			// System.out.println("estas em runnig");
			// for (int i = 0; i < maxConnection; i++) {
			// if (!connections[i].isConnected()) {
			// connections[i] = null;
			//
			// }
			// }
			// TODO Fazer alguma coisa
		}
		System.err.println("Server Stopped.");

	}

	private boolean existsConnect(String ip) {
		for (int i = 0; i < maxConnection; i++) {
			if (connections[i] != null && connections[i].getIp().equals(ip)) {
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

	synchronized public ConnectionId[] getConnections() {
		return connections;
	}

	public static int getMaxConnection() {
		return maxConnection;
	}

	public static void setMaxConnection(int maxConnection) {
		Connection.maxConnection = maxConnection;
	}

	synchronized public ServerStatus getStatus() {
		return status;
	}

}