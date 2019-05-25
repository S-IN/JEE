// ==========================================================================
// Classe LignesComparator                                TestsCompletsTables
// --------------------------------------------------------------------------
// Comparateur de deux objets Vector<Object>
// ==========================================================================
package utilitairesMG.graphique.table;

import java.util.*;

public class LignesComparator implements Comparator
{
    private int numeroColonne;
    private boolean triAscendant;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public LignesComparator(int numeroColonne, boolean triAscendant)
    {
        this.numeroColonne = numeroColonne;
        this.triAscendant = triAscendant;
    }

// --------------------------------------------------------------------------
// compare : methode de comparaison de deux objets Vector (ligne)
// --------------------------------------------------------------------------
    public int compare(Object o1, Object o2)
    {
        int retour;

        retour = 0;

        if ((o1 instanceof Vector) && (o2 instanceof Vector))
        {
            Vector<Comparable> e1 = (Vector<Comparable>) o1;
            Vector<Comparable> e2 = (Vector<Comparable>) o2;

            if (e1.elementAt(numeroColonne) == null)
            {
                retour = -1;
            }
            else if (e2.elementAt(numeroColonne) == null)
            {
                retour = 1;
            }
            else if (e1.elementAt(numeroColonne) instanceof String)
            {
                retour = ((String) e1.elementAt(numeroColonne)).
                    compareToIgnoreCase((String) e2.elementAt(numeroColonne));
            }
            else
            {
                retour = (e1.elementAt(numeroColonne)).compareTo(
                    e2.elementAt(numeroColonne));
            }

            if (!triAscendant)
            {
                retour = -retour;
            }
        }

        return retour;
    }

// --------------------------------------------------------------------------
// equals : egalite entre deux objets EssaiComparator (l'objet courant et
// l'objet passe en parametre).
// --------------------------------------------------------------------------
// La methode retourne vrai si le numero de colonne et le sens du tri sont
// egaux...
// --------------------------------------------------------------------------
    public boolean equals(Object obj)
    {
        boolean retour;

        retour = false;

        if (obj instanceof LignesComparator)
        {
            LignesComparator objLignesComparator = (LignesComparator) obj;

            if ((objLignesComparator.numeroColonne == numeroColonne)
                && (objLignesComparator.triAscendant == triAscendant))
            {
                retour = true;
            }
        }
        return retour;
    }
}
