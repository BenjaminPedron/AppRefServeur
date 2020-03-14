package services;

import java.net.Socket;
import java.io.*;

import bri.ServeurBRi;
import bri.Service;

public class ServiceConnexion implements Service {
	
	private static int cpt = 1;
	
	private final int numero;
	private final Socket client;
	
	public ServiceConnexion(final Socket socket) {
		this.numero = cpt ++;
		this.client = socket;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Identifiant : ");
			String id = (String) in.readLine();
			out.println("Mot de passe : ");
			String pass = (String) in.readLine();

			try {
				ServeurBRi.getDev(id, pass);
				out.println("succes");
			} catch(Exception e) {
				out.println(e.toString());
			}
				
		}
		catch (IOException e) {
		}
		//Fin du service d'inversion
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	public static String toStringue() {
		return "Inversion de texte";
	}
}