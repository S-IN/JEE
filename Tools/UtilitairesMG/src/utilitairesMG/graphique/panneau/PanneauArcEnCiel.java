// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauArcEnCiel
// --------------------------------------------------------------------------
// Panneau d'affichage de toutes les couleurs "pleines" de la palette.
// Une couleur "pleine" est au maximum de luminosite et de saturation.
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.awt.*;
import java.util.*;
import utilitairesMG.divers.*;

public class PanneauArcEnCiel extends PanneauCouleurVector
{

// --------------------------------------------------------------------------
// Constructeur.
// --------------------------------------------------------------------------
    public PanneauArcEnCiel(Dimension dimensionRect)
    {
        super(dimensionRect);

        setPreferredSize(new Dimension(dimensionRect.width * 360,
            dimensionRect.height));

// --------------------------------------------------------------------------
// Boucle sur les teintes de la ligne de couleur (arc en ciel).
// La saturation et la luminosite sont maximales (1.0f)
// --------------------------------------------------------------------------
        float teinte;

        for (int col = 0; col <= 359; col++)
        {
            teinte = (float) col / 359;
            int x = col * dimensionRect.width;
            int y = 0;

            RectangleCouleur rect = new RectangleCouleur(x, y, dimensionRect,
                Color.getHSBColor(teinte, 1.0f, 1.0f));

            getVRect().add(rect);
        }
    }

// --------------------------------------------------------------------------
// trouver le rectangle le plus proche de la teinte transmise.
// --------------------------------------------------------------------------
    public int trouveRectangleTeinte(float teinte)
    {
        Vector<RectangleCouleur> vRect;
        int i;
        RectangleCouleur rect;
        float teinteRect;
        int rectTrouve;
        float ecart, minEcart;

        vRect = getVRect();

        rect = vRect.elementAt(0);
        teinteRect = ConversionCouleur.getH(rect.getCouleur());
        minEcart = Math.abs(teinte - teinteRect);
        rectTrouve = 0;

        i = 1;
        while (i < vRect.size())
        {
            rect = vRect.elementAt(i);
            teinteRect = ConversionCouleur.getH(rect.getCouleur());
            ecart = Math.abs(teinte - teinteRect);

            if (ecart < minEcart)
            {
                rectTrouve = i;
                minEcart = ecart;
            }
            i++;
        }

        return rectTrouve;
    }

// --------------------------------------------------------------------------
// Positionne le curseur sur un rectangle
// --------------------------------------------------------------------------
    public void placeCurseur(int numeroRect)
    {
        RectangleCouleur rect = getVRect().elementAt(numeroRect);
        deplaceCurseur(rect.x);
    }
}
