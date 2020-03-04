package bri;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Vector;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partag�e en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = new Vector<Class <? extends Service>>();
	}
	private static Vector<Class<? extends Service>> servicesClasses;

// ajoute une classe de service apr�s contr�le de la norme BLTi
	public static void addService(Class<? extends Service> classe) throws Exception {
		// v�rifier la conformit� par introspection
		if (!checkIfClassImplements(classe, bri.Service.class))
			throw new Exception("Le service doit implementer" + bri.Service.class);
		if (!Modifier.isPublic(classe.getModifiers()))
			throw new Exception("Votre classe doit �tre public !");
		if (Modifier.isAbstract(classe.getModifiers()))
			throw new Exception("Votre classe doit �tre abstract !");
		// ajout du service
		servicesClasses.add(classe);
	}

	// renvoie la classe de service (numService -1)	
	public static Class<? extends Service> getServiceClass(int numService) {
		return servicesClasses.get(numService - 1);
	}

// liste les activit�s pr�sentes
	public static String toStringue() {
		return "toto";
	}

	private static boolean checkIfClassImplements(Class<?> classe1, Class<?> classe2) {
		for (Class<?> i : classe1.getInterfaces()) {
			if (i.equals(classe2))
				return true;
		}
		return false;
	}

}
