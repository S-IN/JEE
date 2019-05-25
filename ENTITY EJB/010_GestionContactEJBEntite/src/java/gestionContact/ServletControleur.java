package gestionContact;

// ==========================================================================
// ServletControleur.java : servlet d'accueil du projet gestionContactMVC2
// ==========================================================================
import java.io.*;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import objetDistant.MappingEntiteRemote;

public class ServletControleur extends HttpServlet
{
    private TraitementAccueil traitementAccueil;
    private TraitementModif traitementModif;
    private MappingEntiteRemote mapping;

    @Override
    public void init()
    {
        try
        {

// ----------------------------------------------------------------------------
// Acces a l'objet distant
// ----------------------------------------------------------------------------
            Hashtable variablesEnv = new Hashtable();
            variablesEnv.put("org.omg.CORBA.ORBInitialHost", "localhost");
            variablesEnv.put("org.omg.CORBA.ORBInitialPort", "3700");

            Context ctx = new InitialContext(variablesEnv);
            mapping = (MappingEntiteRemote) ctx.lookup("jndiMappingEntite");
            traitementAccueil = new TraitementAccueil(mapping);
            traitementModif = new TraitementModif(mapping);
        }
        catch (NamingException ex)
        {
        }
    }

// --------------------------------------------------------------------------
// Traitement du formulaire d'accueil (index.jsp)
// --------------------------------------------------------------------------
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException, IOException
    {

// --------------------------------------------------------------------------
// contexte   : ServletContext pour utiliser le dispatcher.
// dispatcher : pour acceder aux jsp d'affichage.
// --------------------------------------------------------------------------
        ServletContext contexte;
        RequestDispatcher dispatcher;

// --------------------------------------------------------------------------
// idEcran    : identifiant de l'ecran reÃ§u.
// jsp        : jsp a afficher (retournee par les methodes de Traitement.
// choixAction : action choisie sur l'ecran d'accueil.
// --------------------------------------------------------------------------
        Integer idEcran;
        String jsp;
        String choixAction;
        HttpSession session;

// --------------------------------------------------------------------------
// Indication du codage pour l'interpretation des caracteres recus par la
// requete.
// --------------------------------------------------------------------------
        request.setCharacterEncoding("UTF-8");

// --------------------------------------------------------------------------
// Recuperation du SerletContext pour dispatcher...
// --------------------------------------------------------------------------
        contexte = getServletContext();

// --------------------------------------------------------------------------
// Lecture de l'identifiant de l'ecran (champ cache ou parametre d'index.jsp)
// --------------------------------------------------------------------------
        String numeroEcran = request.getParameter("idEcran");
        idEcran = new Integer(numeroEcran);

// --------------------------------------------------------------------------
// Divers branchements suivant l'ecran qui vient d'appeler ServletControleur
// --------------------------------------------------------------------------
        switch (idEcran)
        {

// --------------------------------------------------------------------------
// On vient de l'ecran jspAccueil
// --------------------------------------------------------------------------
            case 1:
                choixAction = request.getParameter("choixAction");

                if (choixAction.compareTo("liste") == 0)
                {
                    jsp = traitementAccueil.traitementListe(request);
                }
                else if (choixAction.compareTo("modification") == 0)
                {
                    jsp = traitementAccueil.traitementModif(request);
                }
                else
                {
                    jsp = traitementAccueil.traitementNonRealise(request);
                }
                break;

// --------------------------------------------------------------------------
// On vient de l'ecran jspModif
// --------------------------------------------------------------------------
            case 2:
                choixAction = request.getParameter("choixAction");

                if (choixAction.compareTo("Annuler") == 0)
                {
                    jsp = traitementModif.annulationModif(request);
                }
                else
                {
                    jsp = traitementModif.enregModif(request);
                }
                break;

            default:
                session = request.getSession();
                session.setAttribute("message", "");
                session.setAttribute("numeroContact", "");
                session.setAttribute("choixAction", "liste");

                jsp = "/jspAccueil.jsp";
        }

        dispatcher = contexte.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
        throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
        throws ServletException, IOException
    {
        processRequest(request, response);
    }
}
