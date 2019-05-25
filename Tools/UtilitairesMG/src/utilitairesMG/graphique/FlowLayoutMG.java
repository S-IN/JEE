// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// Classe FlowLayoutMG
// --------------------------------------------------------------------------
// Cette classe permet de creer un gestionnaire de mise en page de type
// FlowLayout. Ce gestionnaire est capable de redimensionner le panneau
// en fonction de la disposition des composants calculee par la methode
// layoutContainer(). Pour cela, on a surcharge cette methode...
// ==========================================================================
package utilitairesMG.graphique;

import java.awt.*;          

public class FlowLayoutMG extends FlowLayout
{

// --------------------------------------------------------------------------
// Constructeurs (correspondant a ceux de FlowLayout).
// --------------------------------------------------------------------------
    public FlowLayoutMG()
    {
    }

    public FlowLayoutMG(int align)
    {
        super(align);
    }

    public FlowLayoutMG(int align, int hgap, int vgap)
    {
        super(align, hgap, vgap);
    }

// --------------------------------------------------------------------------
// Surcharge de la methode layoutContainer de FlowLayout.
// --------------------------------------------------------------------------
// Cette methode imposee par l'inferface LayoutManager met en page le
// Container passe en parametre. Il s'agit en fait du panneau sur lequel
// porte le Layout (par exemple un JPanel).
// --------------------------------------------------------------------------
// Elle est appelee des que le Layout doit reorganiser le panneau (en fait,
// a chaque redimensionnement du panneau).
// --------------------------------------------------------------------------
    public void layoutContainer(Container panneau)
    {

// --------------------------------------------------------------------------
// composantsPanneau : liste des composants (boutons par exemple) ajoutes au
//                     panneau.
// largeurPanneau    : largeur suffisante pour afficher le composant le plus
//                     large
// hauteurPanneau    : hauteur suffisante pour afficher le composant le plus
//                     bas
// --------------------------------------------------------------------------
        Component[] composantsPanneau = panneau.getComponents();
        int largeurPanneau = 0;
        int hauteurPanneau = 0;

        int i;
        Rectangle coordonneesComposant;
        int largeurPourComposant;
        int hauteurPourComposant;

// --------------------------------------------------------------------------
// Mise en page standard du FlowLayout
// --------------------------------------------------------------------------
        super.layoutContainer(panneau);

// --------------------------------------------------------------------------
// Boucle sur les composants
// --------------------------------------------------------------------------
        for (i = 0; i < composantsPanneau.length; i++)
        {
            coordonneesComposant = composantsPanneau[i].getBounds();

            largeurPourComposant = coordonneesComposant.width
                + (2 * getVgap());

            if (largeurPourComposant > largeurPanneau)
            {
                largeurPanneau = largeurPourComposant;
            }

            hauteurPourComposant = coordonneesComposant.y
                + coordonneesComposant.height
                + getHgap();

            if (hauteurPourComposant > hauteurPanneau)
            {
                hauteurPanneau = hauteurPourComposant;
            }
        }

// --------------------------------------------------------------------------
// Changement de la taille preferee du panneau
// --------------------------------------------------------------------------
        panneau.setPreferredSize(new Dimension(largeurPanneau, hauteurPanneau));
    }

// --------------------------------------------------------------------------
// unifieTailleComposants : met tous les composants ajoutes au panneau (add)
// a la meme taille preferee.
// --------------------------------------------------------------------------
    public static void unifieTailleComposants(Container panneau)
    {

// --------------------------------------------------------------------------
// composantsPanneau   : liste des composants (boutons par exemple) ajoutes au
//                       panneau.
// largeurMaxComposant : largeur maximale d'un composant
// hauteurMaxComposant : hauteur maximale d'un composant
// --------------------------------------------------------------------------
        Component[] composantsPanneau = panneau.getComponents();
        int largeurMaxComposant = 0;
        int hauteurMaxComposant = 0;
        Dimension tailleComposant;
        int i;

// --------------------------------------------------------------------------
// Boucle sur les composants
// --------------------------------------------------------------------------
        for (i = 0; i < composantsPanneau.length; i++)
        {
            tailleComposant = composantsPanneau[i].getPreferredSize();

            if (tailleComposant.width > largeurMaxComposant)
            {
                largeurMaxComposant = tailleComposant.width;
            }

            if (tailleComposant.height > hauteurMaxComposant)
            {
                hauteurMaxComposant = tailleComposant.height;
            }
        }

        tailleComposant = new Dimension(largeurMaxComposant, hauteurMaxComposant);
        for (i = 0; i < composantsPanneau.length; i++)
        {
            composantsPanneau[i].setPreferredSize(tailleComposant);
        }
    }
}
