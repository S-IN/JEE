// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe EditeurObject
// ==========================================================================
package utilitairesMG.graphique.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import utilitairesMG.graphique.texte.*;

public class EditeurObject extends Editeur
{
    private JTextField zoneTexte;

// --------------------------------------------------------------------------
// Constructeurs
// --------------------------------------------------------------------------
// Il est au moins necessaire de definir un constructeur, car la classe
// DefaultCellEditor n'a que trois constructeurs avec parametre : JTextField,
// JCheckBox, JComboBox. A priori, on peut utiliser n'importe lequel. Dans le
// cas present, on prendra le JTextField qui correspond au type de donnees
// traite (il s'agit d'un Object sous forme toString()).
// --------------------------------------------------------------------------
// Le Constructeur par defaut ne sert a rien. Il suffit de laisser faire le
// DefaultCellEditor, en ne faisant pas appel a setCellEditor().
// --------------------------------------------------------------------------
    public EditeurObject()
    {
        super(new JTextField());
    }

// --------------------------------------------------------------------------
// Redefinition de la methode de DefaultCellEditor qui permet de retourner
// le composant qui sert a l'edition. Ici, il s'agit d'un JTextField dont le
// nombre de caracteres est limite.
// --------------------------------------------------------------------------
    public Component getTableCellEditorComponent(JTable table,
        Object value,
        boolean isSelected,
        int row,
        int column)
    {
        Border bords;

        zoneTexte = new JTextField((String) value);
        zoneTexte.setOpaque(true);
        bords = BorderFactory.createLineBorder(table.getForeground());
        zoneTexte.setBorder(bords);

        if (nombreCaracteres > 0)
        {
            zoneTexte.setDocument(
                new DocumentSaisieLimitee(nombreCaracteres, (String) value));
        }

        return zoneTexte;
    }

// --------------------------------------------------------------------------
// Retourne la valeur contenue dans l'editeur (ici le JTextField)
// Cette methode est appellee a la FIN de l'edition. Elle est visiblement
// utilisee immediatement par setValueAt du TableModel...
// --------------------------------------------------------------------------
    public Object getCellEditorValue()
    {
        String texteCellule;
        String retour;

        texteCellule = zoneTexte.getText();
        texteCellule = texteCellule.trim();

        if (texteCellule.length() == 0)
        {
            retour = null;
        }
        else
        {
            retour = texteCellule;
        }

        return retour;
    }
}
