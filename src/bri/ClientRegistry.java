package bri;

import java.util.Vector;
import java.util.regex.Pattern;
import personnes.*;

public class ClientRegistry {
	/**
	 * Gestionnaire des personnes, uniquement utilisable par les developpeurs pour l'instant.
	 */

	static {
        block = new Object();
		dev = new Vector<Personne>();
	}
    private static Vector<Personne> dev;
    private static Object block;


    /**
	 * Ajoute un developpeur à la liste.
	 * @param id L'identifiant de la personne.
     * @param pass Mot de passe de la personne.
     * @param ftp L'adresse ftp de la personne, le port 2121 sera utilisé par défaut.
	 */
    public static Personne addDev(String id, String pass, String ftp) throws Exception {
        synchronized(block) {
            
            Personne p = new Personne(id, pass, ftp);
            ClientRegistry.verify(p);
            ClientRegistry.dev.add(p);
            System.out.println("Developer ajouté !");
            return p;
        }
    }

    /**
	 * Récupere un developpeur dans la liste.
	 * @param id L'identifiant de la personne.
     * @param pass Mot de passe de la personne.
	 */
    public static Personne getDev(String id, String pass) throws Exception {
		synchronized(block) {
			for(Personne p : ClientRegistry.dev) {
				if(p.getId().equals(id) && p.getPass().equals(pass))
				return p;
			}
			throw new Exception("Mauvais login / mot de passe");
	}
    }
    
    /**
	 * Vérifie les entrées utilisateur
	 * @param p La personne vérifiée
	 */
    public static boolean verify(Personne p) throws Exception {
        synchronized(block) {
            String regexId = "[a-zA-Z0-9\\._\\-]{3,}"; 
            //String regexPass = "^(?=.*[0-9]).{8,}$";

            if(!Pattern.matches(regexId, p.getId())){
                System.out.println("Login non valide");
                throw new Exception("Login non valide");
            }
            //if(!Pattern.matches(regexPass, pass)) {
                //System.out.println("Mdp non valide");
                //throw new Exception("Mot de passe non valide, doit contenir 8 lettre 1 MAJ 1 SPEC CHAR 1 MIN");
            //}
            return true;
        }
    }
}
