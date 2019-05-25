// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauSliderCMJN
// --------------------------------------------------------------------------
// Panneau compose de quatre PanneauSlider representant les couleurs
// primaires de peinture (Cyan, Magenta, Jaune) et la couleur Noire utilisee
// en imprimerie (quadrichromie).
// ==========================================================================
package utilitairesMG.graphique.panneau;

import java.awt.*;
import utilitairesMG.divers.*;
import utilitairesMG.graphique.evenement.*;

public class PanneauSliderCMJN extends PanneauCouleur
    implements PanneauSliderListener
{
    private PanneauSlider panneauSliderCyan;
    private PanneauSlider panneauSliderMagenta;
    private PanneauSlider panneauSliderJaune;
    private PanneauSlider panneauSliderNoir;

    private Color resultante;

// --------------------------------------------------------------------------
// Constructeur :
// --------------------------------------------------------------------------
    public PanneauSliderCMJN()
    {

        setLayout(new GridLayout(4, 1));

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

        panneauSliderNoir = new PanneauSlider("Noir :",
            new Color(0, 0, 0),
            0, 0, 100, 1, 20, 10);
        panneauSliderNoir.setBackground(Color.white);
        panneauSliderNoir.addPanneauSliderListener(this);

        add(panneauSliderCyan);
        add(panneauSliderMagenta);
        add(panneauSliderJaune);
        add(panneauSliderNoir);
    }

// --------------------------------------------------------------------------
// Modification de l'un des quatre PanneauSlider
// --------------------------------------------------------------------------
    public void panneauSliderSelected(PanneauSliderEvent e)
    {
        int cyan, magenta, jaune, noir;
        int[] tabCMJN = new int[4];
        int[] tabRVB = new int[3];

// --------------------------------------------------------------------------
// Recuperer les 4 valeurs contenues dans les "PanneauSlider"
// --------------------------------------------------------------------------
        cyan = panneauSliderCyan.getValeur();
        magenta = panneauSliderMagenta.getValeur();
        jaune = panneauSliderJaune.getValeur();
        noir = panneauSliderNoir.getValeur();

        if (cyan + noir > 100)
        {
            cyan = 100 - noir;
            panneauSliderCyan.setValeur(cyan);
        }

        if (magenta + noir > 100)
        {
            magenta = 100 - noir;
            panneauSliderMagenta.setValeur(magenta);
        }

        if (jaune + noir > 100)
        {
            jaune = 100 - noir;
            panneauSliderJaune.setValeur(jaune);
        }

        panneauSliderCyan.setValeurBlocage(100 - noir);
        panneauSliderMagenta.setValeurBlocage(100 - noir);
        panneauSliderJaune.setValeurBlocage(100 - noir);

// --------------------------------------------------------------------------
// Conversion en RBV, calcul de la couleur resultante et emission d'un
// evenement CouleurEvent a destination du composant qui ecoute ce panneau.
// --------------------------------------------------------------------------
        tabCMJN[0] = cyan;
        tabCMJN[1] = magenta;
        tabCMJN[2] = jaune;
        tabCMJN[3] = noir;
        ConversionCouleur.CMJNtoRVB(tabCMJN, tabRVB);
        resultante = new Color(tabRVB[0], tabRVB[1], tabRVB[2]);
        fireCouleurSelected(new CouleurEvent(this, resultante));
    }

// --------------------------------------------------------------------------
// Positionnement des 4 PanneauSlider en fonction de la couleur transmise.
// --------------------------------------------------------------------------
    public void setCouleur(Color couleur)
    {
        resultante = couleur;

        int tabRVB[] = new int[3];
        int tabCMJN[] = new int[4];

        tabRVB[0] = couleur.getRed();
        tabRVB[1] = couleur.getGreen();
        tabRVB[2] = couleur.getBlue();

        ConversionCouleur.RVBtoCMJN(tabRVB, tabCMJN);

        panneauSliderCyan.setValeur(tabCMJN[0]);
        panneauSliderMagenta.setValeur(tabCMJN[1]);
        panneauSliderJaune.setValeur(tabCMJN[2]);
        panneauSliderNoir.setValeur(tabCMJN[3]);
    }

// --------------------------------------------------------------------------
// Retourne la couleur "resultante" des 4 valeurs des PanneauSlider.
// --------------------------------------------------------------------------
    public Color getCouleur()
    {
        return resultante;
    }
}
