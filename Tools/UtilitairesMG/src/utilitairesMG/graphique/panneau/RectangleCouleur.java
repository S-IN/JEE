// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe RectangleCouleur
// --------------------------------------------------------------------------
// Cette classe herite de Rectangle en ajoutant une propriete Color.
// Par heritage de Rectangle, on peut acceder aux proprietes publiques :
// x, y, height, width.
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.awt.*;

public class RectangleCouleur extends Rectangle
{

// --------------------------------------------------------------------------
// Propriete : couleur du rectangle
// --------------------------------------------------------------------------
    private Color couleur;

// --------------------------------------------------------------------------
// Constructeurs
// --------------------------------------------------------------------------
    public RectangleCouleur(int x, int y, Dimension taille, Color couleur)
    {
        super(x, y, taille.width, taille.height);
        this.couleur = couleur;
    }

// --------------------------------------------------------------------------
// getters
// --------------------------------------------------------------------------
    public Color getCouleur()
    {
        return couleur;
    }

// --------------------------------------------------------------------------
// setters
// --------------------------------------------------------------------------
    public void setCouleur(Color couleur)
    {
        this.couleur = couleur;
    }

// --------------------------------------------------------------------------
// affiche
// --------------------------------------------------------------------------
    public void affiche(Graphics g)
    {
        g.setColor(couleur);
        g.fillRect(x, y, width, height);
    }
}
