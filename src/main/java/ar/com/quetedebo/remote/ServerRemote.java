package ar.com.quetedebo.remote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ar.com.quetedebo.core.remote.HistorialRemote;

public class ServerRemote {

	public static void main(String[] args) {
		try {
            // Iniciar el observador
			HistoryObserverRemote observador = new HistoryObserverRemote();
            
            // Conectar con el servidor core
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            HistorialRemote coreService = (HistorialRemote) registry.lookup("HistorialRemoteServer");

            // Registrar el observador en core
            coreService.addObserverRemote(observador);
            System.out.println("Observador registrado en Core.");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
