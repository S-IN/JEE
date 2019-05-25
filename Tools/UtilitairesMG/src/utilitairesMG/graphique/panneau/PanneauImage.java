// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauImage
// --------------------------------------------------------------------------
// Panneau contenant une image
// --------------------------------------------------------------------------
// Ce panneau pourra être affiché seul dans une fenetre a la dimension
// reelle de l'image. C'est l'interet du setPreferredSize().
// --------------------------------------------------------------------------
// On pourra aussi l'afficher de façon réduite, dans une cellule de JTable
// par exemple.
// ==========================================================================
package utilitairesMG.graphique.panneau;

import javax.swing.*;       // Pour JFrame, JPanel
import java.awt.*;          // Pour Graphics, Color...

public class PanneauImage extends JPanel
{

// --------------------------------------------------------------------------
// Proprietes
// --------------------------------------------------------------------------
    private Image img;

// --------------------------------------------------------------------------
// Constructeurs
// --------------------------------------------------------------------------
    public PanneauImage()
    {
    }

    public PanneauImage(String imageFond)
    {
        setImage(imageFond);
    }

// --------------------------------------------------------------------------
// Changement de l'image du panneau
// --------------------------------------------------------------------------
// Si la chaine imageFond ne correspond a aucun fichier image correct, en
// particulier si elle vaut null, la methode kit.getImage() retourne quand
// meme une reference d'objet Image.
// --------------------------------------------------------------------------
// Quand la chaine vaut null, une erreur se produit lors du waitForID.
// --------------------------------------------------------------------------
// Quand la chaine ne correspond pas a un nom de fichier image correct, les
// methodes img.getWidth() et img.getHeigth() retournent -1 !
// --------------------------------------------------------------------------
    public void setImage(String imageFond)
    {

// --------------------------------------------------------------------------
// La chaine imageFond vaut null
// --------------------------------------------------------------------------
        if (imageFond == null)
        {
            img = null;
        }
        else
        {
            Toolkit kit;
            MediaTracker pisteur;

// --------------------------------------------------------------------------
// Chargement de l'image
// --------------------------------------------------------------------------
// On temporise pour attendre son chargement. A defaut, le panneau risque
// d'apparaitre vide, et ne se remplira que lorsqu'il sera redessiné...
// --------------------------------------------------------------------------
            kit = Toolkit.getDefaultToolkit();
            img = kit.getImage(imageFond);

            pisteur = new MediaTracker(this);
            pisteur.addImage(img, 1);

            try
            {
                pisteur.waitForID(1);
            }
            catch (InterruptedException e)
            {
            }

// --------------------------------------------------------------------------
// Dimension de l'image
// --------------------------------------------------------------------------
// Le this des methodes getWidth et getHeight correspond au panneau courant.
// En effet, JPanel implemente ImageObserver. Un panneau est un observateur
// d'images...
// --------------------------------------------------------------------------
            int largeurImg = img.getWidth(this);
            int hauteurImg = img.getHeight(this);

            if (largeurImg == -1)
            {
                img = null;
            }
            else
            {
                setPreferredSize(new Dimension(largeurImg, hauteurImg));
            }
        }
    }

// --------------------------------------------------------------------------
// Methode d'affichage du panneau
// --------------------------------------------------------------------------
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

// --------------------------------------------------------------------------
// Y-a-t'il une image a afficher ?
// --------------------------------------------------------------------------
        if (img != null)
        {

// --------------------------------------------------------------------------
// Taille reelle du panneau au moment du paintComponent
// --------------------------------------------------------------------------
            Dimension taillePanneau = getSize();
            int wPanneau = taillePanneau.width;
            int hPanneau = taillePanneau.height;

// --------------------------------------------------------------------------
// Calcul du ratio hauteur largeur de l'image
// --------------------------------------------------------------------------
            int wImage, hImage;
            float ratioHauteurLargeurImg = 1;

            int largeurImg = img.getWidth(this);
            int hauteurImg = img.getHeight(this);

            if (largeurImg != 0)
            {
                ratioHauteurLargeurImg = (float) hauteurImg / (float) largeurImg;
            }

// --------------------------------------------------------------------------
// Calcul de la taille de l'image pour respecter le ratio hauteur largeur
// --------------------------------------------------------------------------
// On fixe la largeur de l'image au maximum (largeur du panneau).
// On calcule la hauteur de l'image.
// Si cette hauteur est trop grande (elle depasse la hauteur du panneau), on
// fixe la hauteur de l'image et on calcule la largeur.
// --------------------------------------------------------------------------
            wImage = wPanneau;
            hImage = (int) (wImage * ratioHauteurLargeurImg);

            if (hImage > hPanneau)
            {
                hImage = hPanneau;
                wImage = (int) (hImage / ratioHauteurLargeurImg);
            }

// --------------------------------------------------------------------------
// On positionne l'image au centre du panneau
// --------------------------------------------------------------------------
            int posX = (wPanneau - wImage) / 2;
            int posY = (hPanneau - hImage) / 2;

            g.drawImage(img, posX, posY, wImage, hImage, null);
        }
    }
}
