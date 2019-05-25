// ==========================================================================
// package utilitairesMG.graphique.evenement
// --------------------------------------------------------------------------
// Interface CouleurListener
// --------------------------------------------------------------------------
// Interface pour creer des ecouteurs d'evenements de choix de couleur sur un
// panneau de type PanneauCouleur
// ==========================================================================
package utilitairesMG.graphique.evenement;

import java.util.*;

public interface CouleurListener extends EventListener
{

// --------------------------------------------------------------------------
// Methodes
// --------------------------------------------------------------------------
    public void couleurSelected(CouleurEvent e);
}
