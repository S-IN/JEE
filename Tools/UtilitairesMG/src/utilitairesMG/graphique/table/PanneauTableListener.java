// ==========================================================================
// Interface PanneauTableListener                         TestsCompletsTables
// --------------------------------------------------------------------------
// Interface pour creer des ecouteurs d'evenements PanneauTableEvent
// ==========================================================================
package utilitairesMG.graphique.table;

import java.util.*;

public interface PanneauTableListener extends EventListener
{

// --------------------------------------------------------------------------
// Methodes
// --------------------------------------------------------------------------
    public void panneauTableSelected(PanneauTableEvent e);
}
