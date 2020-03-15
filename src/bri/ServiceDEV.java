package bri;


import java.io.*;
import java.net.*;

import services.auth.*;
import services.dev.*;
import personnes.*;

public class ServiceDEV implements Runnable {
	
	private Socket client;
	private Personne dev;

	public ServiceDEV(Socket client, Personne dev) {
		this.client = client;
		this.dev = dev;
	}
	
	ServiceDEV(Socket socket) {
		client = socket;
	}

	public void run() {
		try {
            System.out.println("Rentré dans le run");
            BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter (client.getOutputStream(), true);

			

			if(dev == null) {
				out.println(" ##1) Inscription ##2) Connexion ##3) Fermer la connexion");

				switch(Integer.parseInt(in.readLine())) {
					case(1): new ServiceInscription(client).run();
					break;
					case(2): new ServiceConnexion(client).run();
					break;
					case(3): client.close();
					break;
				}

			} else {
				System.out.println("test");
				out.println(" ##1) Fournir un service ##2) Effacer un service ##3) Mettre a jour un service ##4) Changer d'adresse ftp ##4) Fermer la connexion");
				switch(Integer.parseInt(in.readLine())) {
					case(1): new ServiceAddService(client, dev).run();
					break;
					case(2): new ServiceDeleteService(client, dev).run();
					break;
					case(3): new ServiceMajService(client, dev).run();
					break;
					case(5): client.close();
					break;
				}
			}

		} catch (IOException | SecurityException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
