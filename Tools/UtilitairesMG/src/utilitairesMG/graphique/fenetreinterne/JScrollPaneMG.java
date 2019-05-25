// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe JScrollPaneMG
// --------------------------------------------------------------------------
// JScrollPane qui ecoute les changements de son JViewport.
// Cette classe ne travaille qu'avec un client de type JDesktopPaneMG.
// ==========================================================================
package utilitairesMG.graphique.fenetreinterne;

import javax.swing.*;
import javax.swing.event.*;

public class JScrollPaneMG extends JScrollPane implements ChangeListener
{
    private JDesktopPaneMG vue;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public JScrollPaneMG(JDesktopPaneMG vue)
    {
        super(vue);

        this.vue = vue;
        getViewport().addChangeListener(this);
    }

// --------------------------------------------------------------------------
// Changement du JViewport (ChangeEvent)
// --------------------------------------------------------------------------
    public void stateChanged(ChangeEvent e)
    {
        vue.calculePreferredSize();
    }
}
