// ==========================================================================
// Classe PanneauTableEvent                               TestsCompletsTables
// --------------------------------------------------------------------------
// Evenement qui se produit quand la souris passe sur une cellule de la
// JTable
// ==========================================================================
package utilitairesMG.graphique.table;

import java.util.*;

public class PanneauTableEvent extends EventObject
{

// --------------------------------------------------------------------------
// Proprietes
// --------------------------------------------------------------------------
    private int ligne;
    private int colonne;
    private Object valeur;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public PanneauTableEvent(Object source,
        int ligne,
        int colonne,
        Object valeur)
    {
        super(source);

        this.ligne = ligne;
        this.colonne = colonne;
        this.valeur = valeur;
    }

// --------------------------------------------------------------------------
// Getters
// --------------------------------------------------------------------------
    public int getLigne()
    {
        return ligne;
    }

    public int getColonne()
    {
        return colonne;
    }

    public Object getValeur()
    {
        return valeur;
    }
}
