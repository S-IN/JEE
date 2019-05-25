// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe EditeurDate
// --------------------------------------------------------------------------
// L'utilisation de cet editeur est obligatoire, car l'editeur par defaut ne
// fonctionne pas. Il faudra utiliser le meme format que celui du RenduDate,
// lui aussi obligatoire...
// ==========================================================================
package utilitairesMG.graphique.table;

import java.awt.*;
import javax.swing.*;
import java.text.*;
import java.util.*;
import javax.swing.border.*;
import utilitairesMG.divers.*;
import utilitairesMG.graphique.texte.*;

public class EditeurDate extends Editeur
{
    private JTextField zoneTexte;
    private String formatDate;
    private DateFr dateFr;

// --------------------------------------------------------------------------
// Constructeurs
// --------------------------------------------------------------------------
// Il est au moins necessaire de definir un constructeur, car la classe
// DefaultCellEditor n'a que trois constructeurs avec parametre : JTextField,
// JCheckBox, JComboBox. A priori, on peut utiliser n'importe lequel. Dans le
// cas present, on prendra le JTextField qui correspond au type de donnees
// traite (il s'agit d'une Date sous forme de String).
// --------------------------------------------------------------------------
    public EditeurDate()
    {
        super(new JTextField());
        formatDate = "dd/MM/yyyy";
    }

    public EditeurDate(String formatDate)
    {
        super(new JTextField());
        this.formatDate = formatDate;
    }

// --------------------------------------------------------------------------
// Redefinition de la methode de DefaultCellEditor qui permet de retourner
// le composant qui sert a l'edition. Ici, il s'agit d'un JTextField.
// --------------------------------------------------------------------------
// La date initiale (value) contenue dans ce JTextField est formatee (si
// possible comme celle du RenduDate utilise pour l'affichage de la cellule.
// --------------------------------------------------------------------------
    public Component getTableCellEditorComponent(JTable table,
        Object value,
        boolean isSelected,
        int row,
        int column)
    {
        Border bords;

        zoneTexte = new JTextField();
        zoneTexte.setOpaque(true);
        bords = BorderFactory.createLineBorder(table.getForeground());
        zoneTexte.setBorder(bords);
        zoneTexte.setHorizontalAlignment(SwingConstants.RIGHT);

        dateFr = new DateFr();
        dateFr.setFormat(formatDate);

        String affichage = null;

        if (value != null)
        {
            dateFr.setTime((Date) value);
            affichage = dateFr.toString();
        }

        if (nombreCaracteres > 0)
        {
            zoneTexte.setDocument(
                new DocumentSaisieLimitee(nombreCaracteres, affichage));
        }
        else
        {
            zoneTexte.setText(affichage);
        }

        return zoneTexte;
    }

// --------------------------------------------------------------------------
// Retourne la valeur contenue dans l'editeur (ici une Date).
// L'objet Date est reconstitue a partir de la chaine contenue dans le
// JTextField.
// --------------------------------------------------------------------------
// Cette methode est appelee APRES l'execution de stopCellEditing(). Tant que
// stopCellEditing() ne retourne pas true, elle ne s'execute pas...
// --------------------------------------------------------------------------
    public Object getCellEditorValue()
    {
        Date retour;
        String texteCellule;

        texteCellule = zoneTexte.getText();
        texteCellule = texteCellule.trim();

// --------------------------------------------------------------------------
// Le ParseException qui peut se produire dans le set(), est en fait teste
// d'abord dans la methode stopCellEditing(), qui ne rend pas la main tant
// que cette erreur n'a pas disparu. On ne risque donc rien ici...
// --------------------------------------------------------------------------
        if (texteCellule.length() == 0)
        {
            retour = null;
        }
        else
        {
            try
            {
                dateFr.set(texteCellule);
            }
            catch (ParseException e)
            {
            }
            retour = dateFr.getTime();
        }
        return retour;
    }

// --------------------------------------------------------------------------
// Quand cette methode retourne true, l'edition s'arrete, et
// getCellEditorValue() s'execute... La surcharger permet d'empecher la fin
// de l'edition tant que la saisie n'est pas correcte
// --------------------------------------------------------------------------
    public boolean stopCellEditing()
    {
        boolean retour;
        String texteCellule;

        texteCellule = zoneTexte.getText();
        texteCellule = texteCellule.trim();

        if (texteCellule.length() == 0)
        {
            retour = super.stopCellEditing();
        }
        else
        {
            try
            {
                dateFr.set(texteCellule);
                retour = super.stopCellEditing();
            }
            catch (ParseException e)
            {
                retour = false;
                Border bords;

                bords = BorderFactory.createLineBorder(Color.red);
                zoneTexte.setBorder(bords);
            }
        }
        return retour;
    }
}
