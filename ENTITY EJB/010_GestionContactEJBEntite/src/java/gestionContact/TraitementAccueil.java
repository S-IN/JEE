package gestionContact;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import objetDistant.MappingEntiteRemote;
import tables.Contact;
import tables.Secteur;
import utilitairesMG.divers.Colonne;

public class TraitementAccueil
{
    private MappingEntiteRemote mapping;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public TraitementAccueil(MappingEntiteRemote mapping)
    {
        this.mapping = mapping;
    }

// --------------------------------------------------------------------------
// Traitement d'affichage de la liste
// --------------------------------------------------------------------------
    public String traitementListe(HttpServletRequest request)
    {
        String jspRetour;
        
        Vector<Contact> listeContacts;
        Vector<Colonne> listeColonnes;
        HttpSession session = request.getSession();
        
        try
        {
            listeContacts = mapping.lireListeContacts();
            listeColonnes = mapping.lireListeColonnes("tables.Contact");

            jspRetour = "/jspListe.jsp";
            session.setAttribute("listeContacts", listeContacts);
            session.setAttribute("listeColonnes", listeColonnes);
        }
        catch (Exception e)
        {
            jspRetour = "/jspAccueil.jsp";
            session.setAttribute("message", e.getMessage());
            session.setAttribute("numeroContact", "");
            session.setAttribute("choixAction", "liste");
        }
        return jspRetour;
    }

// --------------------------------------------------------------------------
// Traitement d'affichage de l'ecran de modification
// --------------------------------------------------------------------------
    public String traitementModif(HttpServletRequest request)
    {
        String jspRetour;

        Contact contact;
        Integer numeroContact;
        Vector<Secteur> vSect;
        HttpSession session = request.getSession();

        String chaineNumeroContact = request.getParameter("numeroContact");

        try
        {
            numeroContact = Integer.parseInt(chaineNumeroContact);
            contact = mapping.rafraichirContact(numeroContact);
            
            if (contact != null)
            {
                vSect = mapping.lireListeSecteurs();

                jspRetour = "/jspModif.jsp";
                session.setAttribute("message", "");
                session.setAttribute("contact", contact);
                session.setAttribute("vSect", vSect);
            }
            else
            {
                jspRetour = "/jspAccueil.jsp";
                session.setAttribute("message", "Contact " +
                                     chaineNumeroContact + " inconnu");
                session.setAttribute("numeroContact", chaineNumeroContact);
                session.setAttribute("choixAction", "modification");
            }
        }
        catch (Exception e)
        {
            jspRetour = "/jspAccueil.jsp";
            session.setAttribute("message", e.getMessage());
            session.setAttribute("numeroContact", chaineNumeroContact);
            session.setAttribute("choixAction", "modification");
        }

        return jspRetour;
    }

// --------------------------------------------------------------------------
// Traitement d'affichage du message non realise sur l'ecran d'accueil
// --------------------------------------------------------------------------
    public String traitementNonRealise(HttpServletRequest request)
    {
        String jspRetour;
        HttpSession session = request.getSession();

        String choixAction = request.getParameter("choixAction");
        String chaineNumeroContact = request.getParameter("numeroContact");

        jspRetour = "/jspAccueil.jsp";
        session.setAttribute("message", 
                             "Ecran de " + choixAction + " non réalisé");
        session.setAttribute("choixAction", choixAction);
        session.setAttribute("numeroContact", chaineNumeroContact);

        return jspRetour;
    }
}