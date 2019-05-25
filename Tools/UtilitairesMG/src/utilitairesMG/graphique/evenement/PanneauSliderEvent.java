// ==========================================================================
// package utilitairesMG.graphique.evenement
// --------------------------------------------------------------------------
// Classe PanneauSliderEvent
// --------------------------------------------------------------------------
// Evenement qui se produit quand on modifie le JSlider ou le JSpinner du
// panneau PanneauSlider
// ==========================================================================
package utilitairesMG.graphique.evenement;

import java.util.*;

public class PanneauSliderEvent extends EventObject
{

// --------------------------------------------------------------------------
// Proprietes
// --------------------------------------------------------------------------
    private int valeur;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public PanneauSliderEvent(Object source, int valeur)
    {
        super(source);
        this.valeur = valeur;
    }

// --------------------------------------------------------------------------
// Getters
// --------------------------------------------------------------------------
    public int getValeur()
    {
        return valeur;
    }
}
