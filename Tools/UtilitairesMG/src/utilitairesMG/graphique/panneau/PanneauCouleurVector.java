// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauCouleurVector
// --------------------------------------------------------------------------
// Classe mere de tous les panneaux de type PanneauCouleur qui se presentent
// graphiquement comme l'affichage d'un vecteur de rectangles
// (RectangleCouleur).
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import utilitairesMG.graphique.evenement.*;

public abstract class PanneauCouleurVector extends PanneauCouleur
    implements MouseListener,
    MouseMotionListener
{
    private Vector<RectangleCouleur> vRect = new Vector<RectangleCouleur>();
    private Curseur curseur;

// --------------------------------------------------------------------------
// Constructeur.
// --------------------------------------------------------------------------
    public PanneauCouleurVector(Dimension dimensionRect)
    {
        curseur = new Curseur(0, 0, 7, 7);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

// --------------------------------------------------------------------------
// Setters et Getters
// --------------------------------------------------------------------------
    public void setVRect(Vector<RectangleCouleur> vRect)
    {
        this.vRect = vRect;
    }

    public Vector<RectangleCouleur> getVRect()
    {
        return vRect;
    }

// --------------------------------------------------------------------------
// Deplacement du curseur.
// --------------------------------------------------------------------------
// Deux parametres x, y : le panneau a deux dimensions (lignes et colonnes).
// Un parametre x       : le panneau a une dimension (une seule ligne)
// --------------------------------------------------------------------------
    public void deplaceCurseur(int x, int y)
    {
        curseur.x = x;
        curseur.y = y;
        repaint();
    }

    public void deplaceCurseur(int x)
    {
        curseur.x = x;
        repaint();
    }

// --------------------------------------------------------------------------
// trouveRectangle.
// --------------------------------------------------------------------------
    public int trouveRectangle(Point p)
    {
        int i;
        RectangleCouleur rect;
        int rectTrouve = -1;

        i = 0;
        while ((rectTrouve == -1) && (i < vRect.size()))
        {
            rect = vRect.elementAt(i);
            if (rect.contains(p))
            {
                rectTrouve = i;
            }
            i++;
        }
        return rectTrouve;
    }

// --------------------------------------------------------------------------
// Retourne la couleur ou se trouve le curseur.
// --------------------------------------------------------------------------
    public Color getCouleurAuCurseur()
    {
        Point p = new Point(curseur.x, curseur.y);
        int numero = trouveRectangle(p);
        return vRect.elementAt(numero).getCouleur();
    }

// --------------------------------------------------------------------------
// paintComponent
// --------------------------------------------------------------------------
    public void paintComponent(Graphics g)
    {
        int i;
        RectangleCouleur rect;

        super.paintComponent(g);
        for (i = 0; i < vRect.size(); i++)
        {
            rect = vRect.elementAt(i);
            rect.affiche(g);
        }

        curseur.affiche(g);
    }

// --------------------------------------------------------------------------
// Evenements MouseEvent
// --------------------------------------------------------------------------
// Quand on "presse" la souris ou qu'on la "drague", envoyer un evenement
// CouleurEvent aux ecouteurs du PanneauCouleurVector.
// --------------------------------------------------------------------------
    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        int numeroRect;

        numeroRect = trouveRectangle(e.getPoint());
        if (numeroRect != -1)
        {
            curseur.x = e.getX();
            curseur.y = e.getY();
            repaint();

            Color couleur = vRect.elementAt(numeroRect).getCouleur();
            fireCouleurSelected(new CouleurEvent(this, couleur));
        }
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseDragged(MouseEvent e)
    {
        Dimension taillePanneau = getSize();
        Point posCurseur = e.getPoint();

        if (posCurseur.x < 0)
        {
            posCurseur.x = 0;
        }
        if (posCurseur.x > taillePanneau.width - 1)
        {
            posCurseur.x = taillePanneau.width - 1;
        }

        if (posCurseur.y < 0)
        {
            posCurseur.y = 0;
        }
        if (posCurseur.y > taillePanneau.height - 1)
        {
            posCurseur.y = taillePanneau.height - 1;
        }

        curseur.x = posCurseur.x;
        curseur.y = posCurseur.y;
        repaint();

        int numeroRect;

        numeroRect = trouveRectangle(posCurseur);
        Color couleur = vRect.elementAt(numeroRect).getCouleur();
        fireCouleurSelected(new CouleurEvent(this, couleur));
    }

    public void mouseMoved(MouseEvent e)
    {
    }
}
