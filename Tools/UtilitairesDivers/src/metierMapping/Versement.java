// ==========================================================================
// package metierMapping                                       Projet Mapping
// --------------------------------------------------------------------------
// CLASSE Versement
// ==========================================================================
package metierMapping;

import java.util.*;
import utilitairesMG.divers.*;
import java.math.*;

public class Versement implements java.io.Serializable
{

// ==========================================================================
// PROPRIETES
// ==========================================================================
// Toutes les colonnes de la table y compris les clefs etrangeres
// --------------------------------------------------------------------------
    private Integer numero;                  // Clef primaire
    private Date date;
    private BigDecimal montant;
    private Integer numeroContact;           // Clef etrangere vers Contact

// --------------------------------------------------------------------------
// Proprietes de mapping
// --------------------------------------------------------------------------
    private Contact contact;  

// ==========================================================================
// METHODES
// ==========================================================================
// --------------------------------------------------------------------------
// CONSTRUCTEUR
// --------------------------------------------------------------------------
    public Versement()
    {
    }

// --------------------------------------------------------------------------
// SETTERS
// --------------------------------------------------------------------------
    public void setNumero(Integer numero)
    {
        this.numero = numero;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setMontant(BigDecimal montant)
    {
        this.montant = montant;
    }

// --------------------------------------------------------------------------
// Il n'y a pas conversion automatique de "double" en "BigDecimal"...
// --------------------------------------------------------------------------
    public void setMontant(double montant)
    {
        this.montant = new BigDecimal(montant);
    }

    public void setNumeroContact(Integer numeroContact)
    {
        this.numeroContact = numeroContact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;

        if (contact != null)
        {
            numeroContact = contact.getNumero();
        }
        else
        {
            numeroContact = null;
        }
    }

// --------------------------------------------------------------------------
// GETTERS
// --------------------------------------------------------------------------
    public Integer getNumero()
    {
        return numero;
    }

    public Date getDate()
    {
        return date;
    }

    public BigDecimal getMontant()
    {
        return montant;
    }

    public Integer getNumeroContact()
    {
        return numeroContact;
    }

    public Contact getContact()
    {
        return contact;
    }

// --------------------------------------------------------------------------
// AFFICHAGE DU VERSEMENT (POUR MISE AU POINT)
// --------------------------------------------------------------------------
    public String toString()
    {
        String retour;

        DateFr dateFr = null;
        if (date != null)
        {
            dateFr = new DateFr();
            dateFr.setTime(date);
        }

        String chaineMontant = null;
        if (montant != null)
        {
            chaineMontant
                = Conversion.doubleChaine(montant.doubleValue(), 2, 1, '.');
        }

        retour = "Numero              : " + numero + "\n";
        retour += "Date                : " + dateFr + "\n";
        retour += "Montant             : " + chaineMontant + "\n";
        retour += "Numero Contact      : " + numeroContact + "\n";

        return retour;
    }
}
