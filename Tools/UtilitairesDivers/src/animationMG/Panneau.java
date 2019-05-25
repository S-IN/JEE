// ==========================================================================
// package animationMG      (interfaces, classes abstraites pour l'animation)
// --------------------------------------------------------------------------
// Classe Panneau
// --------------------------------------------------------------------------
// Le panneau implemente Runnable, c'est a dire qu'il possede une methode
// run() qui pourra etre lancee par un nouveau Thread... C'est ce qui est
// fait ci-dessus par les Thread animation.
// ==========================================================================

package animationMG;

import javax.swing.*;       // Pour JFrame, JPanel
import java.awt.image.*;    // Pour BufferedImage
import java.awt.*;          // Pour Graphics, Color...
import java.awt.geom.*;     // Pour Arc2D
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Panneau extends JPanel implements Runnable
{
    private BufferedImage img;
    private Animable dessin;
    private int angle = 0;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public Panneau(Animable dessin, String imageFond)
    {
        try
        {
            img = ImageIO.read(new File(imageFond));
        }
        catch(IOException e)
        {
            
        }

// --------------------------------------------------------------------------
// On calcule la dimension du panneau a partir de la dimention de l'image
// Le this des methodes getWidth et getHeight correspond au panneau courant.
// En effet, JPanel implemente ImageObserver. Un panneau est un observateur
// d'images...
// --------------------------------------------------------------------------
        setPreferredSize(
            new Dimension(img.getWidth(this), img.getHeight(this)));

        this.dessin = dessin;
    }

// --------------------------------------------------------------------------
// Methode d'affichage du panneau
// --------------------------------------------------------------------------
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

// --------------------------------------------------------------------------
// Taille du panneau et affichage de l'image plein panneau...
// --------------------------------------------------------------------------
        Dimension taille = getSize();
        g.drawImage(img, 0, 0, taille.width, taille.height, null);

// --------------------------------------------------------------------------
// Creation d'une nouvelle image et de son contexte graphique.
// --------------------------------------------------------------------------
        BufferedImage img2 =
            new BufferedImage(
                taille.width, taille.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = img2.createGraphics();

// --------------------------------------------------------------------------
// Couleur de fond
// --------------------------------------------------------------------------
        //g2.setBackground(new Color(255, 0, 255, 100));
        //g2.clearRect(0, 0, taille.width, taille.height);

// --------------------------------------------------------------------------
// Option pour eviter le crenelage
// --------------------------------------------------------------------------
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

// --------------------------------------------------------------------------
// Couleur d'ecriture dans l'image img2 :
// --------------------------------------------------------------------------
        int rouge = dessin.getCouleur().getRed();
        int vert = dessin.getCouleur().getGreen();
        int bleu = dessin.getCouleur().getBlue();
        int transparence = dessin.getTransparence();
        g2.setColor(new Color(rouge, vert, bleu, transparence));

// --------------------------------------------------------------------------
// Creation d'un camembert :
// --------------------------------------------------------------------------
        AffineTransform at = 
            AffineTransform.getTranslateInstance(
                taille.width / 2, taille.height / 2);
        at.rotate(Math.toRadians(angle));
        g2.fill(at.createTransformedShape(dessin.getDessin()));

// --------------------------------------------------------------------------
// Liberation du contexte graphique g2 qui a ete alloue dynamiquement.
// --------------------------------------------------------------------------
        g2.dispose();

// --------------------------------------------------------------------------
// Affichage de l'image img2 dans le panneau :
// --------------------------------------------------------------------------
        g.drawImage(img2, 0, 0, null);
    }

// --------------------------------------------------------------------------
// Methode run, lancee par le Thread animation de la fenetre
// --------------------------------------------------------------------------
    public void run()
    {
        while (true)
        {
            try
            {
                Thread.sleep(50);
                angle += 10;
            }
            catch (InterruptedException e)
            {
                System.out.println("Fumier !");
            }
            repaint();
        }
    }
}
