// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe RenduObject
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;

public class RenduObject extends AbstractRenduCellule
{
    private JLabel texte;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public RenduObject()
    {
        texte = new JLabel();
        texte.setOpaque(true);
    }

// --------------------------------------------------------------------------
// Redefinition des methodes abstraites
// --------------------------------------------------------------------------
    public JComponent getComposant()
    {
        return texte;
    }

    public void initComposant(Object value)
    {
        if (value != null)
        {
            String affichage = value.toString();
            texte.setText(affichage);
        }
        else
        {
            texte.setText(null);
        }
    }

// --------------------------------------------------------------------------
// Conversion de l'objet affiche selon le format de la cellule
// --------------------------------------------------------------------------
    public String conversionObjectString(Object value)
    {
        String retour = null;

        if (value != null)
        {
            retour = value.toString();
        }
        return retour;
    }
}
