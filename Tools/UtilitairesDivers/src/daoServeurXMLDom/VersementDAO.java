package daoServeurXMLDom;

// ==========================================================================
// CLASSE VersementDAO
// ==========================================================================

import java.io.*;
import java.util.*;
import utilitairesMG.divers.*;
import utilitairesMG.jdbc.*;
import java.math.*;
import metierMapping.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

public class VersementDAO
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
    public VersementDAO(AccesServeur accesServeur)
    {
        this.accesServeur = accesServeur;
    }

// --------------------------------------------------------------------------
// Lecture d'un objet Versement (dont la clef est renseignee)
// --------------------------------------------------------------------------
    public void lire(Versement versement) 
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        int rowCount;

        String select;
        Vector ligne;

        select = "SELECT * FROM VERSEMENT WHERE NUMERO = "
            + versement.getNumero();

        jeuResultat = accesServeur.executeQuery(select);

        rowCount = (jeuResultat.getLignes()).size();

// --------------------------------------------------------------------------
// Si le executeQuery retourne 0 ligne, il n'y a pas SQLException. C'est la
// raison de la creation d'une SQLException particuliere.
// --------------------------------------------------------------------------
        if (rowCount == 1)
        {
            ligne = (jeuResultat.getLignes()).elementAt(0);

            versement.setDate((Date) ligne.elementAt(1));
            versement.setMontant((BigDecimal) ligne.elementAt(2));
            versement.setNumeroContact((Integer) ligne.elementAt(3));
        }
        else
        {
            if (rowCount == 0)
            {
                throw new IOException(
                        "Versement " + versement.getNumero() + " inconnu");
            }
            else
            {
                throw new IOException(
                        "Clef " + versement.getNumero() + " en double !");
            }
        }
    }

// --------------------------------------------------------------------------
// Creation (insert) d'un objet Versement
// --------------------------------------------------------------------------
    public int creer(Versement versement) 
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        int rowCount;
        String insert;

        Integer numero = versement.getNumero();
        Date date = versement.getDate();
        BigDecimal montant = versement.getMontant();
        Integer numeroContact = versement.getNumeroContact();

        String chaineDate
            = Conversion.dateSQL(date, accesServeur.getPrise().getFormatDate());

        insert = "INSERT INTO VERSEMENT VALUES("
            + numero + ", "
            + chaineDate + ", "
            + montant + ", "
            + numeroContact + ")";

        rowCount = accesServeur.executeUpdate(insert);

        return rowCount;
    }

// --------------------------------------------------------------------------
// Modification (update) d'un objet Versement
// --------------------------------------------------------------------------
    public int modifier(Versement versement) 
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        int rowCount;
        String update;

        Integer numero = versement.getNumero();
        Date date = versement.getDate();
        BigDecimal montant = versement.getMontant();
        Integer numeroContact = versement.getNumeroContact();

        String chaineDate
            = Conversion.dateSQL(date, accesServeur.getPrise().getFormatDate());

        update = "UPDATE VERSEMENT SET "
            + "DATE = " + chaineDate + ", "
            + "MONTANT = " + montant + ", "
            + "NUMERO_CONTACT = " + numeroContact + " "
            + "WHERE NUMERO = " + numero;

        rowCount = accesServeur.executeUpdate(update);

        return rowCount;
    }

// --------------------------------------------------------------------------
// Destruction (delete) d'un objet Versement
// --------------------------------------------------------------------------
    public int detruire(Versement versement) 
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        int rowCount;
        String delete;

        Integer numero = versement.getNumero();

        delete = "DELETE FROM VERSEMENT WHERE NUMERO = " + numero;

        rowCount = accesServeur.executeUpdate(delete);

        return rowCount;
    }

// --------------------------------------------------------------------------
// Liste des versements pour un contact donne
// --------------------------------------------------------------------------
    public Vector<Versement> lireListe(Contact contact)
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        Vector<Versement> listeVersements;
        Versement versement;

        String select
            = "SELECT * FROM VERSEMENT WHERE NUMERO_CONTACT = ";
        select += contact.getNumero();

        int nombreDeVersements;
        Vector ligne;
        int i;

        jeuResultat = accesServeur.executeQuery(select);

        listeVersements = new Vector<Versement>();
        nombreDeVersements = (jeuResultat.getLignes()).size();

        for (i = 0; i < nombreDeVersements; i++)
        {
            ligne = (jeuResultat.getLignes()).elementAt(i);

            versement = new Versement();
            versement.setNumero((Integer) ligne.elementAt(0));
            versement.setDate((Date) ligne.elementAt(1));
            versement.setMontant((BigDecimal) ligne.elementAt(2));
            versement.setNumeroContact((Integer) ligne.elementAt(3));

            versement.setContact(contact);
            listeVersements.addElement(versement);
        }

        return listeVersements;
    }

// --------------------------------------------------------------------------
// Liste des versements
// --------------------------------------------------------------------------
    public Vector<Versement> lireListe()
        throws ParserConfigurationException,
               SAXException,
               IOException
    {
        Vector<Versement> listeVersements;
        Versement versement;

        String select = "SELECT * FROM VERSEMENT";

        int nombreDeVersements;
        Vector ligne;
        int i;

        jeuResultat = accesServeur.executeQuery(select);

        listeVersements = new Vector<Versement>();
        nombreDeVersements = (jeuResultat.getLignes()).size();

        for (i = 0; i < nombreDeVersements; i++)
        {
            ligne = (jeuResultat.getLignes()).elementAt(i);

            versement = new Versement();
            versement.setNumero((Integer) ligne.elementAt(0));
            versement.setDate((Date) ligne.elementAt(1));
            versement.setMontant((BigDecimal) ligne.elementAt(2));
            versement.setNumeroContact((Integer) ligne.elementAt(3));

            listeVersements.addElement(versement);
        }

        return listeVersements;
    }

// --------------------------------------------------------------------------
// Liste des colonnes de la table VERSEMENT
// --------------------------------------------------------------------------
    public Vector<Colonne> getListeColonnes()
    {
        return jeuResultat.getColonnes();
    }
}
