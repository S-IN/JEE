// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe RenduDouble
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;
import utilitairesMG.divers.*;

public class RenduDouble extends AbstractRenduCellule
{
    private JLabel texte;
    private int ndec = 2;
    private int zero = 1;
    private char separ = '\0';

// --------------------------------------------------------------------------
// Constructeurs : par defaut, il n'y a pas de separateur de milliers
// --------------------------------------------------------------------------
    public RenduDouble()
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    public RenduDouble(int ndec)
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);

        this.ndec = ndec;
    }

    public RenduDouble(int ndec, int zero)
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);

        this.ndec = ndec;
        this.zero = zero;
    }

    public RenduDouble(int ndec, int zero, char separ)
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);

        this.ndec = ndec;
        this.zero = zero;
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
            String affichage = Conversion.doubleChaine((Double) value,
                ndec,
                zero,
                separ);
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
            retour = Conversion.doubleChaine((Double) value,
                ndec,
                zero,
                separ);
        }
        return retour;
    }
}
