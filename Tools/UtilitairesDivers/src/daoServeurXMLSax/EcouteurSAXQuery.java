package daoServeurXMLSax;

// ==========================================================================
// Classe EcouteurSAXQuery : redefinition des methodes startElement et
// characters de la classe DefaultHandler pour un SELECT.
// ==========================================================================

import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import utilitairesMG.divers.*;
import metierMapping.*;

public class EcouteurSAXQuery extends DefaultHandler
{

// --------------------------------------------------------------------------
// erreur : booleen vrai si erreur (<ERREUR>, faux sinon (<RACINE>)
// texteErreur : texte de l'erreur du flux : <ERREUR>texteErreur</ERREUR>
// --------------------------------------------------------------------------
   private boolean erreur;
   private String texteErreur;

// --------------------------------------------------------------------------
// Proprietes pour le traitement d'un flux normal : <RACINE>.....</RACINE>
// --------------------------------------------------------------------------
   private Vector<Colonne> colonnes = new Vector<Colonne>();
   private Colonne colonne;
   private int indiceCol;

   private Vector<Vector<Object>> lignes = new Vector<Vector<Object>>();
   private Vector<Object> rangee;
   private String texte;

   private String typeElement;

// --------------------------------------------------------------------------
// Ecoute des evenements ELEMENT (debut)
// --------------------------------------------------------------------------
   public void startElement(String namespaceURI,
                            String lname,
                            String qname,
                            Attributes attrs) throws SAXException
   {
      if (qname.compareTo("ERREUR") == 0)
      {
         erreur = true;
      }
      else
      {
         if (qname.compareTo("RACINE") == 0)
         {
            erreur = false;
         }
         else
         {
            if (qname.compareTo("COLONNE") == 0)
            {
               typeElement = "COLONNE";
               colonne = new Colonne();
               indiceCol = 0;
            }
            else
            {
               if (qname.compareTo("LIGNE") == 0)
               {
                  typeElement = "LIGNE";
                  rangee = new Vector<Object>();
                  indiceCol = 0;
               }
               else
               {
                  if (typeElement.compareTo("LIGNE") == 0)
                  {
                     texte = null;
                  }
               }
            }
         }
      }
   }

// --------------------------------------------------------------------------
// Ecoute des evenements ELEMENT (fin)
// --------------------------------------------------------------------------
   public void endElement(String namespaceURI,
                          String lname,
                          String qname) throws SAXException
   {
      if (qname.compareTo("LIGNE") == 0)
      {
         lignes.addElement(rangee);
      }
      else
      {
         if ((typeElement.compareTo("LIGNE") == 0) && (qname.compareTo("RACINE") != 0))
         {
            Object o;
            String typeColonne = colonnes.elementAt(indiceCol).getType();

            try
            {
               if(texte != null)
               {
                  if((typeColonne.compareTo("java.util.Date") == 0) ||
                     (typeColonne.compareTo("java.sql.Date") == 0) ||
                     (typeColonne.compareTo("java.sql.Time") == 0) ||
                     (typeColonne.compareTo("java.sql.Timestamp") == 0))
                  {
                     o = CreationObjet.creeObjet(typeColonne, new Long(texte));
                  }
                  else
                  {
                     o = CreationObjet.creeObjet(typeColonne, texte);
                  }
               }
               else
               {
                  o = null;
               }
            }
            catch (Exception e)
            {
               throw new SAXException(e.getMessage());
            }

            rangee.addElement(o);
            indiceCol++;
         }
      }
   }

// --------------------------------------------------------------------------
// Ecoute des evenements PCDATA
// --------------------------------------------------------------------------
   public void characters(char[] ch, int start, int length)
   {
      texte = new String(ch, start, length);

      if (erreur == true)
      {
         texteErreur = texte;
      }
      else
      {
         if (typeElement.compareTo("COLONNE") == 0)
         {
            switch(indiceCol)
            {
               case 0 : colonne.setNom(texte);
                        break;
               case 1 : colonne.setLongueur(new Integer(texte));
                        break;
               case 2 : colonne.setType(texte);
                        colonnes.addElement(colonne);
                        break;
            }
            indiceCol++;
         }
      }
   }

// --------------------------------------------------------------------------
// Getters
// --------------------------------------------------------------------------
   public boolean getErreur()
   {
      return erreur;
   }

   public String getTexteErreur()
   {
      return texteErreur;
   }

   public Vector<Colonne> getColonnes()
   {
      return colonnes;
   }

   public Vector<Vector<Object>> getLignes()
   {
      return lignes;
   }
}