// ==========================================================================
// package metierMapping                                       Projet Mapping
// --------------------------------------------------------------------------
// CLASSE Secteur
// ==========================================================================
package metierMapping;

import java.util.*;

public class Secteur implements java.io.Serializable
{

// ==========================================================================
// PROPRIETES
// ==========================================================================
// Toutes les colonnes de la table y compris les clefs etrangeres
// --------------------------------------------------------------------------
    private Integer code;                 // Clef primaire
    private String libelle;

// --------------------------------------------------------------------------
// Proprietes de mapping
// --------------------------------------------------------------------------
    private Vector<Contact> listeContacts;

// ==========================================================================
// METHODES
// ==========================================================================
// --------------------------------------------------------------------------
// CONSTRUCTEUR
// --------------------------------------------------------------------------
    public Secteur()
    {
    }

// --------------------------------------------------------------------------
// SETTERS
// --------------------------------------------------------------------------
    public void setCode(Integer code)
    {
        this.code = code;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

    public void setListeContacts(Vector<Contact> listeContacts)
    {
        this.listeContacts = listeContacts;
    }

// --------------------------------------------------------------------------
// GETTERS
// --------------------------------------------------------------------------
    public Integer getCode()
    {
        return code;
    }

    public String getLibelle()
    {
        return libelle;
    }

    public Vector<Contact> getListeContacts()
    {
        return listeContacts;
    }

// --------------------------------------------------------------------------
// AFFICHAGE DU SECTEUR (POUR MISE AU POINT)
// --------------------------------------------------------------------------
    public String toString()
    {
        String retour;
        int i;

        retour = "Code                : " + code + "\n";
        retour += "Libelle             : " + libelle + "\n";

        return retour;
    }
}
