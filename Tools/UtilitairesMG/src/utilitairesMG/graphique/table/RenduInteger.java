// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe RenduInteger
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;
import utilitairesMG.divers.*;

public class RenduInteger extends AbstractRenduCellule
{
    private JLabel texte;
    private char separ = '\0';

// --------------------------------------------------------------------------
// Constructeurs : par defaut, il n'y a pas de separateur de milliers
// --------------------------------------------------------------------------
    public RenduInteger()
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    public RenduInteger(char separ)
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);

        this.separ = separ;
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
            String affichage = Conversion.longChaine((Integer) value, separ);
            texte.setText(affichage);
        }
        else
        {
            texte.setText(null);
        }
    }

// --------------------------------------------------------------------------
// Conversion d'un objet en String selon le format de la cellule
// --------------------------------------------------------------------------
    public String conversionObjectString(Object value)
    {
        String retour = null;

        if (value != null)
        {
            retour = Conversion.longChaine((Integer) value, separ);
        }
        return retour;
    }
}
