// ==========================================================================
// package utilitairesMG.graphique.table
// --------------------------------------------------------------------------
// Classe PanneauJTable
// --------------------------------------------------------------------------
// Cette classe est destinee a eviter la zone grise qui apparait au niveau
// du Header de la JTable, quand celle-ci est placee dans un JScrollPane.
// En effet, si le client du JScrollPane est la JTable, quand le JViewport
// s'agrandit, la JTable reste de taille fixe, mais pas son JTableHeader...
// Cela ressemble a une anomalie...
// --------------------------------------------------------------------------
// Pour corriger cela, cette classe met la JTable dans un panneau organise en
// BorderLayout, la JTable dans la zone centre et le JTableHeader en zone
// nord. Ce panneau est ensuite ajoute au panneau courant organise en
// FlowLayout cadre a gauche et sans espace en haut et a gauche, pour eviter
// qu'il se recentre automatiquement quand le panneau courant change de
// taille.
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;
import java.awt.*;

public class PanneauJTable extends JPanel
{

// --------------------------------------------------------------------------
// CONSTRUCTEUR
// --------------------------------------------------------------------------
    public PanneauJTable(JTable table)
    {
        JPanel panneauBorderLayout = new JPanel();
        panneauBorderLayout.setLayout(new BorderLayout());

        panneauBorderLayout.add(table.getTableHeader(), BorderLayout.NORTH);
        panneauBorderLayout.add(table);

        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        fl.setHgap(0);
        fl.setVgap(0);
        setLayout(fl);

        add(panneauBorderLayout);
    }
}
