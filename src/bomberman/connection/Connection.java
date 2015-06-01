package bomberman.connection;

import java.io.IOException;

public class Connection {
	
	public static void main(String[] args) throws IOException {
		new ConnectionThread().start();
	}
}
