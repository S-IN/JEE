// ==========================================================================
// package utilitairesMG.divers
// --------------------------------------------------------------------------
// Classe TriBulleVecteur
// --------------------------------------------------------------------------
// Classe pour le tri de vecteurs d'objets de type Comparable
// ==========================================================================
package utilitairesMG.divers;

import java.util.Vector;

public class TriBulleVecteur
{

// --------------------------------------------------------------------------
// Methode de tri (a bulles)
// --------------------------------------------------------------------------
// vecteur : reference du vecteur a trier
// Les objets référencés dans le vecteur devront être des objets d'une classe
// qui implemente Comparable.
// --------------------------------------------------------------------------
    public static void trier(Vector<Comparable> vecteur)
    {
        boolean permut;
        int n;
        Comparable x;

        do
        {
            permut = false;
            for (n = 0; n < vecteur.size() - 1; n++)
            {
                if (vecteur.elementAt(n).compareTo(vecteur.elementAt(n + 1)) > 0)
                {
                    x = vecteur.elementAt(n);
                    vecteur.setElementAt(vecteur.elementAt(n + 1), n);
                    vecteur.setElementAt(x, (n + 1));
                    permut = true;
                }
            }
        }
        while (permut == true);
    }

// --------------------------------------------------------------------------
// Affichage du vecteur
// --------------------------------------------------------------------------
    public static void afficher(Vector<Comparable> vecteur)
    {
        int n;

        for (n = 0; n < vecteur.size(); n++)
        {
            System.out.println(vecteur.elementAt(n));
        }
    }
}
