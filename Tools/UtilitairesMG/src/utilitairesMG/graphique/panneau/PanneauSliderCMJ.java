// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauSliderCMJ
// --------------------------------------------------------------------------
// Panneau compose de trois PanneauSlider representant les couleurs primaires
// de peinture (Cyan, Magenta, Jaune).
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.awt.*;
import utilitairesMG.divers.*;
import utilitairesMG.graphique.evenement.*;

public class PanneauSliderCMJ extends PanneauCouleur
    implements PanneauSliderListener
{
    private PanneauSlider panneauSliderCyan;
    private PanneauSlider panneauSliderMagenta;
    private PanneauSlider panneauSliderJaune;

    private Color resultante;

// --------------------------------------------------------------------------
// Constructeur :
// --------------------------------------------------------------------------
    public PanneauSliderCMJ()
    {

        setLayout(new GridLayout(3, 1));

        panneauSliderCyan = new PanneauSlider("Cyan :",
            new Color(0, 200, 200),
            0, 0, 100, 1, 20, 10);
        panneauSliderCyan.setBackground(Color.white);
        panneauSliderCyan.addPanneauSliderListener(this);

        panneauSliderMagenta = new PanneauSlider("Magenta :",
            new Color(200, 0, 200),
            100, 0, 100, 1, 20, 10);
        panneauSliderMagenta.setBackground(Color.white);
        panneauSliderMagenta.addPanneauSliderListener(this);

        panneauSliderJaune = new PanneauSlider("Jaune :",
            new Color(180, 180, 0),
            100, 0, 100, 1, 20, 10);
        panneauSliderJaune.setBackground(Color.white);
        panneauSliderJaune.addPanneauSliderListener(this);

        add(panneauSliderCyan);
        add(panneauSliderMagenta);
        add(panneauSliderJaune);
    }

// --------------------------------------------------------------------------
// Modification de l'un des trois PanneauSlider
// --------------------------------------------------------------------------
    public void panneauSliderSelected(PanneauSliderEvent e)
    {
        int tabRVB[] = new int[3];
        int tabCMJ[] = new int[3];

// --------------------------------------------------------------------------
// Recuperer les 3 valeurs contenues dans les "PanneauSlider"
// --------------------------------------------------------------------------
        tabCMJ[0] = panneauSliderCyan.getValeur();
        tabCMJ[1] = panneauSliderMagenta.getValeur();
        tabCMJ[2] = panneauSliderJaune.getValeur();

// --------------------------------------------------------------------------
// Conversion en RBV, calcul de la couleur resultante et emission d'un
// evenement CouleurEvent a destination du composant qui ecoute ce panneau.
// --------------------------------------------------------------------------
        ConversionCouleur.CMJtoRVB(tabCMJ, tabRVB);
        resultante = new Color(tabRVB[0], tabRVB[1], tabRVB[2]);
        fireCouleurSelected(new CouleurEvent(this, resultante));
    }

// --------------------------------------------------------------------------
// Positionnement des 3 PanneauSlider en fonction de la couleur transmise.
// --------------------------------------------------------------------------
    public void setCouleur(Color couleur)
    {
        resultante = couleur;

        int tabRVB[] = new int[3];
        int tabCMJ[] = new int[3];

        tabRVB[0] = couleur.getRed();
        tabRVB[1] = couleur.getGreen();
        tabRVB[2] = couleur.getBlue();

        ConversionCouleur.RVBtoCMJ(tabRVB, tabCMJ);

        panneauSliderCyan.setValeur(tabCMJ[0]);
        panneauSliderMagenta.setValeur(tabCMJ[1]);
        panneauSliderJaune.setValeur(tabCMJ[2]);
    }

// --------------------------------------------------------------------------
// Retourne la couleur "resultante" des 3 valeurs des PanneauSlider.
// --------------------------------------------------------------------------
    public Color getCouleur()
    {
        return resultante;
    }
}
