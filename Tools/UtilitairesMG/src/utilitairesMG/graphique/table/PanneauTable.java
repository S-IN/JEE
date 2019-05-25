// ==========================================================================
// Classe PanneauTable                                    TestsCompletsTables
// --------------------------------------------------------------------------
// Panneau contenant une JTable dans un defileur
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;

public class PanneauTable extends JPanel
{

// ==========================================================================
// Proprietes
// ==========================================================================
// --------------------------------------------------------------------------
// JTable
// --------------------------------------------------------------------------
// PanneauJTable : panneau qui contient une JTable. Permet d'afficher
// separement le header et le contenu de la JTable (evite l'anomalie
// d'affichage du header).
// --------------------------------------------------------------------------
// JScrollPane qui contient ce panneau.
// --------------------------------------------------------------------------
    private JTable table;
    private PanneauJTable panneauJTable;
    private JScrollPane defileur;

// ==========================================================================
// Constructeur
// ==========================================================================
    public PanneauTable()
    {
        setLayout(new BorderLayout());

// --------------------------------------------------------------------------
// Création de l'objet JTable
// --------------------------------------------------------------------------
// Le Header et l'ecouteur servent a traiter les evenements de selection sur
// la JTable.
// --------------------------------------------------------------------------
        table = new JTable();
        table.setTableHeader(new JTableHeaderMG());
        table.addMouseListener(new EcouteTable(table));
        table.addMouseMotionListener(new EcouteTable(table));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// --------------------------------------------------------------------------
// Mise de la JTable dans un defileur et ajout du defileur au panneau
// panneauTableMG permet d'avoir un header de JTable correct.
// --------------------------------------------------------------------------
        panneauJTable = new PanneauJTable(table);
        defileur = new JScrollPane(panneauJTable);
        add(defileur);
    }

// ==========================================================================
// Methodes
// ==========================================================================
// --------------------------------------------------------------------------
// getter de la JTable
// --------------------------------------------------------------------------
    public JTable getTable()
    {
        return table;
    }

// --------------------------------------------------------------------------
// getter du panneau de presentation de la JTable
// --------------------------------------------------------------------------
    public PanneauJTable getPanneauJTable()
    {
        return panneauJTable;
    }

// --------------------------------------------------------------------------
// getter du defileur
// --------------------------------------------------------------------------
    public JScrollPane getDefileur()
    {
        return defileur;
    }

// --------------------------------------------------------------------------
// Taille de la police (nombre de points de la lettre M)
// --------------------------------------------------------------------------
    public int getTailleM()
    {
        Font fontParDefaut = table.getFont();
        return table.getFontMetrics(fontParDefaut).stringWidth("M");
    }

// --------------------------------------------------------------------------
// Tri des lignes correspondant aux colonnes selectionnees de la table
// --------------------------------------------------------------------------
    public void trierLignes(boolean triAscendant)
    {
        int[] colonnesSelVisibles;
        int[] colonnesSelModele;

        if (table.getColumnSelectionAllowed())
        {
            colonnesSelVisibles = table.getSelectedColumns();
            colonnesSelModele = new int[colonnesSelVisibles.length];

            if (colonnesSelVisibles.length > 0)
            {

// --------------------------------------------------------------------------
// colonnesSelVisibles contient les numeros des colonnes VISIBLES de la
// table. La boucle suivante permet d'avoir la liste des numeros des colonnes
// selectionnees du MODELE. Les colonnes ont pu etre deplacees avec la
// souris...
// --------------------------------------------------------------------------
                for (int i = 0; i < colonnesSelVisibles.length; i++)
                {
                    colonnesSelModele[i]
                        = table.convertColumnIndexToModel(
                            colonnesSelVisibles[i]);
                }

                ModeleTable modeleTable = (ModeleTable) table.getModel();
                modeleTable.trier(colonnesSelModele, triAscendant);
                table.repaint();
                table.revalidate();
            }
        }
    }

// --------------------------------------------------------------------------
// Recherche d'une chaine dans une colonne de la table.
// --------------------------------------------------------------------------
// Cette recherche se fait sur les valeurs VISIBLES de la JTable et non sur
// les valeurs du vecteur de lignes contenu dans le modele. C'est pour cela
// que cette methode est declaree ici et non dans le modele de table
// --------------------------------------------------------------------------
// Valeur de retour : -1 (chaine non trouvee) ou numero de la ligne
// --------------------------------------------------------------------------
// nCol : colonne dans laquelle on cherche
// cherchee : chaine cherchee
// ligDepart : ligne de debut de la recherche
// option : 0 (chaine entiere), 1 (debut de chaine), 2 (n'importe ou)
// --------------------------------------------------------------------------
    public int chercherLigne(String cherchee, int ligDepart, int option)
    {
        int[] colonnesSelVisibles;
        int colSelect;
        int retour = -1;
        String valeur;

// --------------------------------------------------------------------------
// Conditions pour que la recherche se fasse :
// --------------------------------------------------------------------------
// La selection des colonnes de la table est activee
// Une et une seule colonne est selectionnee
// Cette colonne a un rendu (Renderer) de type JLabel
// --------------------------------------------------------------------------
        if (table.getColumnSelectionAllowed())
        {
            colonnesSelVisibles = table.getSelectedColumns();

// --------------------------------------------------------------------------
// On ne recherche que s'il n'y a qu'une colonne selectionnee
// --------------------------------------------------------------------------
            if (colonnesSelVisibles.length == 1)
            {
                colSelect = colonnesSelVisibles[0];

// --------------------------------------------------------------------------
// Recherche du rendu (Renderer) de la colonne
// --------------------------------------------------------------------------
                TableColumnModel modeleColonne = table.getColumnModel();
                TableColumn colonne = modeleColonne.getColumn(colSelect);
                TableCellRenderer rendu = colonne.getCellRenderer();

// --------------------------------------------------------------------------
// Boucle de recherche
// --------------------------------------------------------------------------
                int i = ligDepart;

                while ((i < table.getRowCount()) && (retour == -1))
                {
                    valeur = ((AbstractRenduCellule) rendu).
                        conversionObjectString(table.getValueAt(i, colSelect));

                    if (valeur != null)
                    {
                        valeur = valeur.toUpperCase();
                        cherchee = cherchee.toUpperCase();

                        switch (option)
                        {
                            case 0:
                                if (valeur.compareTo(cherchee) == 0)
                                {
                                    retour = i;
                                }
                                break;
                            case 1:
                                if (valeur.startsWith(cherchee))
                                {
                                    retour = i;
                                }
                                break;
                            case 2:
                                if (valeur.contains(cherchee))
                                {
                                    retour = i;
                                }
                                break;
                            default:
                        }
                    }

                    i++;
                }

                if (retour != -1)
                {
                    table.changeSelection(retour, colSelect, false, false);
                }
            }
        }
        return retour;
    }

// --------------------------------------------------------------------------
// Suppression des lignes selectionnees de la table
// --------------------------------------------------------------------------
    public int supprimerLignes()
    {
        int nombreLignesSupprimees = 0;

        if (table.getRowSelectionAllowed())
        {
            int[] lignesSelectionnees = table.getSelectedRows();

            if (lignesSelectionnees.length > 0)
            {
                int n
                    = JOptionPane.showConfirmDialog(
                        this,
                        "Confirmez-vous la suppression des lignes sélectionnées ?",
                        "Confirmation suppression",
                        JOptionPane.YES_NO_OPTION);

                if (n == 0)
                {
                    ModeleTable modeleTable = (ModeleTable) table.getModel();
                    nombreLignesSupprimees
                        = modeleTable.supprimer(lignesSelectionnees);

                    table.repaint();
                    table.revalidate();
                }
            }
        }
        return nombreLignesSupprimees;
    }

// --------------------------------------------------------------------------
// Restauration des lignes supprimees de la table
// --------------------------------------------------------------------------
    public void restaurerLignes()
    {
        ModeleTable modeleTable = (ModeleTable) table.getModel();
        modeleTable.restaurer();
        table.repaint();
        table.revalidate();
    }

// --------------------------------------------------------------------------
// Methode pour ajouter un listener a la liste des ecouteurs de PanneauTable.
// Remarque : listenerList est une propriete protected de JComponent. Elle
// est de type EventListenerList. Sur un objet qui n'herite pas de cette
// propriete, on peut creer une propriete EventListenerList qu'on instancie
// dans le constructeur...
// --------------------------------------------------------------------------
    public void addPanneauTableListener(PanneauTableListener p)
    {
        listenerList.add(PanneauTableListener.class, p);
    }

    public void removePanneauTableListener(PanneauTableListener p)
    {
        listenerList.remove(PanneauTableListener.class, p);
    }

// --------------------------------------------------------------------------
// Lancement de l'evenement
// --------------------------------------------------------------------------
    public void firePanneauTableSelected(PanneauTableEvent e)
    {
        EventListener[] listeners
            = listenerList.getListeners(PanneauTableListener.class);

        for (int i = 0; i < listeners.length; i++)
        {
            PanneauTableListener listener = (PanneauTableListener) listeners[i];
            listener.panneauTableSelected(e);
        }
    }
}
