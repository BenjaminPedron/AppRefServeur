package appli;


import bri.ServeurBRi;

public class BRiLaunch {
	
	private final static int PORT_AMAT = 3000;
	private final static int PORT_DEV = 3001;
	
	public static void main(String[] args) {
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
		System.out.println("Pour ajouter une activité, celle-ci doit être présente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'intégrer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activité");
		System.out.println("Les developpers se connectent au serveur 3001 pour gérer une activité");
		
		new Thread(new ServeurBRi(PORT_AMAT)).start();
		new Thread(new ServeurBRi(PORT_DEV)).start();
		
	}
}
