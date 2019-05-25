// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe RenduBoolean
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.*;

public class RenduBoolean extends AbstractRenduCellule
{
    private JCheckBox boite;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public RenduBoolean()
    {
        boite = new JCheckBox();
        boite.setOpaque(true);
        boite.setBorderPainted(true);
        boite.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public JComponent getComposant()
    {
        return boite;
    }

    public void initComposant(Object value)
    {
        if (value != null)
        {
            boite.setSelected((Boolean) value);
        }
        else
        {
            boite.setSelected(false);
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
            if ((Boolean) value)
            {
                retour = "true";
            }
            else
            {
                retour = "false";
            }
        }
        return retour;
    }
}
