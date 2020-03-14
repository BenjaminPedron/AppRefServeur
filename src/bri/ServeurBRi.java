package bri;


import java.io.*;
import java.net.*;
import personnes.*;
import java.util.Vector;
import java.util.regex.*;

public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;
	public static Object block = new Object();

	private final static int PORT_AMAT = 3000;
	private final static int PORT_DEV = 3001;

	private static Vector<Personne> dev = new Vector<>();
	
	// Cree un serveur TCP - objet de la classe ServerSocket
	public ServeurBRi(int port) {
		try {
			listen_socket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Personne addDev(String id, String pass, String ftp) throws Exception {
		synchronized(block) {
			String regexId = "[a-zA-Z0-9\\._\\-]{3,}"; 
			//String regexPass = "^(?=.*[0-9]).{8, }$";

			if(!Pattern.matches(regexId, id)) {
				System.out.println("Login non valide");
				throw new Exception("Login non valide");
			}
			//if(!Pattern.matches(regexPass, pass)) {
				//System.out.println("Mdp non valide");
				//throw new Exception("Mot de passe non valide, doit contenir 8 lettre 1 MAJ 1 SPEC CHAR 1 MIN");
			//}
		
			Personne p = new Personne(id, pass, ftp);
			System.out.println("Developer ajouté !");
			ServeurBRi.dev.add(p);
			return p;
		}
	}

	public static Personne getDev(String id, String pass) throws Exception {
		for(Personne p : dev) {
			if(p.getId().equals(id) && p.getPass().equals(pass))
			return p;
		}
		throw new Exception("Mauvais login / mot de passe");
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
