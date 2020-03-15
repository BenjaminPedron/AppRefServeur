package bri;

import java.io.*;
import java.net.*;

public class ServeurBRi implements Runnable {

	/**
	 * Lance et serveur et se met en écoute
	 * @param port Le port sur lequel est lancé l'application
	 */

	private ServerSocket listen_socket;
	private final static int PORT_AMAT = 3000;
	private final static int PORT_DEV = 3001;

	public ServeurBRi(int port) {
		try {
			listen_socket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Regarde le port du Socket, lance le service correspondant.
	 */
	public void run() {
		try {
			while(true) {
				Socket client = listen_socket.accept();
				switch(client.getLocalPort()) {
					case(PORT_AMAT): new ServiceBRi(client).start();
					break;
					case(PORT_DEV): new ServiceDEV(client).start();
					break;
				}
			}
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'écoute :" + e);
		}
	}

	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}

	public void lancer() {
		(new Thread(this)).start();		
	}
}
