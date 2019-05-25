package daoServeurXMLDom;

// ==========================================================================
// CLASSE ContactDAO
// ==========================================================================

import java.io.*;
import java.util.*;
import utilitairesMG.jdbc.*;
import utilitairesMG.divers.*;
import metierMapping.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

public class ContactDAO
{

// ==========================================================================
// PROPRIETES
// ==========================================================================
// --------------------------------------------------------------------------
// Acces au serveur XML
// --------------------------------------------------------------------------
    private AccesServeur accesServeur;

// --------------------------------------------------------------------------
// Jeu de resultats lu par l'un des "executeQuery"
// Il contient toutes les donnees des lignes lues dans la table contact et
// les donnees relatives aux colonnes.
// --------------------------------------------------------------------------
    private JeuResultat jeuResultat;

// ==========================================================================
// METHODES
// ==========================================================================
// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public ContactDAO(AccesServeur accesServeur)
    {
        this.accesServeur = accesServeur;
    }

// --------------------------------------------------------------------------
// Lecture d'un objet Contact (dont la clef est renseignee)
// --------------------------------------------------------------------------
    public void lire(Contact contact) 
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        int rowCount;

        String select;
        Vector<Object> ligne;

        select = "SELECT * FROM CONTACT WHERE NUMERO = " + contact.getNumero();

        jeuResultat = accesServeur.executeQuery(select);

        rowCount = (jeuResultat.getLignes()).size();

// --------------------------------------------------------------------------
// Si le executeQuery retourne 0 ligne, il n'y a pas d'Exception. C'est la
// raison de la creation d'une Exception particuliere.
// --------------------------------------------------------------------------
        if (rowCount == 1)
        {
            ligne = (jeuResultat.getLignes()).elementAt(0);

            contact.setNom((String) ligne.elementAt(1));
            contact.setAdresse((String) ligne.elementAt(2));
            contact.setCodePostal((String) ligne.elementAt(3));
            contact.setVille((String) ligne.elementAt(4));
            contact.setCodeSecteur((Integer) ligne.elementAt(5));
        }
        else
        {
            if (rowCount == 0)
            {
                throw new IOException(
                        "Contact " + contact.getNumero() + " inconnu");
            }
            else
            {
                throw new IOException(
                        "Clef " + contact.getNumero() + " en double !");
            }
        }
    }

// --------------------------------------------------------------------------
// Creation (insert) d'un objet Contact
// --------------------------------------------------------------------------
    public int creer(Contact contact) 
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        int rowCount;
        String insert;

        Integer numero = contact.getNumero();
        String nom = contact.getNom();
        String adresse = contact.getAdresse();
        String codePostal = contact.getCodePostal();
        String ville = contact.getVille();
        Integer codeSecteur = contact.getCodeSecteur();

        insert = "INSERT INTO CONTACT VALUES("
            + numero + ", "
            + Conversion.chaineSQL(nom) + ", "
            + Conversion.chaineSQL(adresse) + ", "
            + Conversion.chaineSQL(codePostal) + ", "
            + Conversion.chaineSQL(ville) + ", "
            + codeSecteur + ")";

        rowCount = accesServeur.executeUpdate(insert);

        return rowCount;
    }

// --------------------------------------------------------------------------
// Modification (update) d'un objet Contact
// --------------------------------------------------------------------------
    public int modifier(Contact contact) 
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        int rowCount;
        String update;

        Integer numero = contact.getNumero();
        String nom = contact.getNom();
        String adresse = contact.getAdresse();
        String codePostal = contact.getCodePostal();
        String ville = contact.getVille();
        Integer codeSecteur = contact.getCodeSecteur();

        update = "UPDATE CONTACT SET "
            + "NOM = " + Conversion.chaineSQL(nom) + ", "
            + "ADRESSE = " + Conversion.chaineSQL(adresse) + ", "
            + "CODE_POSTAL = " + Conversion.chaineSQL(codePostal) + ", "
            + "VILLE = " + Conversion.chaineSQL(ville) + ", "
            + "CODE_SECTEUR = " + codeSecteur + " "
            + "WHERE NUMERO = " + numero;

        rowCount = accesServeur.executeUpdate(update);

        return rowCount;
    }

// --------------------------------------------------------------------------
// Destruction (delete) d'un objet Contact
// --------------------------------------------------------------------------
    public int detruire(Contact contact) 
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        int rowCount;
        String delete;

        Integer numero = contact.getNumero();

        delete = "DELETE FROM CONTACT WHERE NUMERO = " + numero;

        rowCount = accesServeur.executeUpdate(delete);

        return rowCount;
    }

// --------------------------------------------------------------------------
// Lecture d'un Contact, pour un Versement donne
// --------------------------------------------------------------------------
    public Contact lireContact(Versement versement)
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        Contact contact = new Contact();
        contact.setNumero(versement.getNumeroContact());
        lire(contact);
        return contact;
    }

// --------------------------------------------------------------------------
// Liste des contacts pour un secteur donne
// --------------------------------------------------------------------------
    public Vector<Contact> lireListe(Secteur secteur)
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        Vector<Contact> listeContacts;
        Contact contact;

        String select = "SELECT * FROM CONTACT WHERE CODE_SECTEUR = ";
        select += secteur.getCode();

        int nombreDeContacts;
        Vector<Object> ligne;
        int i;

        jeuResultat = accesServeur.executeQuery(select);

        listeContacts = new Vector<Contact>();
        nombreDeContacts = (jeuResultat.getLignes()).size();

        for (i = 0; i < nombreDeContacts; i++)
        {
            ligne = (jeuResultat.getLignes()).elementAt(i);

            contact = new Contact();
            contact.setNumero((Integer) ligne.elementAt(0));
            contact.setNom((String) ligne.elementAt(1));
            contact.setAdresse((String) ligne.elementAt(2));
            contact.setCodePostal((String) ligne.elementAt(3));
            contact.setVille((String) ligne.elementAt(4));
            contact.setCodeSecteur((Integer) ligne.elementAt(5));

            contact.setSecteur(secteur);
            listeContacts.addElement(contact);
        }

        return listeContacts;
    }

// --------------------------------------------------------------------------
// Liste des contacts
// --------------------------------------------------------------------------
    public Vector<Contact> lireListe()
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        Vector<Contact> listeContacts;
        Contact contact;

        String select = "SELECT * FROM CONTACT";

        int nombreDeContacts;
        Vector<Object> ligne;
        int i;

        jeuResultat = accesServeur.executeQuery(select);

        listeContacts = new Vector<Contact>();
        nombreDeContacts = (jeuResultat.getLignes()).size();

        for (i = 0; i < nombreDeContacts; i++)
        {
            ligne = (jeuResultat.getLignes()).elementAt(i);

            contact = new Contact();
            contact.setNumero((Integer) ligne.elementAt(0));
            contact.setNom((String) ligne.elementAt(1));
            contact.setAdresse((String) ligne.elementAt(2));
            contact.setCodePostal((String) ligne.elementAt(3));
            contact.setVille((String) ligne.elementAt(4));
            contact.setCodeSecteur((Integer) ligne.elementAt(5));

            listeContacts.addElement(contact);
        }

        return listeContacts;
    }

// --------------------------------------------------------------------------
// Liste des colonnes de la table CONTACT
// --------------------------------------------------------------------------
    public Vector<Colonne> getListeColonnes()
    {
        return jeuResultat.getColonnes();
    }
}
