// ==========================================================================
// Classe ModeleColonneTable                              
// ==========================================================================
package utilitairesMG.graphique.table;

import javax.swing.table.*;
import java.util.*;
import utilitairesMG.divers.*;

// ==========================================================================
// Classe ModeleColonneTable :
// --------------------------------------------------------------------------
// Modele des colonnes des tables.
// ==========================================================================
public class ModeleColonneTable extends DefaultTableColumnModel
{

// ==========================================================================
// PROPRIETES
// --------------------------------------------------------------------------
// tailleCaractere : la taille du caractere depend de la police utilisee pour
// l'affichage de la JTable. Elle permet de calculer la taille de chaque
// colonne (exprimee en points).
// --------------------------------------------------------------------------
// nombreCaracteresMax et nombreCaracteresMin : nombre de caracteres max et
// min pour l'affichage initial d'une colonne de la table.
// ==========================================================================
    private int tailleCaractere = 10;
    private int nombreCaracteresMax = 20;
    private int nombreCaracteresMin = 8;

    private Vector<Colonne> listeColonnes;

// ==========================================================================
// CONSTRUCTEURS
// ==========================================================================
    public ModeleColonneTable(Vector<Colonne> listeColonnes, int tailleCaractere)
    {
        this.listeColonnes = listeColonnes;
        this.tailleCaractere = tailleCaractere;
        creerColonnes();
    }

