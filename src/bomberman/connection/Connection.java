package bomberman.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe responsavel pelo sistema de rede Utiliza o padrao de desenho Singleton
 * 
 * @author Diogo Moura
 *
 */
public class Connection extends Thread {
	protected static Connection instance = null;
	protected static int maxConnection = 2;
	protected static String hostIp;

	protected ServerSocket serverSocket;
	protected ConnectionId connections[];
	protected int nrConnections = 0;

	/**
	 * Estado do Servidor
	 * 
	 * @author Diogo Moura
	 *
	 */
	public enum ServerStatus {
		GETTINGCLIENT, RUNNING, STOPPED
	};

	protected ServerStatus status;

	protected boolean isRunning = true;
	public final static int PORT = 4445;

	/**
	 * Cria uma ServerSocket e inicia as variaveis do servidor necessarias
	 */
	public Connection() {
		connections = new ConnectionId[maxConnection];
		status = ServerStatus.GETTINGCLIENT;

		ConnectionId.nextId = 1;
		this.nrConnections = 0;

		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			hostIp = InetAddress.getLocalHost().toString();
			System.out.println(hostIp);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Aplicacao do Padrao Singleton Se nao existir nenhuma conexao cria uma e
	 * inicia o servidor, se j� existir retorna essa liga��o
	 * 
	 * @return Conexao
	 */
	public static Connection getInstance() {
		if (instance == null) {
			instance = new Connection();
			instance.start();
		}
		return instance;
	}

	/**
	 * Inicia o Servidor e consolante o estado executa determinadas operacoes Se
	 * estiver em: - GETTINGCLIENT fic� espera que os clientes se connectem at�
	 * chegar ao limite definido - RUNNING fica a correr o servidor
	 * indefinidamente - STOPPED termina todas as liga��es dos clientes e
	 * encerra o servidor
	 */
	public void run() {

		while (status == ServerStatus.GETTINGCLIENT) {
			System.out.println(ConnectionId.nextId);
			if (ConnectionId.nextId <= maxConnection) {
				try {
					Socket clientSocket = this.serverSocket.accept();
					if (!existsConnect(clientSocket.getInetAddress().getHostName())) {
						connections[ConnectionId.nextId - 1] = new ConnectionId(clientSocket);
						new Thread(connections[ConnectionId.nextId - 2]).start();
						nrConnections++;

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

		while (status == ServerStatus.RUNNING) {
			// for (int i = 0; i < maxConnection; i++) {
			// if (!connections[i].isConnected()) {
			// connections[i] = null;
			// nrConnections--;
			// }
			// }

		}

		if (status == ServerStatus.STOPPED) {
			for (int i = 0; i < connections.length; i++) {
				connections[i].closeConnection();
			}
		}

		System.err.println("Server Stopped.");

	}

	/**
	 * Verifica se j� exite determinada ligacao
	 * 
	 * @param ip
	 *            Ip da ligacao
	 * @return True- existir False se nao existir
	 */
	private boolean existsConnect(String ip) {
		for (int i = 0; i < maxConnection; i++) {
			if (connections[i] != null && connections[i].getIp().equals(ip)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Muda o estado do Servidor para STOPPED e faz reset da instance
	 */
	public synchronized void stopServer() {
		this.status = ServerStatus.STOPPED;

		try {
			this.serverSocket.close();
			instance = null;
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	public static void main(String[] arg) {
		getInstance();

	}

	/**
	 * Muda o estado do Servidor
	 * 
	 * @param status
	 *            Novo Estado
	 */
	public void changeStatus(ServerStatus status) {
		this.status = status;
	}

	/**
	 * Retorna as Liga��es existentes aos diferentes Clientes
	 * 
	 * @return Array com as Liga��es existentes
	 */
	synchronized public ConnectionId[] getConnections() {
		return connections;
	}

	/**
	 * Obtem o Nr M�ximo de Liga��es ao servidor
	 * 
	 * @return Nr Maximo de Liga��es
	 */
	public static int getMaxConnection() {
		return maxConnection;
	}

	/**
	 * Atribui um novo Maximo de Conexoes
	 * 
	 * @param maxConnection
	 *            Novo m�ximo
	 */
	public static void setMaxConnection(int maxConnection) {
		Connection.maxConnection = maxConnection;
	}

	/**
	 * Obtem o estado do servidor
	 * 
	 * @return Estado
	 */
	synchronized public ServerStatus getStatus() {
		return status;
	}

	/**
	 * Obtem o numero de conexoes activas
	 * 
	 * @return Nr de Conexoes activas
	 */
	synchronized public int getNrConnections() {
		return nrConnections;
	}

	/**
	 * Obtem o Local Ip do Servidor
	 * 
	 * @return Nome+ Ip do Servidor
	 */
	public static String getHostIp() {
		return hostIp;
	}

}