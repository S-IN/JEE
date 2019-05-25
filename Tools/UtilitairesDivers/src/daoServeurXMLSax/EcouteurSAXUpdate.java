package daoServeurXMLSax;

// ==========================================================================
// Classe EcouteurSAXUpdate : redefinition des methodes startElement et
// characters de la classe DefaultHandler pour un UPDATE (INSERT, DELETE).
// ==========================================================================

import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import utilitairesMG.divers.*;
import metierMapping.*;

public class EcouteurSAXUpdate extends DefaultHandler
{
   private String texteErreur;
   private Integer nombreLignes;

   private boolean erreur;

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
      if (qname.compareTo("MODIF") == 0)
      {
         erreur = false;
      }
   }

// --------------------------------------------------------------------------
// Ecoute des evenements PCDATA
// --------------------------------------------------------------------------
   public void characters(char[] ch, int start, int length)
   {
      String texte;

      texte = new String(ch, start, length);

      if (erreur == true)
      {
         texteErreur = texte;
      }
      else
      {
         nombreLignes = new Integer(texte);
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

   public Integer getNombreLignes()
   {
      return nombreLignes;
   }
}