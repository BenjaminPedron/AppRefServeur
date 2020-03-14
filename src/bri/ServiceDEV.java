package bri;


import java.io.*;
import java.net.*;
import services.*;


class ServiceDEV implements Runnable {
	
	private Socket client;
	
	ServiceDEV(Socket socket) {
		client = socket;
	}

	public void run() {
		try {
            System.out.println("Rentré dans le run");
            BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
            out.println(" ##1) Inscription ##2)Connexion");

            switch(Integer.parseInt(in.readLine())) {
                case(1): new ServiceInscription(client).run();
                break;
                case(2): new ServiceConnexion(client).run();
                break;
                default: 
            }
        }
		catch (IOException | SecurityException e) {
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
