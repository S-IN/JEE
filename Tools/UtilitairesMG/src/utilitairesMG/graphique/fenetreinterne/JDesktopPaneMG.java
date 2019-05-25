// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe JDesktopPaneMG
// --------------------------------------------------------------------------
// Cette classe utilise un DesktopManager particulier qui "piste" les
// deplacements des fenetres internes...
// --------------------------------------------------------------------------
// Cette classe n'est interessante que si l'on utilise un JScrollPaneMG.
// ==========================================================================
package utilitairesMG.graphique.fenetreinterne;

import javax.swing.*;
import java.awt.*;

public class JDesktopPaneMG extends JDesktopPane
{

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public JDesktopPaneMG()
    {
        setDesktopManager(new DesktopManagerMG(this));
    }

// --------------------------------------------------------------------------
// Calcul de la taille preferee du panneau en fonction de la taille et de la
// position des fenetres internes.
// --------------------------------------------------------------------------
    public void calculePreferredSize()
    {
        Dimension taille;

        JInternalFrame[] listeFenetres;
        Rectangle coordooneesFenInt;
        int xMini = 0;
        int xMaxi = 0;
        int yMini = 0;
        int yMaxi = 0;

        JViewport jv;
        Dimension tailleViewport;
        Point posViewport;

// --------------------------------------------------------------------------
// Position et taille actuelle du JViewport de la zone de scrolling
// --------------------------------------------------------------------------
        jv = (JViewport) getParent();
        posViewport = jv.getViewPosition();
        tailleViewport = jv.getSize();

// --------------------------------------------------------------------------
// Recuperer la liste des fenetres internes
// --------------------------------------------------------------------------
        listeFenetres = getAllFrames();

// --------------------------------------------------------------------------
// Calculer les positions extremes des fenetres internes
// --------------------------------------------------------------------------
        for (int i = 0; i < listeFenetres.length; i++)
        {
            coordooneesFenInt = listeFenetres[i].getBounds();
            if (coordooneesFenInt.x < xMini)
            {
                xMini = coordooneesFenInt.x;
            }
            if (coordooneesFenInt.x + coordooneesFenInt.width > xMaxi)
            {
                xMaxi = coordooneesFenInt.x + coordooneesFenInt.width;
            }

            if (coordooneesFenInt.y < yMini)
            {
                yMini = coordooneesFenInt.y;
            }
            if (coordooneesFenInt.y + coordooneesFenInt.height > yMaxi)
            {
                yMaxi = coordooneesFenInt.y + coordooneesFenInt.height;
            }
        }

// --------------------------------------------------------------------------
// Initialisation de la taille du panneau a celle du rectangle
// --------------------------------------------------------------------------
        taille = new Dimension();
        taille.width = xMaxi - xMini;
        taille.height = yMaxi - yMini;

// --------------------------------------------------------------------------
// Le panneau doit au moins atteindre le coin en bas a droite du JViewport
// --------------------------------------------------------------------------
        if (posViewport.x > 0)
        {
            if (taille.width < posViewport.x + tailleViewport.width)
            {
                taille.width = posViewport.x + tailleViewport.width;
            }
        }

        if (posViewport.y > 0)
        {
            if (taille.height < posViewport.y + tailleViewport.height)
            {
                taille.height = posViewport.y + tailleViewport.height;
            }
        }

        setPreferredSize(taille);
    }

// --------------------------------------------------------------------------
// Surcharge de la methode add de la classe Component, pour recalculer
// automatiquement la taille preferee du panneau en cas d'ajout d'une fenetre
// interne.
// --------------------------------------------------------------------------
    public Component add(Component comp)
    {
        super.add(comp);
        calculePreferredSize();
        return comp;
    }
}
