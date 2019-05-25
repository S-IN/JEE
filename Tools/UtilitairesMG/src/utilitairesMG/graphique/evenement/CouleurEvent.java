// ==========================================================================
// package utilitairesMG.graphique.evenement
// --------------------------------------------------------------------------
// Classe CouleurEvent
// ==========================================================================
package utilitairesMG.graphique.evenement;

import java.awt.Color;
import java.util.*;

public class CouleurEvent extends EventObject
{

// --------------------------------------------------------------------------
// Proprietes
// --------------------------------------------------------------------------
    private Color couleur;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public CouleurEvent(Object source, Color couleur)
    {
        super(source);
        this.couleur = couleur;
    }

// --------------------------------------------------------------------------
// Getters
// --------------------------------------------------------------------------
    public Color getCouleur()
    {
        return couleur;
    }

    public int getRouge()
    {
        return couleur.getRed();
    }

    public int getVert()
    {
        return couleur.getGreen();
    }

    public int getBleu()
    {
        return couleur.getBlue();
    }
}
