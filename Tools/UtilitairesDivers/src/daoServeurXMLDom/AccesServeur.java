package daoServeurXMLDom;

// ==========================================================================
// CLASSE AccesServeur
// ==========================================================================
import java.net.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import utilitairesMG.jdbc.*;
import utilitairesMG.divers.*;
import org.w3c.dom.*;
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
// Parseur du document XML qui sera recu
// --------------------------------------------------------------------------
        DocumentBuilderFactory usineParseurXml;
        DocumentBuilder parseurXml;

// --------------------------------------------------------------------------
// Document DOM genere a partir du flux XML et variables d'analyse de l'arbre
// --------------------------------------------------------------------------
        Document documentDom;
        Element racine;

        NodeList listeElementsColonne;
        Element elementColonne;
        NodeList listeEnfantsColonne;
        Element nomColonne;
        Element tailleColonne;
        Element classeColonne;

        NodeList listeElementsLigne;
        Element elementLigne;
        NodeList listeEnfantsLigne;
        Text texte;

// --------------------------------------------------------------------------
// Creation de l'objet parseurXml.
// --------------------------------------------------------------------------
        usineParseurXml = DocumentBuilderFactory.newInstance();
        usineParseurXml.setIgnoringComments(true);
        usineParseurXml.setIgnoringElementContentWhitespace(true);
        usineParseurXml.setValidating(true);

// --------------------------------------------------------------------------
// Creer le parseur et lui associer le gestionnaire d'erreur
// --------------------------------------------------------------------------
        parseurXml = usineParseurXml.newDocumentBuilder();
        parseurXml.setErrorHandler(new ErreurDtd());

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
            documentDom = parseurXml.parse(sourceXml);

// --------------------------------------------------------------------------
// Recuperation de l'element racine de l'arbre
// --------------------------------------------------------------------------
            racine = documentDom.getDocumentElement();

            if (racine.getNodeName().compareTo("ERREUR") == 0)
            {
                throw new SAXException(
                        racine.getFirstChild().getNodeValue());
            }
            else
            {

// --------------------------------------------------------------------------
// La requete s'est bien executee. On analyse le fichier XML
// --------------------------------------------------------------------------
// Creation de la liste des colonnes
// --------------------------------------------------------------------------
                colonnes = new Vector<Colonne>();
                Colonne rangee;

                listeElementsColonne = racine.getElementsByTagName("COLONNE");

                for (int col = 0; col < listeElementsColonne.getLength(); col++)
                {
                    rangee = new Colonne();

                    elementColonne = (Element) listeElementsColonne.item(col);
                    listeEnfantsColonne = elementColonne.getChildNodes();

                    nomColonne = (Element) listeEnfantsColonne.item(0);
                    tailleColonne = (Element) listeEnfantsColonne.item(1);
                    classeColonne = (Element) listeEnfantsColonne.item(2);

                    rangee.setNom(
                            nomColonne.getFirstChild().getNodeValue());
                    rangee.setLongueur(new Integer(
                            tailleColonne.getFirstChild().getNodeValue()));
                    rangee.setType(
                            classeColonne.getFirstChild().getNodeValue());

                    colonnes.addElement(rangee);
                }

// --------------------------------------------------------------------------
// Creation de la liste des lignes
// --------------------------------------------------------------------------
                lignes = new Vector<Vector<Object>>();
                Vector<Object> ligne;

                listeElementsLigne = racine.getElementsByTagName("LIGNE");

                for (int lig = 0; lig < listeElementsLigne.getLength(); lig++)
                {
                    ligne = new Vector<Object>();

                    elementLigne = (Element) listeElementsLigne.item(lig);
                    listeEnfantsLigne = elementLigne.getChildNodes();

// --------------------------------------------------------------------------
// Creation des elements d'une ligne.
// --------------------------------------------------------------------------
                    for (int col = 0; col < listeEnfantsLigne.getLength(); col++)
                    {
                        texte = 
                            (Text) listeEnfantsLigne.item(col).getFirstChild();
                        if (texte != null)
                        {
                            Object o;
                            String typeColonne = 
                                    colonnes.elementAt(col).getType();
                            String valeur = texte.getNodeValue();

                            try
                            {
                                if ((typeColonne.
                                        compareTo("java.util.Date") == 0)
                                        || (typeColonne.
                                        compareTo("java.sql.Date") == 0)
                                        || (typeColonne.
                                        compareTo("java.sql.Time") == 0)
                                        || (typeColonne.
                                        compareTo("java.sql.Timestamp") == 0))
                                {
                                    o = CreationObjet.creeObjet(typeColonne,
                                                                new Long(valeur));
                                }
                                else
                                {
                                    o = CreationObjet.creeObjet(typeColonne,
                                                                valeur);
                                }
                            }
                            catch (ClassNotFoundException |
                                    NoSuchMethodException |
                                    InstantiationException |
                                    IllegalAccessException |
                                    InvocationTargetException e)
                            {
                                throw new SAXException(e.getMessage());
                            }

                            ligne.addElement(o);
                        }
                        else
                        {
                            ligne.addElement(null);
                        }
                    }

                    lignes.addElement(ligne);
                }

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
// Parseur du document XML qui sera recu
// --------------------------------------------------------------------------
        DocumentBuilderFactory usineParseurXml;
        DocumentBuilder parseurXml;

// --------------------------------------------------------------------------
// Document DOM genere a partir du flux XML et variables d'analyse de l'arbre
// --------------------------------------------------------------------------
        Document documentDom;
        Element racine;

// --------------------------------------------------------------------------
// Creation de l'objet parseurXml.
// --------------------------------------------------------------------------
        usineParseurXml = DocumentBuilderFactory.newInstance();
        usineParseurXml.setIgnoringComments(true);
        usineParseurXml.setIgnoringElementContentWhitespace(true);
        usineParseurXml.setValidating(true);

// --------------------------------------------------------------------------
// Creer le parseur et lui associer le gestionnaire d'erreur
// --------------------------------------------------------------------------
        parseurXml = usineParseurXml.newDocumentBuilder();
        parseurXml.setErrorHandler(new ErreurDtd());

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
            documentDom = parseurXml.parse(sourceXml);

// --------------------------------------------------------------------------
// Recuperation de l'element racine de l'arbre
// --------------------------------------------------------------------------
            racine = documentDom.getDocumentElement();

            if (racine.getNodeName().compareTo("ERREUR") == 0)
            {
                throw new SAXException(racine.getFirstChild().getNodeValue());
            }
            else
            {

// --------------------------------------------------------------------------
// La requete s'est bien executee. On analyse le fichier XML pour recuperer
// le nombre de lignes modifiees
// --------------------------------------------------------------------------
                nombreLignes
                        = new Integer(racine.getFirstChild().getNodeValue());
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
