// ==========================================================================
// Classe ModeleTable                                     TestsCompletsTables
// --------------------------------------------------------------------------
// Modele de table constitue a partir d'un vecteur de lignes et d'un vecteur
// de colonnes
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.table.*;
import java.util.*;
import utilitairesMG.divers.*;

public class ModeleTable extends AbstractTableModel
{
    private Vector<Vector<Object>> listeLignes;
    private Vector<Colonne> listeColonnes;
    private Vector<Character> marqueursLignes;

// --------------------------------------------------------------------------
// Lignes supprimees
// --------------------------------------------------------------------------
    private Vector<Vector<Object>> lignesSupprimees;
    private Vector<Character> marqueursLignesSupprimees;

// --------------------------------------------------------------------------
// Constructeurs (initialisation de la liste des lignes supprimmees)
// --------------------------------------------------------------------------
    public ModeleTable()
    {
        lignesSupprimees = new Vector<Vector<Object>>();
        marqueursLignesSupprimees = new Vector<Character>();
    }

    public ModeleTable(Vector<Vector<Object>> listeLignes,
        Vector<Colonne> listeColonnes)
    {
        this.listeLignes = listeLignes;
        this.listeColonnes = listeColonnes;

        marqueursLignes = new Vector<Character>();
        for (int i = 0; i < listeLignes.size(); i++)
        {
            marqueursLignes.addElement(' ');
        }

        lignesSupprimees = new Vector<Vector<Object>>();
        marqueursLignesSupprimees = new Vector<Character>();
    }

// --------------------------------------------------------------------------
// Initialisation de la liste des lignes
// --------------------------------------------------------------------------
    public void setListeLignes(Vector<Vector<Object>> listeLignes)
    {
        this.listeLignes = listeLignes;

        marqueursLignes = new Vector<Character>();
        for (int i = 0; i < listeLignes.size(); i++)
        {
            marqueursLignes.addElement(' ');
        }
    }

// --------------------------------------------------------------------------
// Initialisation de la liste des colonnes
// --------------------------------------------------------------------------
    public void setListeColonnes(Vector<Colonne> listeColonnes)
    {
        this.listeColonnes = listeColonnes;
    }

// --------------------------------------------------------------------------
// Getters pour les differentes listes
// --------------------------------------------------------------------------
    public Vector<Vector<Object>> getListeLignes()
    {
        return listeLignes;
    }

    public Vector<Vector<Object>> getListeLignesSupprimees()
    {
        return lignesSupprimees;
    }

    public Vector<Colonne> getListeColonnes()
    {
        return listeColonnes;
    }

    public Vector<Character> getMarqueursLignes()
    {
        return marqueursLignes;
    }

// --------------------------------------------------------------------------
// getRowCount : nombre de lignes de la JTable
// --------------------------------------------------------------------------
    public int getRowCount()
    {
        return listeLignes.size();
    }

// --------------------------------------------------------------------------
// getColumnCount : nombre de colonnes de la JTable
// --------------------------------------------------------------------------
    public int getColumnCount()
    {
        return listeColonnes.size();
    }

// --------------------------------------------------------------------------
// getValueAt : "Object" qui s'affiche en ligne l et en colonne c
// --------------------------------------------------------------------------
    public Object getValueAt(int l, int c)
    {
        Object valeur;
        Vector<Object> ligne;

        if (l < listeLignes.size())
        {
            ligne = listeLignes.elementAt(l);
            valeur = ligne.elementAt(c);
        }
        else
        {
            valeur = null;
        }

        return valeur;
    }

// --------------------------------------------------------------------------
// Recupere la valeur tapee et la met dans le vecteur
// --------------------------------------------------------------------------
    public void setValueAt(Object valeur, int l, int c)
    {
        Vector<Object> ligne;

        if (l < listeLignes.size())
        {
            ligne = listeLignes.elementAt(l);
            ligne.setElementAt(valeur, c);

            if (marqueursLignes.elementAt(l) == ' ')
            {
                marqueursLignes.setElementAt('M', l);
            }
        }
        else if (valeur != null)
        {
            ligne = new Vector<Object>();
            for (int iCol = 0; iCol < listeColonnes.size(); iCol++)
            {
                ligne.addElement(null);
            }
            listeLignes.addElement(ligne);
            ligne.setElementAt(valeur, c);

            marqueursLignes.addElement('I');
        }
    }

// --------------------------------------------------------------------------
// getColumnClass : retourne la classe des objets de la colonne c.
// --------------------------------------------------------------------------
// Ce renseignement permet a la JTable d'adapter l'affichage au type de
// donnees de la colonne. Ainsi, les booleens apparaissent en case a cocher,
// les valeurs numeriques sont cadrees a droite...
// --------------------------------------------------------------------------
// Cette methode servira pour toutes les colonnes dont l'editeur par defaut
// n'a pas ete remplace (voir ModeleColonneTable.java)
// --------------------------------------------------------------------------
    public Class getColumnClass(int c)
    {
        Class classe = null;

        try
        {
            classe
                = Class.forName(listeColonnes.elementAt(c).getType());
        }
        catch (ClassNotFoundException e)
        {
        }

        return classe;
    }

// --------------------------------------------------------------------------
// Tri de la table. La liste des objets est modifiee.
// --------------------------------------------------------------------------
    public void trier(int[] colonnesSel, boolean triAscendant)
    {
        for (int i = colonnesSel.length - 1; i >= 0; i--)
        {
            Collections.sort(listeLignes,
                new LignesComparator(colonnesSel[i],
                    triAscendant));
        }
    }

// --------------------------------------------------------------------------
// Suppression de lignes de la table.
// --------------------------------------------------------------------------
    public int supprimer(int[] lignesSelectionnees)
    {
        int nombreLignesSupprimees = 0;

        for (int i = lignesSelectionnees.length - 1; i >= 0; i--)
        {
            if (lignesSelectionnees[i] < getRowCount() - 1)
            {
                nombreLignesSupprimees++;
                lignesSupprimees.addElement(
                    listeLignes.elementAt(lignesSelectionnees[i]));
                listeLignes.remove(lignesSelectionnees[i]);

                marqueursLignesSupprimees.addElement(
                    marqueursLignes.elementAt(lignesSelectionnees[i]));
                marqueursLignes.remove(lignesSelectionnees[i]);
            }
        }
        return nombreLignesSupprimees;
    }

// --------------------------------------------------------------------------
// Restauration des lignes supprimees.
// --------------------------------------------------------------------------
    public void restaurer()
    {
        for (int i = lignesSupprimees.size() - 1; i >= 0; i--)
        {
            listeLignes.addElement(lignesSupprimees.elementAt(i));
            marqueursLignes.addElement(marqueursLignesSupprimees.elementAt(i));
        }

        lignesSupprimees.clear();
        marqueursLignesSupprimees.clear();
    }
}
