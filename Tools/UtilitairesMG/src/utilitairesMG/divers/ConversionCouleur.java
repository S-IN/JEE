// ==========================================================================
// package utilitairesMG.divers
// --------------------------------------------------------------------------
// Classe ConversionCouleur
// ==========================================================================
package utilitairesMG.divers;

import java.awt.*;

public class ConversionCouleur
{

// --------------------------------------------------------------------------
// Recuperation de la teinte (h) de la couleur
// --------------------------------------------------------------------------
    public static float getH(Color couleur)
    {
        float tabHSB[] = new float[3];
        Color.RGBtoHSB(couleur.getRed(),
            couleur.getGreen(),
            couleur.getBlue(),
            tabHSB);
        return tabHSB[0];
    }

// --------------------------------------------------------------------------
// Recuperation de la saturation (s) de la couleur
// --------------------------------------------------------------------------
    public static float getS(Color couleur)
    {
        float tabHSB[] = new float[3];
        Color.RGBtoHSB(couleur.getRed(),
            couleur.getGreen(),
            couleur.getBlue(),
            tabHSB);
        return tabHSB[1];
    }

// --------------------------------------------------------------------------
// Recuperation de la luminosite (b) de la couleur
// --------------------------------------------------------------------------
    public static float getB(Color couleur)
    {
        float tabHSB[] = new float[3];
        Color.RGBtoHSB(couleur.getRed(),
            couleur.getGreen(),
            couleur.getBlue(),
            tabHSB);
        return tabHSB[2];
    }

// --------------------------------------------------------------------------
// Conversion d'une couleur exprimee en CMJ en RVB
// --------------------------------------------------------------------------
// tabCMJ : tableau des valeurs Cyan, Magenta, Jaune de la couleur. Ces
//          valeurs sont comprises entre 0 et 100
// tabRVB : tableau des valeurs Rouge, Vert, Bleu de la couleur. Ces
//          valeurs sont comprises entre 0 et 255
// --------------------------------------------------------------------------
    public static void CMJtoRVB(int[] tabCMJ, int[] tabRVB)
    {
        int cyan, magenta, jaune;

        cyan = (int) Math.round(tabCMJ[0] * 2.55);
        magenta = (int) Math.round(tabCMJ[1] * 2.55);
        jaune = (int) Math.round(tabCMJ[2] * 2.55);

        tabRVB[0] = 255 - cyan;
        tabRVB[1] = 255 - magenta;
        tabRVB[2] = 255 - jaune;
    }

// --------------------------------------------------------------------------
// Conversion d'une couleur exprimee en RVB en CMJ
// --------------------------------------------------------------------------
    public static void RVBtoCMJ(int[] tabRVB, int[] tabCMJ)
    {
        int rouge, vert, bleu;

        rouge = (int) Math.round(tabRVB[0] / 2.55);
        vert = (int) Math.round(tabRVB[1] / 2.55);
        bleu = (int) Math.round(tabRVB[2] / 2.55);

        tabCMJ[0] = 100 - rouge;
        tabCMJ[1] = 100 - vert;
        tabCMJ[2] = 100 - bleu;
    }

// --------------------------------------------------------------------------
// Conversion d'une couleur exprimee en CMJN en RVB
// --------------------------------------------------------------------------
// tabCMJN : tableau des valeurs Cyan, Magenta, Jaune, Noir de la couleur.
//           Ces valeurs sont comprises entre 0.f et 1.f
// tabRVB  : tableau des valeurs Rouge, Vert, Bleu de la couleur. Ces
//           valeurs sont comprises entre 0 et 255
// --------------------------------------------------------------------------
    public static void CMJNtoRVB(int[] tabCMJN, int[] tabRVB)
    {
        int cyan, magenta, jaune;
        int noir;

        cyan = (int) Math.round((tabCMJN[0] + tabCMJN[3]) * 2.55);
        magenta = (int) Math.round((tabCMJN[1] + tabCMJN[3]) * 2.55);
        jaune = (int) Math.round((tabCMJN[2] + tabCMJN[3]) * 2.55);

        tabRVB[0] = 255 - cyan;
        tabRVB[1] = 255 - magenta;
        tabRVB[2] = 255 - jaune;
    }

// --------------------------------------------------------------------------
// Conversion d'une couleur exprimee en RVB en CMJN
// --------------------------------------------------------------------------
    public static void RVBtoCMJN(int[] tabRVB, int[] tabCMJN)
    {
        int tabCMJ[] = new int[3];
        int cyan, magenta, jaune;
        int noir;

        RVBtoCMJ(tabRVB, tabCMJ);

        cyan = tabCMJ[0];
        magenta = tabCMJ[1];
        jaune = tabCMJ[2];

        noir = cyan;
        if (magenta < noir)
        {
            noir = magenta;
        }
        if (jaune < noir)
        {
            noir = jaune;
        }

        if (noir < 100)
        {
            tabCMJN[0] = cyan - noir;
            tabCMJN[1] = magenta - noir;
            tabCMJN[2] = jaune - noir;
        }
        else
        {
            tabCMJN[0] = 0;
            tabCMJN[1] = 0;
            tabCMJN[2] = 0;
        }

        tabCMJN[3] = noir;
    }

// --------------------------------------------------------------------------
// Calcul du code Hexa d'une couleur
// --------------------------------------------------------------------------
    public static String calculeCodeHexa(Color couleur)
    {
        Integer r, v, b;
        String rHexa, vHexa, bHexa;
        String rvbHexa;

        r = couleur.getRed();
        v = couleur.getGreen();
        b = couleur.getBlue();

        rHexa = Integer.toHexString(r).toUpperCase();
        if (rHexa.length() == 1)
        {
            rHexa = "0" + rHexa;
        }

        vHexa = Integer.toHexString(v).toUpperCase();
        if (vHexa.length() == 1)
        {
            vHexa = "0" + vHexa;
        }

        bHexa = Integer.toHexString(b).toUpperCase();
        if (bHexa.length() == 1)
        {
            bHexa = "0" + bHexa;
        }

        rvbHexa = rHexa + vHexa + bHexa;

        return rvbHexa;
    }

}
