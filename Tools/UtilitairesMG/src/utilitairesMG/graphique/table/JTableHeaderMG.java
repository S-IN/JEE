// ==========================================================================
// Classe JTableHeaderMG                                  TestsCompletsTables
// --------------------------------------------------------------------------
// JTableHeader equipe d'un ecouteur de souris pour detecter les clics sur un
// entete de colonne.
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.table.*;
import java.awt.event.*;

public class JTableHeaderMG extends JTableHeader implements MouseListener
{
    private int derniereSelection = 0;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public JTableHeaderMG()
    {
        super();
        addMouseListener(this);
    }

    public void setDerniereSelection(int derniereSelection)
    {
        this.derniereSelection = derniereSelection;
    }

// --------------------------------------------------------------------------
// Méthodes de l'interface MouseListener
// --------------------------------------------------------------------------
// --------------------------------------------------------------------------
// On clique avec la souris : Pressed + Released
// --------------------------------------------------------------------------
    public void mouseClicked(MouseEvent e)
    {
        int touche;
        int c, cm;

// --------------------------------------------------------------------------
// On recupere le numero de la touche du clavier enfoncee lors du clic de
// la souris grace a la methode getModifiers.
// Les valeurs sont : 16 (normal) 17 (shift) 18 (ctrl) 24 (alt)...
// --------------------------------------------------------------------------
        touche = e.getModifiers();

// --------------------------------------------------------------------------
// La methode columnAtPoint() indique le numero de la colonne de la table
// ou on clique. Ce numero est le numero de la colonne telle qu'on la voit.
// Si on clique a cote de la table a hauteur du header, c vaudra -1
// --------------------------------------------------------------------------
        c = columnAtPoint(e.getPoint());

// --------------------------------------------------------------------------
// Il est possible que le numero c ne corresponde pas au numero de la colonne
// dans le modele de colonnes (a cause d'un deplacement de colonne a la
// souris par exemple). La methode convertColumnIndexToModel() permet de
// retrouver le numero dans le modele...
// Si c vaut -1, cm aussi...
// --------------------------------------------------------------------------
        cm = getTable().convertColumnIndexToModel(c);

        if (c != -1)
        {
            getTable().setCellSelectionEnabled(false);
            getTable().setColumnSelectionAllowed(true);

            if (touche == 16)    // Normal
            {
                getTable().setColumnSelectionInterval(c, c);
                derniereSelection = c;
            }
            if (touche == 18)    // Ctrl
            {
                getTable().addColumnSelectionInterval(c, c);
                derniereSelection = c;
            }
            if (touche == 17)    // Shift
            {
                getTable().setColumnSelectionInterval(c, derniereSelection);
            }
        }
    }

// --------------------------------------------------------------------------
// On enfonce un bouton de la souris
// --------------------------------------------------------------------------
    public void mousePressed(MouseEvent e)
    {
    }

// --------------------------------------------------------------------------
// On relâche un bouton de la souris
// --------------------------------------------------------------------------
    public void mouseReleased(MouseEvent e)
    {
    }

// --------------------------------------------------------------------------
// La souris entre dans la fenêtre
// --------------------------------------------------------------------------
    public void mouseEntered(MouseEvent e)
    {
    }

// --------------------------------------------------------------------------
// La souris sort de la fenêtre
// --------------------------------------------------------------------------
    public void mouseExited(MouseEvent e)
    {
    }
}
