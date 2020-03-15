package bri;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Vector;
import java.util.regex.Pattern;
import java.lang.reflect.*;
import java.net.Socket;
import personnes.*;

public class ClientRegistry {
	// cette classe est un registre de services
	// partag�e en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
        block = new Object();
		dev = new Vector<Personne>();
	}
    private static Vector<Personne> dev;
    private static Object block;

// ajoute une classe de service après contrôle de la norme BLTi

    public static Personne addDev(String id, String pass, String ftp) throws Exception {
        synchronized(block) {
            
            Personne p = new Personne(id, pass, ftp);
            ClientRegistry.verify(p);
            ClientRegistry.dev.add(p);
            System.out.println("Developer ajouté !");
            return p;
        }
    }

    public static Personne getDev(String id, String pass) throws Exception {
		synchronized(block) {
			for(Personne p : ClientRegistry.dev) {
				if(p.getId().equals(id) && p.getPass().equals(pass))
				return p;
			}
			throw new Exception("Mauvais login / mot de passe");
	}
    }
    
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
