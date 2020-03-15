package appli;
import bri.ServeurBRi;

public class BRiLaunch {

	/**
	 * S'occupe de lancer les deux serveurs d'écoute (DEV + AMAT)
	 * @param args Sans utilité
	 */
	
	private final static int PORT_AMAT = 3000;
	private final static int PORT_DEV = 3001;
	
	public static void main(String[] args) {
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activité");
		System.out.println("Les developpers se connectent au serveur 3001 pour gérer une activité\n\n");
		
		new Thread(new ServeurBRi(PORT_AMAT)).start();
		new Thread(new ServeurBRi(PORT_DEV)).start();
		
	}
}
