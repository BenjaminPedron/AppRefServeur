package services.dev;

import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.*;

import bri.Service;
import bri.ServiceDEV;
import bri.ServiceRegistry;
import personnes.Personne;

public class ServiceAddService implements Service {
	

    private final Socket client;
    private final Personne dev;
	
	public ServiceAddService(final Socket socket, Personne dev) {

        this.client = socket;
        this.dev = dev;
	}

	public void run() {
		try {
            System.out.println("Rentré dans ServiceAddService");
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			try {
                out.println("Veuillez entrer le nom de votre classe, \"stop\" pour revenir à l'accueil");
                String classeName = (String) in.readLine();
                if(classeName.equals(("stop"))) {
                    new ServiceDEV(client, dev).run();
                } 
                else {
                    URL[] classLoaderUrls = new URL[]{new URL("ftp://" + dev.getFtp() + ":2121/")};
                    
                    /* Create a new URLClassLoader  */
                    URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
                    
                    Class<? extends Service> servCl = (Class<? extends Service>) urlClassLoader.loadClass(dev.getId() + "/" +  classeName);
                    ServiceRegistry.addService(servCl, classeName);
                    new ServiceDEV(client, dev).run();
                    urlClassLoader.close();

                }
                
            } catch (Exception e) {
                System.out.println(e);
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
		return "Connexion";
    }
    
    	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}
}