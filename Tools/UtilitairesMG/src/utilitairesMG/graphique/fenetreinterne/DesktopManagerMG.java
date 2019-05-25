// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe DesktopManagerMG
// --------------------------------------------------------------------------
// Cette classe permet de gerer le "draggage" d'une fenetre interne en vue
// de faire apparaitre (ou disparaitre) les barres de defilement dans un
// panneau de type JDesktopPaneMG.
// ==========================================================================
package utilitairesMG.graphique.fenetreinterne;

import javax.swing.*;

public class DesktopManagerMG extends DefaultDesktopManager
{
    private JDesktopPaneMG panneau;

// --------------------------------------------------------------------------
// Constructeur (recoit en parametre le JDesktopPaneMG a gerer).
// Il n'y a pas de methode qui fait cela automatiquement...
// --------------------------------------------------------------------------
    public DesktopManagerMG(JDesktopPaneMG panneau)
    {
        this.panneau = panneau;
    }

// --------------------------------------------------------------------------
// Methode appelee lors du "draggage" de la fenetre interne f.
// --------------------------------------------------------------------------
// newX et newY sont les coordonnees de la fenetre au moment de l'evenement
// de "draggage".
// --------------------------------------------------------------------------
// La taille preferee du panneau est recalculee au fur et a mesure du
// "draggage". La fenetre interne ne peut sortir en haut ou a gauche du
// panneau.
// --------------------------------------------------------------------------
    public void dragFrame(JComponent f, int newX, int newY)
    {

// --------------------------------------------------------------------------
// Si la fenetre est "draggee" hors du panneau (a gauche ou en haut), on la
// bloque, en la repositionnant en x=0 et/ou y=0...
// --------------------------------------------------------------------------
        if ((newX < 0) || (newY < 0))
        {
            if (newX < 0)
            {
                newX = 0;
            }
            if (newY < 0)
            {
                newY = 0;
            }
        }

// --------------------------------------------------------------------------
// dragFrame deplace la fenetre a l'emplacent indique...
// --------------------------------------------------------------------------
        super.dragFrame(f, newX, newY);

// --------------------------------------------------------------------------
// Calcul de la nouvelle taille preferee du panneau qui contient la fenetre f
// --------------------------------------------------------------------------
        panneau.calculePreferredSize();

// --------------------------------------------------------------------------
// Le revalidate() envoie l'ordre de se redessiner a un JScrollPane eventuel
// --------------------------------------------------------------------------
        panneau.revalidate();
    }

// --------------------------------------------------------------------------
// Methode qui s'execute en fin de "draggage" de la fenetre f.
// --------------------------------------------------------------------------
// Cette surcharge est necessaire quand on emploie l'option :
// panneauFenetres.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE) car les barres
// de defilement n'apparaissent pas au cours de l'execution de dragFrame (il
// s'agit sans doute d'une optimisation de l'affichage).
// --------------------------------------------------------------------------
    public void endDraggingFrame(JComponent f)
    {
        super.endDraggingFrame(f);

        panneau.calculePreferredSize();
        panneau.revalidate();
    }
}
