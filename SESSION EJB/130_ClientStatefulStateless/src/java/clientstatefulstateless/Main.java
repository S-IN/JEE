package clientstatefulstateless;

import java.util.*;
import javax.naming.*;
import objetDistant.CompteurSFRemote;
import objetDistant.CompteurSLRemote;

public class Main 
{
    private static Fenetre fenetre;
    private static CompteurSLRemote compteurSL;
    private static CompteurSFRemote compteurSF;
    private static Random r;

    public static void main(String[] args) throws NamingException
    {
        Hashtable variablesEnv = new Hashtable();
        variablesEnv.put("org.omg.CORBA.ORBInitialHost", "94010-6101877");
        variablesEnv.put("org.omg.CORBA.ORBInitialPort", "3700");
        InitialContext ctx = new InitialContext(variablesEnv);
        
        compteurSL = (CompteurSLRemote)ctx.lookup("jndiCompteurSL");
        compteurSF = (CompteurSFRemote)ctx.lookup("jndiCompteurSF");

        r = new Random();

        fenetre = new Fenetre("StateFulStateLess");
    }

    public static void tirageCompteur()
    {
        int compteur = r.nextInt(200) + 1;
        fenetre.afficheSaisie("" + compteur);
    }

    public static void litCptSL()
    {
        int compteur = compteurSL.getCpt();
        fenetre.afficheMessage("Compteur StateLess : " + compteur);
    }

    public static void ecritCptSL(int i)
    {
        compteurSL.setCpt(i);
        fenetre.afficheMessage("Compteur StateLess mis a " + i);
    }

    public static void litCptSF()
    {
        int compteur = compteurSF.getCpt();
        fenetre.afficheMessage("Compteur StateFul : " + compteur);
    }

    public static void ecritCptSF(int i)
    {
        compteurSF.setCpt(i);
        fenetre.afficheMessage("Compteur StateFul mis a " + i);
    }

    public static void arreter()
    {
        System.exit(0);
    }
}
