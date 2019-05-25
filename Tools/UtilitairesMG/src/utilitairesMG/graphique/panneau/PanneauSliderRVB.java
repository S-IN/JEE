// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauSliderRVB
// --------------------------------------------------------------------------
// Panneau compose de trois PanneauSlider representant les couleurs primaires
// en lumiere (Rouge, Vert, Bleu).
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.awt.*;
import utilitairesMG.graphique.evenement.*;

public class PanneauSliderRVB extends PanneauCouleur
    implements PanneauSliderListener
{
    private PanneauSlider panneauSliderRouge;
    private PanneauSlider panneauSliderVert;
    private PanneauSlider panneauSliderBleu;

    private Color resultante;

// --------------------------------------------------------------------------
// Constructeur :
// --------------------------------------------------------------------------
    public PanneauSliderRVB()
    {
        setLayout(new GridLayout(3, 1));

        panneauSliderRouge = new PanneauSlider("Rouge :",
            new Color(200, 0, 0),
            255, 0, 255, 1, 85, 17);
        panneauSliderRouge.setBackground(Color.white);
        panneauSliderRouge.addPanneauSliderListener(this);

        panneauSliderVert = new PanneauSlider("Vert :",
            new Color(0, 150, 0),
            0, 0, 255, 1, 85, 17);
        panneauSliderVert.setBackground(Color.white);
        panneauSliderVert.addPanneauSliderListener(this);

        panneauSliderBleu = new PanneauSlider("Bleu :",
            new Color(0, 0, 200),
            0, 0, 255, 1, 85, 17);
        panneauSliderBleu.setBackground(Color.white);
        panneauSliderBleu.addPanneauSliderListener(this);

        add(panneauSliderRouge);
        add(panneauSliderVert);
        add(panneauSliderBleu);
    }

// --------------------------------------------------------------------------
// Modification de l'un des trois PanneauSlider
// --------------------------------------------------------------------------
    public void panneauSliderSelected(PanneauSliderEvent e)
    {
        resultante = new Color(panneauSliderRouge.getValeur(),
            panneauSliderVert.getValeur(),
            panneauSliderBleu.getValeur());
        fireCouleurSelected(new CouleurEvent(this, resultante));
    }

// --------------------------------------------------------------------------
// Positionnement des 3 PanneauSlider en fonction de la couleur transmise.
// --------------------------------------------------------------------------
    public void setCouleur(Color couleur)
    {
        resultante = couleur;

        panneauSliderRouge.setValeur(resultante.getRed());
        panneauSliderVert.setValeur(resultante.getGreen());
        panneauSliderBleu.setValeur(resultante.getBlue());
    }

// --------------------------------------------------------------------------
// Retourne la couleur "resultante" des 3 valeurs des PanneauSlider.
// --------------------------------------------------------------------------
    public Color getCouleur()
    {
        return resultante;
    }
}
