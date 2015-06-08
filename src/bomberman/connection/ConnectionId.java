package bomberman.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Observable;

/**
 * Representa uma ligacao com o cliente
 * @author Diogo Moura
 *
 */
public class ConnectionId extends Observable implements Runnable {
	public static int nextId = 1;
	protected Socket clientSocket = null;
	private int id;
	private boolean isConnected = false;
	private boolean running = true;

	private PrintWriter out;
	private BufferedReader in;

	private String lastMessage, ip;

	/**
	 * Nova Ligação
	 * @param clientSocket Socket a que se vai ligar
	 */
	ConnectionId(Socket clientSocket) {
		id = nextId;
		try {

			clientSocket.setSoTimeout(20000);

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

	/**
	 * Ip do Cliente
	 * @return Ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Obtem Id da Conexao
	 * @return Id Conexao
	 */
	public int getIdConnection() {
		return id;
	}

	/**
	 * Trata da Comunicao com o Cliente
	 */
	public void run() {
		while (!isConnected && running) {

			try {
				String received = in.readLine();

				if (received.equals("ligar")) {
					out.println("ACK");
					out.flush();
					isConnected = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		while (isConnected && running) {
			readMessage();
		}
	}

	/**
	 * Termina a Ligacao com o Cliente
	 */
	public synchronized void closeConnection() {
		try {
			isConnected = false;
			running = false;
			this.clientSocket.close();
			nextId--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Le mensagem do Cliente e notifica Observers
	 * @return Messagem Lida
	 */
	private String readMessage() {
		try {
			lastMessage = in.readLine();
		} catch (SocketTimeoutException e) {
			System.err.println("Socket Desconectada");
			closeConnection();
		} catch (IOException e) {
			System.err.println("Erro a ler da Socket");
			e.printStackTrace();
		}

		if (lastMessage == null) {
			System.err.println("Cliente Desconectado");
			closeConnection();
		} else
			System.out.println(id + ": " + lastMessage);

		setChanged();
		notifyObservers(lastMessage);
		clearChanged();

		return lastMessage;
	}

	/**
	 * Obtem a ultima mesagem recebida
	 * @return ultima Mensagem
	 */
	public String getLastMessage() {
		return lastMessage;
	}

	/**
	 * Verifica se esta ligado
	 * @return True se estiver/ False o contrario
	 */
	public boolean isConnected() {
		return isConnected;
	}

}
