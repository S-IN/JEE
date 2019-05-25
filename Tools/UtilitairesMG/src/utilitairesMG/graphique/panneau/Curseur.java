// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe Curseur
// --------------------------------------------------------------------------
// Curseur pour les panneaux de type PanneauCouleurVector
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.awt.*;

public class Curseur extends Rectangle
{
    public Curseur(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

// --------------------------------------------------------------------------
// Affichage du curseur
// --------------------------------------------------------------------------
    public void affiche(Graphics g)
    {
        int decal = width / 2;

        g.setColor(Color.BLACK);
        g.fillRect(x - decal, y - decal, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x - decal, y - decal, width, height);
    }
}
