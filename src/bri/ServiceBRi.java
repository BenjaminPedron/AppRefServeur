package bri;


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

class ServiceBRi implements Runnable {
	
	/**
	 * S'occupe de lancer les deux serveurs d'écoute (DEV + AMAT)
	 * @param socket Le Socket client reçu par le serveur.
	 */

	private Socket client;
	
	ServiceBRi(Socket socket) {
		client = socket;
	}

	/**
	 * Menu des amateurs.
	 * Lance les services situés dans ServiceRegistry en lui donnant le Socket client.
	 */
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println(ServiceRegistry.toStringue() + " ##Tapez le numéro de service désiré :");
			int choix = Integer.parseInt(in.readLine());
			
			/* Récupère le constructeur de la classe choisie */
			Constructor<? extends Service> constr = ServiceRegistry.getServiceClass(choix).getConstructor(Socket.class);
			
			try {
				/* Instancie le service en lui transmettant le Socket */
				constr.newInstance(client).run();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				System.out.println("Erreur" + e.getMessage());
			}
				
			}
		catch (IOException | NoSuchMethodException | SecurityException e) {
			System.out.println("Erreur" + e.getMessage());
		}

		try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}
	public void start() {
		(new Thread(this)).start();		
	}

}
