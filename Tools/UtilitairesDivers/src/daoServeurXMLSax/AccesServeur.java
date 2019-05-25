package daoServeurXMLSax;

// ==========================================================================
// CLASSE AccesServeur
// ==========================================================================

import java.net.*;
import java.io.*;
import java.util.*;
import utilitairesMG.jdbc.*;
import utilitairesMG.divers.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

public class AccesServeur
{
    private PriseServeur prise;
    private Socket socketClient;
    private InputStreamReader entree;
    private InputSource sourceXml;
    private PrintWriter sortie;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public AccesServeur(PriseServeur prise)
    {
        this.prise = prise;
    }

    public PriseServeur getPrise()
    {
        return prise;
    }

// --------------------------------------------------------------------------
// Methode d'ouverture d'une connexion avec le serveur
// --------------------------------------------------------------------------
    public Socket getConnection() throws IOException
    {
        socketClient = new Socket(prise.getNomMachine(),
                                  prise.getNumeroPort());

        sortie = new PrintWriter(
            new OutputStreamWriter(
                socketClient.getOutputStream(), "utf-8"), true);

        entree = new InputStreamReader(
            socketClient.getInputStream(), "utf-8");

        sourceXml = new InputSource(entree);

        return socketClient;
    }

// --------------------------------------------------------------------------
// Methode de fermeture de la connexion
// --------------------------------------------------------------------------
    public void closeConnection() throws IOException
    {
        if ((socketClient != null) && (!socketClient.isClosed()))
        {
            socketClient.close();
        }
    }

// --------------------------------------------------------------------------
// executeQuery (SELECT)
// --------------------------------------------------------------------------
// Cette methode retourne le jeu de resultats obtenu par le Select
// ou une exception si le select n'est pas correct
// --------------------------------------------------------------------------
    public JeuResultat executeQuery(String requete)
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        JeuResultat jeuResultat;
        Vector<Colonne> colonnes;
        Vector<Vector<Object>> lignes;

// --------------------------------------------------------------------------
// Parseur du document XML qui sera recu et Ecouteur des evenements SAX
// --------------------------------------------------------------------------
        SAXParserFactory usineParseurXml;
        SAXParser parseurXml;
        EcouteurSAXQuery ecouteur;

// --------------------------------------------------------------------------
// Creation de l'objet parseurXml et de l'ecouteur.
// --------------------------------------------------------------------------
        usineParseurXml = SAXParserFactory.newInstance();
        parseurXml = usineParseurXml.newSAXParser();
        ecouteur = new EcouteurSAXQuery();

// --------------------------------------------------------------------------
// Cas de la requete vide.
// --------------------------------------------------------------------------
        if (requete.compareTo("") == 0)
        {
            throw new SAXException("Requete vide");
        }

// --------------------------------------------------------------------------
// Ouverture de la connexion avec le serveur XML.
// --------------------------------------------------------------------------
// Il faut ouvrir (et fermer) la connexion a chaque appel (executeQuery ou
// executeUpdate), a cause de la conception du serveur XML...
// --------------------------------------------------------------------------
// En effet, dans le serveur, le thread client ouvre une socket a la
// reception de la requete (accept), exécute le requete, envoie le resultat,
// et FERME la socket.
// --------------------------------------------------------------------------
        getConnection();

        try
        {

// --------------------------------------------------------------------------
// Envoi de la requete vers le serveur.
// --------------------------------------------------------------------------
            sortie.println(requete);

// --------------------------------------------------------------------------
// Lecture de la reponse du serveur et creation du documentDom :
// --------------------------------------------------------------------------
// Le "protocole" utilise est le suivant :
//
// Un flux XML de racine RACINE si le select a ete execute correctement.
// Un flux XML de racine ERREUR s'il y a eu erreur d'execution de la requete.
// --------------------------------------------------------------------------
            parseurXml.parse(sourceXml, ecouteur);

// --------------------------------------------------------------------------
// L'ecouteur a detecte une balise <ERREUR>
// --------------------------------------------------------------------------
            if (ecouteur.getErreur() == true)
            {
                throw new SAXException(ecouteur.getTexteErreur());
            }
            else
            {

// --------------------------------------------------------------------------
// La requete s'est bien executee. On recupere les resultats "ecoutes"
// --------------------------------------------------------------------------
                colonnes = ecouteur.getColonnes();
                lignes = ecouteur.getLignes();

                jeuResultat = new JeuResultat();
                jeuResultat.setColonnes(colonnes);
                jeuResultat.setLignes(lignes);
            }
        }
        finally
        {
            closeConnection();
        }

        return jeuResultat;
    }

// --------------------------------------------------------------------------
// executeUpdate (INSERT, DELETE, UPDATE)
// --------------------------------------------------------------------------
// Cette methode retourne le nombre de lignes concernees par la requete
// --------------------------------------------------------------------------
    public Integer executeUpdate(String requete)
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        Integer nombreLignes;

// --------------------------------------------------------------------------
// Parseur du document XML qui sera recu et Ecouteur des evenements SAX
// --------------------------------------------------------------------------
        SAXParserFactory usineParseurXml;
        SAXParser parseurXml;
        EcouteurSAXUpdate ecouteur;

// --------------------------------------------------------------------------
// Creation de l'objet parseurXml et de l'ecouteur.
// --------------------------------------------------------------------------
        usineParseurXml = SAXParserFactory.newInstance();
        parseurXml = usineParseurXml.newSAXParser();
        ecouteur = new EcouteurSAXUpdate();

// --------------------------------------------------------------------------
// Cas de la requete vide.
// --------------------------------------------------------------------------
        if (requete.compareTo("") == 0)
        {
            throw new SAXException("Requete vide");
        }

// --------------------------------------------------------------------------
// Ouverture de la connexion avec le serveur JDBC.
// --------------------------------------------------------------------------
        getConnection();

        try
        {

// --------------------------------------------------------------------------
// Envoi de la requete vers le serveur.
// --------------------------------------------------------------------------
            sortie.println(requete);

// --------------------------------------------------------------------------
// Lecture de la reponse du serveur et creation du documentDom :
// --------------------------------------------------------------------------
// Le "protocole" utilise est le suivant :
//
// Un flux XML de racine RACINE si le select a ete execute correctement.
// Un flux XML de racine ERREUR s'il y a eu erreur d'execution de la requete.
// --------------------------------------------------------------------------
            parseurXml.parse(sourceXml, ecouteur);

// --------------------------------------------------------------------------
// L'ecouteur a detecte une balise <ERREUR>
// --------------------------------------------------------------------------
            if (ecouteur.getErreur() == true)
            {
                throw new SAXException(ecouteur.getTexteErreur());
            }
            else
            {

// --------------------------------------------------------------------------
// La requete s'est bien executee. On recupere les resultats "ecoutes"
// --------------------------------------------------------------------------
                nombreLignes = ecouteur.getNombreLignes();
            }

// --------------------------------------------------------------------------
// Fermeture de la connexion :
// --------------------------------------------------------------------------
        }
        finally
        {
            closeConnection();
        }

        return nombreLignes;
    }
}
