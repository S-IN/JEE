// ==========================================================================
// Classe EcouteTable
// --------------------------------------------------------------------------
// Ecouteur d'une JTable pour repérer un clic (mousePressed) dans une cellule
// de la JTable. Il s'agit de changer le mode de selection (Row) qui a
// eventuellement change en cliquant sur le header (Column).
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class EcouteTable extends MouseAdapter
{
    private JTable table;

// --------------------------------------------------------------------------
// Derniere cellule sur laquelle l'evenement PanneauTableEvent s'est produit.
// --------------------------------------------------------------------------
    private int ligneAnc = -1;
    private int colonneAnc = -1;

// --------------------------------------------------------------------------
// CONSTRUCTEUR
// --------------------------------------------------------------------------
// L'ecouteur note la reference de la table "ecoutee"
// --------------------------------------------------------------------------
    public EcouteTable(JTable table)
    {
        this.table = table;
    }

// --------------------------------------------------------------------------
// Ecoute d'un clic de souris (en fait un "pressed")
// --------------------------------------------------------------------------
// L'evenement se produit quand on clique dans une des cellules de la JTable.
// On active alors la selection des lignes de la table. C'est utile quand
// l'utilisateur a auparavant active la selection par colonne en cliquant sur
// le "header" d'une des colonnes.
// --------------------------------------------------------------------------
    public void mousePressed(MouseEvent e)
    {
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        ((JTableHeaderMG) table.getTableHeader()).
            setDerniereSelection(table.getSelectedColumn());
    }

// --------------------------------------------------------------------------
// Ecoute d'un mouvement de la souris
// --------------------------------------------------------------------------
// Quand la souris passe sur une cellule de la table, on recupere les numeros
// de ligne et de colonne de la cellule, et la valeur contenue. On genere
// alors un evenement de type "PanneauTableEvent" qui est transmis au
// PanneauTable contenant la table. Celui-ci pourra s'il le souhaite
// "ecouter" cet evenement pour le traiter.
// --------------------------------------------------------------------------
    public void mouseMoved(MouseEvent e)
    {
        Point p = e.getPoint();

        int ligne = table.rowAtPoint(p);

        if (ligne != -1)
        {
            int colonne = table.convertColumnIndexToModel(table.columnAtPoint(p));
            Object valeur = table.getValueAt(ligne, table.columnAtPoint(p));

            if ((ligne != ligneAnc) || (colonne != colonneAnc))
            {
                PanneauTable source
                    = (PanneauTable) table.getParent(). // JPanel ()
                    getParent(). // PanneauJTable
                    getParent(). // JViewport
                    getParent(). // JScrollPane
                    getParent();   // PanneauTable

                source.firePanneauTableSelected(
                    new PanneauTableEvent(source, ligne, colonne, valeur));

                ligneAnc = ligne;
                colonneAnc = colonne;
            }
        }
    }
}
