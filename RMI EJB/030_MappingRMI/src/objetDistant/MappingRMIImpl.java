package objetDistant;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.Vector;

import metierMapping.*;
import daoJdbcMapping.*;
import utilitairesMG.divers.*;
import utilitairesMG.jdbc.*;

public class MappingRMIImpl extends UnicastRemoteObject implements MappingRMI
{
    private BaseDeDonnees base;

// ==========================================================================
// Constructeur de l'objet distant
// ==========================================================================
    public MappingRMIImpl() throws RemoteException
    {

// --------------------------------------------------------------------------
// Chargement du driver SQL
// --------------------------------------------------------------------------
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Driver inconnu : " + e.getMessage());
            System.exit(0);
        }

// --------------------------------------------------------------------------
// Base(s) de donnees utilisee(s)
// --------------------------------------------------------------------------
        base = new BaseDeDonnees(
                "jdbc:sqlserver://mars;databasename=gnmi;" +
                "user=util_bip;password=x");
        base.setFormatDate("dd/MM/yyyy");
    }

// ==========================================================================
// Lecture d'un contact
// ==========================================================================
    public Contact lireContact(Integer numero) throws RemoteException,
                                                      SQLException
    {
        Contact contact;
        AccesBase accesBase;
        ContactDAO contactDAO;
        
        accesBase = new AccesBase(base);
        contactDAO = new ContactDAO(accesBase);;

// --------------------------------------------------------------------------
// Affichage de l'adresse IP du client
// --------------------------------------------------------------------------
        afficheClientHost("lireContact");

// --------------------------------------------------------------------------
// Lecture du contact
// --------------------------------------------------------------------------
        accesBase.getConnection();
        try
        {
            contact = new Contact();
            contact.setNumero(numero);
            contactDAO.lire(contact);
        }
        finally
        {
            accesBase.closeConnection();
        }

        return contact;
    }

// ==========================================================================
// Lecture de la liste des contacts et des colonnes correspondantes
// ==========================================================================
    public Vector<Vector> lireListeContacts() throws RemoteException,
                                                     SQLException
    {
        Vector<Vector> resultat;
        Vector<Contact> listeContacts;
        Vector<Colonne> listeColonnes;
        AccesBase accesBase;
        ContactDAO contactDAO;
        
        accesBase = new AccesBase(base);
        contactDAO = new ContactDAO(accesBase);;

// --------------------------------------------------------------------------
// Affichage de l'adresse IP du client
// --------------------------------------------------------------------------
        afficheClientHost("lireListeContacts");

// --------------------------------------------------------------------------
// Lecture de la liste des contacts
// --------------------------------------------------------------------------
        accesBase.getConnection();
        try
        {
            listeContacts = contactDAO.lireListe();
            listeColonnes = contactDAO.getListeColonnes();

            resultat = new Vector<Vector>();
            resultat.addElement(listeContacts);
            resultat.addElement(listeColonnes);
        }
        finally
        {
            accesBase.closeConnection();
        }

        return resultat;
    }

// ==========================================================================
// Modification d'un contact
// ==========================================================================
    public int modifierContact(Contact contact) throws RemoteException,
                                                       SQLException
    {
        int retour;
        AccesBase accesBase;
        ContactDAO contactDAO;
        
        accesBase = new AccesBase(base);
        contactDAO = new ContactDAO(accesBase);;

// --------------------------------------------------------------------------
// Affichage de l'adresse IP du client
// --------------------------------------------------------------------------
        afficheClientHost("modifierContact");

// --------------------------------------------------------------------------
// Modification du contact
// --------------------------------------------------------------------------
        accesBase.getConnection();
        try
        {
            retour = contactDAO.modifier(contact);
        }
        finally
        {
            accesBase.closeConnection();
        }

        return retour;
    }

// ==========================================================================
// Lecture de la liste des secteurs et des colonnes correspondantes
// ==========================================================================
    public Vector<Vector> lireListeSecteurs() throws RemoteException,
                                                     SQLException
    {
        Vector<Vector> resultat;
        Vector<Secteur> listeSecteurs;
        Vector<Colonne> listeColonnes;
        AccesBase accesBase;
        SecteurDAO secteurDAO;
        
        accesBase = new AccesBase(base);
        secteurDAO = new SecteurDAO(accesBase);;

// --------------------------------------------------------------------------
// Affichage de l'adresse IP du client
// --------------------------------------------------------------------------
        afficheClientHost("lireListeSecteurs");

// --------------------------------------------------------------------------
// Lecture de la liste des secteurs
// --------------------------------------------------------------------------
        accesBase.getConnection();
        try
        {
            listeSecteurs = secteurDAO.lireListe();
            listeColonnes = secteurDAO.getListeColonnes();

            resultat = new Vector<Vector>();
            resultat.addElement(listeSecteurs);
            resultat.addElement(listeColonnes);
        }
        finally
        {
            accesBase.closeConnection();
        }

        return resultat;
    }

// ==========================================================================
// Affichage de l'adresse de l'utilisateur "appelant"...
// --------------------------------------------------------------------------
// La methode getClientHost() est heritee de UnicastRemoteObject qui en 
// herite lui-meme de RemoteServer.
// ==========================================================================
    public void afficheClientHost(String s)
    {
        try
        {
            System.out.println(getClientHost() + " : " + s);
        }
        catch (ServerNotActiveException e)
        {
        }
    }
}