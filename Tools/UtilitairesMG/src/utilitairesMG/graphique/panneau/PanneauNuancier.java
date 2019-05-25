// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauNuancier
// --------------------------------------------------------------------------
// Ce panneau de type PanneauCouleurVector presente une seule couleur avec
// toutes ses nuances en terme de luminosite et de saturation.
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.awt.*;
import java.util.Vector;
import utilitairesMG.divers.*;

public class PanneauNuancier extends PanneauCouleurVector
{

// --------------------------------------------------------------------------
// Constructeur.
// --------------------------------------------------------------------------
    public PanneauNuancier(Dimension dimensionRect, Color couleur)
    {
        super(dimensionRect);

        setPreferredSize(new Dimension(dimensionRect.width * 100,
            dimensionRect.height * 100));

// --------------------------------------------------------------------------
// Boucle de creation des rectangles du PanneauNuancier.
// --------------------------------------------------------------------------
        for (int lig = 0; lig <= 99; lig++)
        {

            for (int col = 0; col <= 99; col++)
            {
                int x = col * dimensionRect.width;
                int y = lig * dimensionRect.height;

                RectangleCouleur rect = new RectangleCouleur(x, y,
                    dimensionRect,
                    Color.BLACK);
                getVRect().add(rect);
            }
        }

        changeCouleurRectangles(couleur);
    }

// --------------------------------------------------------------------------
// Modification de la couleur des RectangleCouleur qui composent le panneau.
// Cette methode sert aussi a l'initialisation du panneau (voir le
// constructeur).
// --------------------------------------------------------------------------
    public void changeCouleurRectangles(Color couleur)
    {

// --------------------------------------------------------------------------
// Recuperation de la teinte en fonction de la couleur recue en parametre
// --------------------------------------------------------------------------
        float tabHSB[] = new float[3];
        Color.RGBtoHSB(couleur.getRed(),
            couleur.getGreen(),
            couleur.getBlue(),
            tabHSB);

// --------------------------------------------------------------------------
// Boucles de modification de la couleur des rectangles
// --------------------------------------------------------------------------
// Les lignes sont de moins en moins lumineuses (c'est-a-dire vers le noir).
// --------------------------------------------------------------------------
        float teinte, s, b;
        teinte = tabHSB[0];

        for (int lig = 0; lig <= 99; lig++)
        {

// --------------------------------------------------------------------------
// Les colonnes sont de moins en moins saturees (c'est-a-dire vers le blanc).
// --------------------------------------------------------------------------
            for (int col = 0; col <= 99; col++)
            {
                s = ((float) (99 - col)) / 99;
                b = ((float) (99 - lig)) / 99;

                RectangleCouleur rect = getVRect().elementAt((lig * 100) + col);

                rect.setCouleur(Color.getHSBColor(teinte, s, b));
            }
        }
    }

// --------------------------------------------------------------------------
// Modification dynamique de la couleur presentee par le panneau.
// --------------------------------------------------------------------------
    public void modifierPanneauNuancier(Color couleur)
    {
        changeCouleurRectangles(couleur);
        repaint();
    }

// --------------------------------------------------------------------------
// trouver le rectangle le plus proche de la saturation et de la luminosite
// transmises.
// --------------------------------------------------------------------------
    public int trouveRectangleSB(float s, float b)
    {
        Vector<RectangleCouleur> vRect;

        RectangleCouleur rect;
        float sRect, bRect;

        int i;
        int poste;
        int rectTrouve;
        int ligTrouve, colTrouve;

        float ecartS, minEcartS;
        float ecartB, minEcartB;

        vRect = getVRect();

        rect = vRect.elementAt(0);
        sRect = ConversionCouleur.getS(rect.getCouleur());
        minEcartS = Math.abs(s - sRect);
        bRect = ConversionCouleur.getB(rect.getCouleur());
        minEcartB = Math.abs(b - bRect);

        ligTrouve = 0;
        colTrouve = 0;

        i = 1;
        while (i <= 99)
        {
            poste = i * 100;
            rect = vRect.elementAt(poste);
            bRect = ConversionCouleur.getB(rect.getCouleur());
            ecartB = Math.abs(b - bRect);
            if (ecartB < minEcartB)
            {
                ligTrouve = i;
                minEcartB = ecartB;
            }
            i++;
        }

        i = 1;
        while (i <= 99)
        {
            poste = i;
            rect = vRect.elementAt(poste);
            sRect = ConversionCouleur.getS(rect.getCouleur());
            ecartS = Math.abs(s - sRect);

            if (ecartS < minEcartS)
            {
                colTrouve = i;
                minEcartS = ecartS;
            }
            i++;
        }

        rectTrouve = (ligTrouve * 100) + colTrouve;
        return rectTrouve;
    }

// --------------------------------------------------------------------------
// Positionne le curseur sur un rectangle
// --------------------------------------------------------------------------
    public void placeCurseur(int numeroRect)
    {
        RectangleCouleur rect = getVRect().elementAt(numeroRect);
        deplaceCurseur(rect.x, rect.y);
    }
}
