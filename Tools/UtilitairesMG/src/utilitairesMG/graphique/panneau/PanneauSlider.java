// ==========================================================================
// package utilitairesMG.graphique.panneau
// --------------------------------------------------------------------------
// Classe PanneauSlider
// --------------------------------------------------------------------------
// Ce panneau comprend deux composants qui doivent evoluer ensemble :
// un JSlider et un JSpinner.
// --------------------------------------------------------------------------
// Par defaut, le curseur peut varier sans probleme entre la valeur minimale
// et la valeur maximale indiquees dans le constructeur.
// On pourra introduire une valeur de blocage qui permettra d'empecher le
// curseur d'aller au dela de cette valeur.
// --------------------------------------------------------------------------
// Le panneau emet des evenements de type PanneauSliderEvent qui contiennent
// la valeur commune des deux composants JSlider et JSpinner.
// ==========================================================================
package utilitairesMG.graphique.panneau;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.*;
import utilitairesMG.graphique.evenement.*;

public class PanneauSlider extends JPanel implements ChangeListener
{

// ---------------------------------------------------------------------
// Proprietes :
// ---------------------------------------------------------------------
// Les trois composants graphiques.
// La valeur affichee dans le JSlider ET dans le JSpinner.
// La valeur de blocage (0 par defaut).
// ---------------------------------------------------------------------
    private JLabel labelTexte;
    private JSlider slider;
    private JSpinner spinner;

    private int valeur;
    private int valeurBlocage;

// ---------------------------------------------------------------------
// Constructeur :
// ---------------------------------------------------------------------
// Parametres :
//
// texte            : texte du JLabel
// couleurTexte     : couleur de ce texte et des textes du JSlider
// valeur           : valeur au moment de l'instanciation (initiale)
// valeurMin        : valeur minimale du JSlider et du JSpinner
// valeurMax        : valeur maximale du JSlider et du JSpinner
// pas              : pas de modification du JSpinner
// espacementMajeur : espacement des grands traits du JSlider
// espacementMineur : espacement des petits traits du JSlider
// ---------------------------------------------------------------------
    public PanneauSlider(String texte,
        Color couleurTexte,
        int valeur,
        int valeurMin,
        int valeurMax,
        int pas,
        int espacementMajeur,
        int espacementMineur)
    {
        this.valeur = valeur;
        this.valeurBlocage = valeurMax;

// ---------------------------------------------------------------------
// Creation des composants
// ---------------------------------------------------------------------
        labelTexte = new JLabel(texte);
        labelTexte.setForeground(couleurTexte);
        labelTexte.setPreferredSize(new Dimension(55, 20));
        labelTexte.setHorizontalAlignment(JLabel.RIGHT);

        SpinnerModel model = new SpinnerNumberModel(valeur,
            valeurMin,
            valeurMax,
            pas);
        spinner = new JSpinner(model);
        spinner.addChangeListener(this);

        slider = new JSlider(valeurMin, valeurMax, valeur);

// --------------------------------------------------------------------------
// Pour ne pas avoir l'evenement de depart (position initiale du Slider
// --------------------------------------------------------------------------
        slider.setValueIsAdjusting(true);

// --------------------------------------------------------------------------
// setMajorTickSpacing : espacement entre les grands traits (0, 85, 170, 255)
// setMinorTickSpacing : espacement entre les petits traits (85 / 17 = 5)
// setPaintTicks       : affichage des traits
// setPaintLabels      : affichage des legendes des grands traits
// --------------------------------------------------------------------------
        slider.setMajorTickSpacing(espacementMajeur);
        slider.setMinorTickSpacing(espacementMineur);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setOpaque(false);
        slider.setForeground(couleurTexte);
        slider.addChangeListener(this);

        add(labelTexte);
        add(slider);
        add(spinner);
    }

// --------------------------------------------------------------------------
// set et get de la valeur de blocage du curseur du JSlider
// --------------------------------------------------------------------------
    public void setValeurBlocage(int valeurBlocage)
    {
        this.valeurBlocage = valeurBlocage;
    }

    public int getValeurBlocage()
    {
        return valeurBlocage;
    }

// --------------------------------------------------------------------------
// Méthode de l'interface ChangeListener
// --------------------------------------------------------------------------
    public void stateChanged(ChangeEvent e)
    {
        boolean envoiEvenement = true;

// --------------------------------------------------------------------------
// Changement sur le JSpinner.
// --------------------------------------------------------------------------
        if (e.getSource() == spinner)
        {
            valeur = (int) spinner.getValue();
        }
        else
        {
            valeur = (int) slider.getValue();
        }

// --------------------------------------------------------------------------
// Si la valeur entree est superieure a la valeur de blocage, on n'enverra
// pas d'evenement aus ecouteurs.
// --------------------------------------------------------------------------
        if (valeur > valeurBlocage)
        {
            valeur = valeurBlocage;
            envoiEvenement = false;
        }

// --------------------------------------------------------------------------
// Mise a jour de la valeur du JSlider et du JSpinner.
// --------------------------------------------------------------------------
        setValeur(valeur);

        if (envoiEvenement)
        {
            firePanneauSliderSelected(new PanneauSliderEvent(this, valeur));
        }
    }

// --------------------------------------------------------------------------
// get et set de la valeur.
// --------------------------------------------------------------------------
    public int getValeur()
    {
        return valeur;
    }

    public void setValeur(int valeur)
    {
        this.valeur = valeur;
        slider.removeChangeListener(this);
        spinner.removeChangeListener(this);
        slider.setValue(valeur);
        spinner.setValue(valeur);
        slider.addChangeListener(this);
        spinner.addChangeListener(this);
    }

// --------------------------------------------------------------------------
// Methode pour ajouter un listener a la liste des ecouteurs de PanneauSlider
// --------------------------------------------------------------------------
    public void addPanneauSliderListener(PanneauSliderListener p)
    {
        listenerList.add(PanneauSliderListener.class, p);
    }

// --------------------------------------------------------------------------
// Methode pour enlever un listener a la liste des ecouteurs de PanneauSlider
// --------------------------------------------------------------------------
    public void removePanneauSliderListener(PanneauSliderListener p)
    {
        listenerList.remove(PanneauSliderListener.class, p);
    }

// --------------------------------------------------------------------------
// Lancement de l'evenement PanneauSliderEvent
// --------------------------------------------------------------------------
    public void firePanneauSliderSelected(PanneauSliderEvent e)
    {
        EventListener[] listeners
            = listenerList.getListeners(PanneauSliderListener.class);

        for (int i = 0; i < listeners.length; i++)
        {
            PanneauSliderListener listener = 
                (PanneauSliderListener) listeners[i];
            listener.panneauSliderSelected(e);
        }
    }
}
