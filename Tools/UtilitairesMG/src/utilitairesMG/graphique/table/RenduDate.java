// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe RenduDate
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;
import java.util.*;
import utilitairesMG.divers.*;

public class RenduDate extends AbstractRenduCellule
{
    private JLabel texte;
    private String formatDate;
    private DateFr dateFr;

// --------------------------------------------------------------------------
// Constructeurs : par defaut, c'est dd/MM/yy
// --------------------------------------------------------------------------
    public RenduDate()
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);

        formatDate = "dd/MM/yyyy";
    }

    public RenduDate(String formatDate)
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);

        this.formatDate = formatDate;
    }

// --------------------------------------------------------------------------
// Redefinition des methodes abstraites
// --------------------------------------------------------------------------
    public JComponent getComposant()
    {
        texte = new JLabel();
        texte.setOpaque(true);
        texte.setHorizontalAlignment(SwingConstants.RIGHT);

        return texte;
    }

    public void initComposant(Object value)
    {
        if (value != null)
        {
            dateFr = new DateFr();
            dateFr.setTime((Date) value);
            dateFr.setFormat(formatDate);
            texte.setText(dateFr.toString());
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
            dateFr = new DateFr();
            dateFr.setTime((Date) value);
            dateFr.setFormat(formatDate);
            retour = dateFr.toString();
        }
        return retour;
    }
}
