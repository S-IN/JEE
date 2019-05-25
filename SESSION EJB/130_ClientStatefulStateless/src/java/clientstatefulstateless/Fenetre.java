package clientstatefulstateless;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import utilitairesMG.graphique.*;


public class Fenetre extends JFrame implements ActionListener
{
    private JPanel panneauFond;

    private JPanel panneauNord;
    private JTextField saisie;
    private JTextField message;

    private JPanel panneauCentre;
    private JScrollPane defileurCentre;
    private JButton tirage;
    private JButton boutonMajSL;
    private JButton boutonAffSL;
    private JButton boutonMajSF;
    private JButton boutonAffSF;

    public Fenetre(String s)
    {
        super(s);
        addWindowListener(new EcouteWindowClosing());

        panneauFond = new JPanel();
        panneauFond.setPreferredSize(new Dimension(250, 300));
        panneauFond.setLayout(new BorderLayout());

// ---------------------------------------------------------------------------
// Panneau du centre (saisie et affichage)
// ---------------------------------------------------------------------------
        panneauNord = new JPanel();
        panneauNord.setPreferredSize(new Dimension(300, 80));

        saisie = new JTextField(20);
        saisie.setOpaque(true);
        saisie.setEditable(false);
        saisie.setText("0");
        
        message = new JTextField(20);
        message.setOpaque(true);
        message.setEditable(false);
        message.setBackground(Color.white);

        panneauNord.add(saisie);
        panneauNord.add(message);
        
// ---------------------------------------------------------------------------        
// Panneau boutons 
// ---------------------------------------------------------------------------
        tirage = new JButton("TIRAGE");
        tirage.addActionListener(this);
        boutonMajSL = new JButton("MISE A JOUR Stateless");
        boutonMajSL.setForeground(Color.BLUE);
        boutonMajSL.addActionListener(this);
        boutonAffSL = new JButton("AFFICHAGE Stateless");
        boutonAffSL.setForeground(Color.BLUE);
        boutonAffSL.addActionListener(this);
        boutonMajSF = new JButton("MISE A JOUR Stateful");
        boutonMajSF.setForeground(Color.BLACK);
        boutonMajSF.addActionListener(this);
        boutonAffSF = new JButton("AFFICHAGE Stateful");
        boutonAffSF.setForeground(Color.BLACK);
        boutonAffSF.addActionListener(this);
        
        panneauCentre = new JPanel();
        panneauCentre.setLayout(new FlowLayoutMG());

        panneauCentre.add(tirage);
        panneauCentre.add(boutonMajSL);
        panneauCentre.add(boutonAffSL);
        panneauCentre.add(boutonMajSF);
        panneauCentre.add(boutonAffSF);
        FlowLayoutMG.unifieTailleComposants(panneauCentre);
        
        defileurCentre = new JScrollPane(panneauCentre);

        panneauFond.add(panneauNord, BorderLayout.NORTH);
        panneauFond.add(defileurCentre);

        getContentPane().add(panneauFond);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        int compteur;
        if (e.getSource() == boutonMajSL)
        {
            compteur = Integer.parseInt(saisie.getText());
            message.setBackground(Color.BLUE);
            message.setForeground(Color.WHITE);
            Main.ecritCptSL(compteur);
        }
        else
        {
            if (e.getSource() == boutonAffSL)
            {
                message.setBackground(Color.BLUE);
                message.setForeground(Color.WHITE);
                Main.litCptSL();
            }
            else
            {
                if (e.getSource() == boutonMajSF)
                {
                    message.setBackground(Color.BLACK);
                    message.setForeground(Color.WHITE);
                    compteur = Integer.parseInt(saisie.getText());
                    Main.ecritCptSF(compteur);
                }
                else
                {
                    if (e.getSource() == boutonAffSF)
                    {
                        message.setBackground(Color.BLACK);
                        message.setForeground(Color.WHITE);
                        Main.litCptSF();
                    }
                    else
                    {
                        Main.tirageCompteur();
                    }
                }
            }
        }
    }

    public void afficheMessage(String texte)
    {
        message.setText(texte);
    }

    public void afficheSaisie(String texte)
    {
        saisie.setText(texte);
    }

    private class EcouteWindowClosing extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            Main.arreter();
        }
    }
}
