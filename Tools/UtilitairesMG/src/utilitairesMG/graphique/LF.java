// ==========================================================================
// package utilitairesMG.graphique
// --------------------------------------------------------------------------
// LF : classe utilitaire pour le changement de Look&Feel
// ==========================================================================
package utilitairesMG.graphique;

import javax.swing.*;

public class LF
{

// --------------------------------------------------------------------------
// Look and Feel :
// --------------------------------------------------------------------------
    static public final String WindowsLF
        = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    static public final String MetalLF
        = "javax.swing.plaf.metal.MetalLookAndFeel";

    static public final String MotifLF
        = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

    static public final String NimbusLF
        = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

// --------------------------------------------------------------------------
// Chargement du look & feel voulu (par defaut : MetalLF avec fenetre
// principale Windows).
// --------------------------------------------------------------------------
    public static void setLF(String StringLF)
    {
        try
        {
            UIManager.setLookAndFeel(StringLF);
        }
        catch (ClassNotFoundException | 
               InstantiationException | 
               IllegalAccessException | 
               UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

// --------------------------------------------------------------------------
// Changement du look & feel de la fenetre principale
// --------------------------------------------------------------------------
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

// --------------------------------------------------------------------------
// Chargement du look & feel par defaut(celui du Systeme).
// --------------------------------------------------------------------------
    public static void setLF()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | 
               InstantiationException | 
               IllegalAccessException | 
               UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

// --------------------------------------------------------------------------
// Changement du look & feel de la fenetre principale
// --------------------------------------------------------------------------
        JFrame.setDefaultLookAndFeelDecorated(true);
    }
}
