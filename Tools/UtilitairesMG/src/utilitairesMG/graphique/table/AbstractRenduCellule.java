// ==========================================================================
// package utilitairesMG.graphique.table
// --------------------------------------------------------------------------
// Classe AbstractRenduCellule
// ==========================================================================
package utilitairesMG.graphique.table;

import java.awt.*;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.border.*;

public abstract class AbstractRenduCellule implements TableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column)
    {
        JComponent composant;
        Border bords;

// --------------------------------------------------------------------------
// Recuperation du composant (sera precise dans les classes derivees)
// --------------------------------------------------------------------------
        composant = getComposant();

// --------------------------------------------------------------------------
// Couleurs et bordures :
//    la cellule est selectionnee ou pas,
//    elle a le focus ou pas (elle peut etre selectionnee au sein d'une ligne
//    mais ne pas avoir le focus).
// --------------------------------------------------------------------------
        if (isSelected)
        {
            composant.setForeground(table.getSelectionForeground());
            composant.setBackground(table.getSelectionBackground());

            if (hasFocus)
            {
                bords = BorderFactory.createLineBorder(
                    table.getSelectionForeground());
            }
            else
            {
                bords = BorderFactory.createLineBorder(
                    table.getSelectionBackground());
            }
        }
        else
        {
            composant.setForeground(table.getForeground());
            composant.setBackground(table.getBackground());
            bords = BorderFactory.createLineBorder(table.getBackground());
        }
        composant.setBorder(bords);

// --------------------------------------------------------------------------
// Initialisation du composant selon la valeur a afficher (value)
// --------------------------------------------------------------------------
        initComposant(value);
        return composant;
    }

    public abstract JComponent getComposant();

    public abstract void initComposant(Object value);

    public abstract String conversionObjectString(Object value);
}
