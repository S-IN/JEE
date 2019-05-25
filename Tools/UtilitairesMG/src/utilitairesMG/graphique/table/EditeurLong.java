// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe EditeurLong
// --------------------------------------------------------------------------
// L'utilisation de cet editeur est pratique, car l'editeur par defaut
// provoque des erreurs de saisie a cause de blancs (' ') qui ne nuisent pas.
// ==========================================================================
package utilitairesMG.graphique.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import utilitairesMG.divers.*;
import utilitairesMG.graphique.texte.*;

public class EditeurLong extends Editeur
{
    private JTextField zoneTexte;
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
    public EditeurLong()
    {
        super(new JTextField());
    }

    public EditeurLong(char separ)
    {
        super(new JTextField());
        this.separ = separ;
    }

// --------------------------------------------------------------------------
// Redefinition de la methode de DefaultCellEditor qui permet de retourner
// le composant qui sert a l'edition. Ici, il s'agit d'un JTextField.
// --------------------------------------------------------------------------
// Le Long initial (value) contenu dans ce JTextField est formate (si
// possible comme celle du RenduLong utilise pour l'affichage de la cellule.
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
            affichage = Conversion.longChaine((Long) value, separ);
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
// Retourne la valeur contenue dans l'editeur (ici un Long).
// L'objet Long est reconstitue a partir de la chaine contenue dans le
// JTextField.
// --------------------------------------------------------------------------
// Cette methode est appelee APRES l'execution de stopCellEditing(). Tant que
// stopCellEditing() ne retourne pas true, elle ne s'execute pas...
// --------------------------------------------------------------------------
    public Object getCellEditorValue()
    {
        Long retour;
        String texteCellule;

        texteCellule = zoneTexte.getText();
        texteCellule = texteCellule.trim();

// --------------------------------------------------------------------------
// Mise a null ou conversion en Long du texte de la cellule
// --------------------------------------------------------------------------
        if (texteCellule.length() == 0)
        {
            retour = null;
        }
        else
        {
            retour = Conversion.chaineLong(texteCellule);
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
                Conversion.chaineLong(texteCellule);
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
