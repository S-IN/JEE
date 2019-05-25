// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe EditeurDouble
// --------------------------------------------------------------------------
// L'utilisation de cet editeur est obligatoire, car l'editeur par defaut ne
// fonctionne pas. Il faudra utiliser le meme format que celui du RenduDouble
// pas obligatoire mais c'est plus coherent...
// ==========================================================================
package utilitairesMG.graphique.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import utilitairesMG.divers.*;
import utilitairesMG.graphique.texte.*;

public class EditeurDouble extends Editeur
{
    private JTextField zoneTexte;
    private int ndec = 2;
    private int zero = 1;
    private char separ = '\0';

// --------------------------------------------------------------------------
// Constructeurs
// --------------------------------------------------------------------------
// Il est au moins necessaire de definir un constructeur, car la classe
// DefaultCellEditor n'a que trois constructeurs avec parametre : JTextField,
// JCheckBox, JComboBox. A priori, on peut utiliser n'importe lequel. Dans le
// cas present, on prendra le JTextField qui correspond au type de donnees
// traite (il s'agit d'une Date sous forme de String).
// --------------------------------------------------------------------------
    public EditeurDouble()
    {
        super(new JTextField());
    }

    public EditeurDouble(int ndec)
    {
        super(new JTextField());
        this.ndec = ndec;
    }

    public EditeurDouble(int ndec, int zero)
    {
        super(new JTextField());
        this.ndec = ndec;
        this.zero = zero;
    }

    public EditeurDouble(int ndec, int zero, char separ)
    {
        super(new JTextField());
        this.ndec = ndec;
        this.zero = zero;
        this.separ = separ;
    }

// --------------------------------------------------------------------------
// Redefinition de la methode de DefaultCellEditor qui permet de retourner
// le composant qui sert a l'edition. Ici, il s'agit d'un JTextField.
// --------------------------------------------------------------------------
// Le double initial (value) contenue dans ce JTextField est formate (si
// possible comme celle du RenduDouble utilise pour l'affichage de la cellule.
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

        String affichage = null;

        if (value != null)
        {
            affichage = Conversion.doubleChaine((Double) value,
                ndec,
                zero,
                separ);
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
// Retourne la valeur contenue dans l'editeur (ici un Double).
// L'objet Double est reconstitue a partir de la chaine contenue dans le
// JTextField.
// --------------------------------------------------------------------------
// Cette methode est appelee APRES l'execution de stopCellEditing(). Tant que
// stopCellEditing() ne retourne pas true, elle ne s'execute pas...
// --------------------------------------------------------------------------
    public Object getCellEditorValue()
    {
        Double retour;
        String texteCellule;

        texteCellule = zoneTexte.getText();
        texteCellule = texteCellule.trim();

// --------------------------------------------------------------------------
// Mise a null ou conversion en Double du texte de la cellule
// --------------------------------------------------------------------------
        if (texteCellule.length() == 0)
        {
            retour = null;
        }
        else
        {
            retour = Conversion.chaineDouble(texteCellule);
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
                Conversion.chaineDouble(texteCellule);
                retour = super.stopCellEditing();
            }
            catch (NumberFormatException e)
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
