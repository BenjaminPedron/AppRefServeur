package bri;

import java.io.*;
import java.net.*;

public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;

	private final static int PORT_AMAT = 3000;
	private final static int PORT_DEV = 3001;

	
	// Cree un serveur TCP - objet de la classe ServerSocket
	public ServeurBRi(int port) {
		try {
			listen_socket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	// Le serveur ecoute et accepte les connections.
	// pour chaque connection, il cree un ServiceInversion, 
	// qui va la traiter.
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

	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}

	// lancement du serveur
	public void lancer() {
		(new Thread(this)).start();		
	}
}
