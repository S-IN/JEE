// ==========================================================================
// package metierMapping                                       Projet Mapping
// --------------------------------------------------------------------------
// CLASSE Contact
// ==========================================================================
package metierMapping;

import java.util.*;

public class Contact implements java.io.Serializable
{

// ==========================================================================
// PROPRIETES
// ==========================================================================
// Toutes les colonnes de la table y compris les clefs etrangeres
// --------------------------------------------------------------------------
    private Integer numero;             // Clef primaire
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
    private Integer codeSecteur;        // Clef etrangere vers Secteur

// --------------------------------------------------------------------------
// Proprietes de mapping
// --------------------------------------------------------------------------
    private Secteur secteur;  
    private Vector<Versement> listeVersements;

// ==========================================================================
// METHODES
// ==========================================================================
// --------------------------------------------------------------------------
// CONSTRUCTEUR
// --------------------------------------------------------------------------
    public Contact()
    {
    }

// --------------------------------------------------------------------------
// SETTERS
// --------------------------------------------------------------------------
    public void setNumero(Integer numero)
    {
        this.numero = numero;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public void setCodePostal(String codePostal)
    {
        this.codePostal = codePostal;
    }

    public void setVille(String ville)
    {
        this.ville = ville;
    }

    public void setCodeSecteur(Integer codeSecteur)
    {
        this.codeSecteur = codeSecteur;
    }

    public void setSecteur(Secteur secteur)
    {
        this.secteur = secteur;

        if (secteur != null)
        {
            this.codeSecteur = secteur.getCode();
        }
        else
        {
            this.codeSecteur = null;
        }
    }

    public void setListeVersements(Vector<Versement> listeVersements)
    {
        this.listeVersements = listeVersements;
    }

// --------------------------------------------------------------------------
// GETTERS
// --------------------------------------------------------------------------
    public Integer getNumero()
    {
        return numero;
    }

    public String getNom()
    {
        return nom;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public String getCodePostal()
    {
        return codePostal;
    }

    public String getVille()
    {
        return ville;
    }

    public Integer getCodeSecteur()
    {
        return codeSecteur;
    }

    public Secteur getSecteur()
    {
        return secteur;
    }

    public Vector<Versement> getListeVersements()
    {
        return listeVersements;
    }

// --------------------------------------------------------------------------
// AFFICHAGE DU CONTACT (POUR MISE AU POINT)
// --------------------------------------------------------------------------
    public String toString()
    {
        String retour;

        retour = "Numero              : " + numero + "\n";
        retour += "Nom                 : " + nom + "\n";
        retour += "Adresse             : " + adresse + "\n";
        retour += "Code Postal         : " + codePostal + "\n";
        retour += "Ville               : " + ville + "\n";
        retour += "Code Secteur        : " + codeSecteur + "\n";

        return retour;
    }
}
