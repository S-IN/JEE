// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauCouleur
// --------------------------------------------------------------------------
// Classe mere de tous les panneaux qui emettent des evenements CouleurEvent.
// Elle contient les methodes d'ajout ou de suppression d'un ecouteur, et la
// methode "fire" qui avertit les ecouteurs que l'evenement s'est produit.
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.util.*;
import javax.swing.*;
import utilitairesMG.graphique.evenement.*;

public abstract class PanneauCouleur extends JPanel
{

// --------------------------------------------------------------------------
// Methode pour ajouter un listener a la liste des ecouteurs de
// PanneauCouleur.
// --------------------------------------------------------------------------
    public void addCouleurListener(CouleurListener p)
    {
        listenerList.add(CouleurListener.class, p);
    }

// --------------------------------------------------------------------------
// Methode pour enlever un listener a la liste des ecouteurs de
// PanneauCouleur.
// --------------------------------------------------------------------------
    public void removeCouleurListener(CouleurListener p)
    {
        listenerList.remove(CouleurListener.class, p);
    }

// --------------------------------------------------------------------------
// Lancement de l'evenement
// --------------------------------------------------------------------------
    public void fireCouleurSelected(CouleurEvent e)
    {
        EventListener[] listeners
            = listenerList.getListeners(CouleurListener.class);

        for (int i = 0; i < listeners.length; i++)
        {
            CouleurListener listener = (CouleurListener) listeners[i];
            listener.couleurSelected(e);
        }
    }
}
