// ==========================================================================
// Projet MappingRMI
// --------------------------------------------------------------------------
// MappingRMIServer : Serveur d'objets distants.
// ==========================================================================

import objetDistant.MappingRMIImpl;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.*;
import java.rmi.registry.LocateRegistry;


public class MappingRMIServer
{
   public static void main(String args[]) throws UnknownHostException
   {
       String urlAdressage;

// --------------------------------------------------------------------------
// Lancement du serveur d'adressage sur le port 6128. Cette opération fait
// appel a l'utilitaire rmiregistry.       
// --------------------------------------------------------------------------
      try
      {
         LocateRegistry.createRegistry(6128);
      }
      catch(Exception e)
      {
         System.out.println(e.getMessage());
      }

// --------------------------------------------------------------------------
// Le serveur d'adressage est lance. Son URL doit etre recuperee pour 
// permettre d'y referencer l'objet distant (rebind).
// --------------------------------------------------------------------------
      urlAdressage = 
              "//" + InetAddress.getLocalHost().getHostAddress()+ ":6128";

// --------------------------------------------------------------------------
// Construction de l'objet distant et refrencement sur le  serveur 
// d'adressage.
// --------------------------------------------------------------------------
      try
      {
         System.out.println("Construction de l'objet distant...");
         MappingRMIImpl mapping = new MappingRMIImpl();

         System.out.println(
         "Liaison de l'objet distant 'mapping' sur le serveur d'annuaire...");
         Naming.rebind(urlAdressage + "/mappingDistant", mapping);

         System.out.println("Attente des invocations des clients...");
      }
      catch(RemoteException e)
      {
         e.printStackTrace();
      }
      catch(MalformedURLException e)
      {
         e.printStackTrace();
      }
   }
}
