// ==========================================================================
// package utilitairesMG.divers
// --------------------------------------------------------------------------
// Classe Clavier
// ==========================================================================

package utilitairesMG.divers;

import java.io.*;

public class Clavier
{

// --------------------------------------------------------------------------
// On utilisera les trois methodes statiques readString, readInt, readDouble.
// Il n'y aura donc pas besoin de creer des instances de Console.
// Pour utiliser readLine() de la classe BufferedReader, il est necessaire de
// declarer une propriete de type BufferReader qui reference une instance de
// BufferedReader. Cette propriete, utilisee dans les methodes statiques,
// doit etre statique.
// --------------------------------------------------------------------------
    private static BufferedReader clavier
        = new BufferedReader(new InputStreamReader(System.in));

// --------------------------------------------------------------------------
// Preciser le codage (si on souhaite saisir des caracteres speciaux). 
// Le codage par defaut est UTF-8, ce qui n'est pas celui de Windows, qui 
// est le Cp1252.    
// --------------------------------------------------------------------------
    public static void setCodage(String codage)
    {
        InputStreamReader fluxCode;
        
        try
        {
            fluxCode = new InputStreamReader(System.in, codage);
            clavier = new BufferedReader(fluxCode);
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
    }
// --------------------------------------------------------------------------
// Methodes de saisie
// --------------------------------------------------------------------------
    public static String readString()
    {
        String ligne = "";

        try
        {
            ligne = clavier.readLine();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return ligne;
    }

    public static int readInt()
    {
        int retour;
        String ligne = "";

        try
        {
            ligne = clavier.readLine();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        retour = Integer.parseInt(ligne);
        return retour;
    }

    public static double readDouble()
    {
        double retour;
        String ligne = "";

        try
        {
            ligne = clavier.readLine();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        retour = Double.parseDouble(ligne);
        return retour;
    }
}
