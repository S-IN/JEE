// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe EditeurBigDecimal
// --------------------------------------------------------------------------
// L'utilisation de cet editeur est obligatoire, car l'editeur par defaut ne
// fonctionne pas. Il faudra utiliser le meme format que celui du
// RenduBigDecimal. Ce n'est pas obligatoire mais c'est plus coherent...
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;

public class Editeur extends DefaultCellEditor
{
    protected int nombreCaracteres;

// --------------------------------------------------------------------------
// Constructeurs
// --------------------------------------------------------------------------
// Il est au moins necessaire de definir un constructeur, car la classe
// DefaultCellEditor n'a que trois constructeurs avec parametre : JTextField,
// JCheckBox, JComboBox. A priori, on peut utiliser n'importe lequel. Dans le
// cas present, on prendra le JTextField qui correspond au type de donnees
// traite (il s'agit d'une Date sous forme de String).
// --------------------------------------------------------------------------
    public Editeur(JTextField zoneTexte)
    {
        super(zoneTexte);
    }
}