    public ModeleColonneTable(Vector<Colonne> listeColonnes)
    {
        this.listeColonnes = listeColonnes;
        creerColonnes();
    }

// ==========================================================================
// METHODES
// ==========================================================================
// Creation des colonnes, avec les options par defaut
// --------------------------------------------------------------------------
    public void creerColonnes()
    {

// --------------------------------------------------------------------------
// Declaration d'une colonne et de ses caracteristiques :
// nom, taille (en nombre de caracteres) et type.
// --------------------------------------------------------------------------
        TableColumn colonne;

        String nomColonne;
        int nombreCaracteresColonne;
        String typeColonne;

// --------------------------------------------------------------------------
// Nombre de caracteres pour l'affichage initial d'une colonne
// --------------------------------------------------------------------------
        int nombreCaracteresAff;

// --------------------------------------------------------------------------
// Nombre de colonnes a creer, et indice de chaque colonne
// --------------------------------------------------------------------------
        int nombreColonnes;
        int c;

// --------------------------------------------------------------------------
// Nombre de colonnes a creer
// --------------------------------------------------------------------------
        nombreColonnes = listeColonnes.size();

// --------------------------------------------------------------------------
// Boucle de creation des colonnes (TableColumn) du modele de colonnes
// --------------------------------------------------------------------------
        for (c = 0; c < nombreColonnes; c++)
        {

// --------------------------------------------------------------------------
// Creation de l'objet colonne
// --------------------------------------------------------------------------
            colonne = new TableColumn(c);
            
// --------------------------------------------------------------------------
// Lecture des caracteristiques de la colonne dans le vecteur listeColonnes
// --------------------------------------------------------------------------
            nomColonne = listeColonnes.elementAt(c).getNom();
            nombreCaracteresColonne = listeColonnes.elementAt(c).getLongueur();
            typeColonne = listeColonnes.elementAt(c).getType();

// --------------------------------------------------------------------------
// Texte de l'entete de la colonne
// --------------------------------------------------------------------------
            colonne.setHeaderValue(nomColonne);

// --------------------------------------------------------------------------
// Nombre de caracteres a afficher pour la colonne. Puis conversion en nombre
// de points. Enfin, modification de la taille preferee de la colonne.
// --------------------------------------------------------------------------
            nombreCaracteresAff = nombreCaracteresColonne;

            if (nombreCaracteresAff > nombreCaracteresMax)
            {
                nombreCaracteresAff = nombreCaracteresMax;
            }
            if (nombreCaracteresAff < nombreCaracteresMin)
            {
                nombreCaracteresAff = nombreCaracteresMin;
            }

            colonne.setPreferredWidth(nombreCaracteresAff * tailleCaractere);

// --------------------------------------------------------------------------
// En fonction du type de la colonne a afficher, il va falloir changer le
// Rendu et l'Editeur. C'est necessaire, car les choix par defaut sont
// "bizarres". Par exemple, la chaine "date" rendue et celle qui est editee
// sont differentes ! Et quand on valide la chaine editee sans la modifier,
// il y a une erreur de saisie ! Meme remarque pour les donnees Double...
// --------------------------------------------------------------------------
// Pour un bon fonctionnement, choisir un format de rendu et d'edition
// identiques.
// --------------------------------------------------------------------------
// --------------------------------------------------------------------------
// Date
// --------------------------------------------------------------------------
// Le format de Date par defaut est dd/MM/yyyy.
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.util.Date") == 0)
            {
                colonne.setCellRenderer(new RenduDate());
                EditeurDate editeur = new EditeurDate();
                editeur.nombreCaracteres = nombreCaracteresColonne;
                colonne.setCellEditor(editeur);
            }

// --------------------------------------------------------------------------
// Timestamp (classe qui herite de Date)
// --------------------------------------------------------------------------
// Le format de Date par defaut est dd/MM/yyyy.
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.sql.Timestamp") == 0)
            {
                colonne.setCellRenderer(new RenduDate());
                EditeurDate editeur = new EditeurDate();
                editeur.nombreCaracteres = nombreCaracteresColonne;
                colonne.setCellEditor(editeur);
            }

// --------------------------------------------------------------------------
// Float
// --------------------------------------------------------------------------
// Le format de Float par defaut est 1267,00
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.lang.Float") == 0)
            {
                colonne.setCellRenderer(new RenduFloat());
                EditeurFloat editeur = new EditeurFloat();
                editeur.nombreCaracteres = nombreCaracteresColonne;
                colonne.setCellEditor(editeur);
            }

// --------------------------------------------------------------------------
// Double
// --------------------------------------------------------------------------
// Le format de Double par defaut est 1267,00
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.lang.Double") == 0)
            {
                colonne.setCellRenderer(new RenduDouble());
                EditeurDouble editeur = new EditeurDouble();
                editeur.nombreCaracteres = nombreCaracteresColonne;
                colonne.setCellEditor(editeur);
            }

// --------------------------------------------------------------------------
// BigDecimal
// --------------------------------------------------------------------------
// Le format de BigDecimal par defaut est 1267,00
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.math.BigDecimal") == 0)
            {
                colonne.setCellRenderer(new RenduBigDecimal());
                EditeurBigDecimal editeur = new EditeurBigDecimal();
                editeur.nombreCaracteres = nombreCaracteresColonne;
                colonne.setCellEditor(editeur);
            }

// --------------------------------------------------------------------------
// Integer
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.lang.Integer") == 0)
            {
                colonne.setCellRenderer(new RenduInteger());
                EditeurInteger editeur = new EditeurInteger();
                editeur.nombreCaracteres = nombreCaracteresColonne;
                colonne.setCellEditor(editeur);
            }

// --------------------------------------------------------------------------
// Long
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.lang.Long") == 0)
            {
                colonne.setCellRenderer(new RenduLong());
                EditeurLong editeur = new EditeurLong();
                editeur.nombreCaracteres = nombreCaracteresColonne;
                colonne.setCellEditor(editeur);
            }

// --------------------------------------------------------------------------
// Boolean
// --------------------------------------------------------------------------
// On garde l'editeur par defaut (JCheckBox)
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.lang.Boolean") == 0)
            {
                colonne.setCellRenderer(new RenduBoolean());
            }

// --------------------------------------------------------------------------
// Object (en particulier String)
// --------------------------------------------------------------------------
// L'editeur limite la saisie au nombre de caracteres nombreCaracteresColonne
// Si on ne veut pas limiter, on peut ne rien mettre, ou remettre l'editeur
// par defaut.
// --------------------------------------------------------------------------
            if (typeColonne.compareTo("java.lang.String") == 0)
            {
                colonne.setCellRenderer(new RenduObject());
                EditeurObject editeur = new EditeurObject();
                editeur.nombreCaracteres = nombreCaracteresColonne;
                colonne.setCellEditor(editeur);
            }

// --------------------------------------------------------------------------
// La colonne est prete. Ajout au modele.
// --------------------------------------------------------------------------
            addColumn(colonne);
        }
    }

// --------------------------------------------------------------------------
// Modification des tailles par defaut des colonnes (en caracteres)
// --------------------------------------------------------------------------
    public void setNombreCaracteresMax(int nombreCaracteresMax)
    {
        this.nombreCaracteresMax = nombreCaracteresMax;
    }

    public void setNombreCaracteresMin(int nombreCaracteresMin)
    {
        this.nombreCaracteresMin = nombreCaracteresMin;
    }

// --------------------------------------------------------------------------
// Modification des caracteristiques par defaut de la colonne c
// --------------------------------------------------------------------------
    public void setTailleColonne(int c, int taille)
    {
        TableColumn colonne = getColumn(c);
        colonne.setPreferredWidth(taille * tailleCaractere);
    }

// --------------------------------------------------------------------------
// Modification du rendu et de l'editeur de la colonne c
// --------------------------------------------------------------------------
    public void setRenduColonne(int c, TableCellRenderer rendu)
    {
        TableColumn colonne = getColumn(c);
        colonne.setCellRenderer(rendu);
    }

    public void setEditeurColonne(int c, TableCellEditor editeur)
    {
        TableColumn colonne = getColumn(c);
        colonne.setCellEditor(editeur);
    }

    public void modifEditeurColonne(int c, Editeur editeur)
    {
        TableColumn colonne = getColumn(c);
        Editeur e = (Editeur)colonne.getCellEditor();
        editeur.nombreCaracteres = e.nombreCaracteres;
        colonne.setCellEditor(editeur);
    }
}
