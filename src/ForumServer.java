
// HelloServer.java
// Copyright and License 

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

/**
 * Cette classe est la classe principale constituant le programme serveur de
 * forum. Cette classe repr�sente le serveur du forum. Elle initialise l'orb et
 * l'objet servant du forum (ForumImpl).
 */

public class ForumServer  {

	protected ForumServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) {

		int status = 0;

		try {
			Registry registry = LocateRegistry.createRegistry(8585);
			Forum server = new ForumImpl();
			String URL = "//" + InetAddress.getLocalHost().getHostName() + ":" + 8585 + "/mon_serveur";
			Naming.rebind(URL, server);

		} catch (Exception ex) {
			ex.printStackTrace();
			status = 1;
		}

	}


}
