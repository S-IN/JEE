// ==========================================================================
// package utilitairesMG.graphique.texte
// --------------------------------------------------------------------------
// Classe DocumentSaisieLimitee
// --------------------------------------------------------------------------
// Document qui permet de limiter le nombre de  caracteres saisis dans une
// zone de texte.
//
// Exemples d'utilisation :
//
//      saisie1 = new JTextField(20);
//      saisie1.setDocument(new DocumentSaisieLimitee(8, "Cocu !"));
//
//      saisie2 = new JTextField(20);
//      saisie2.setDocument(new DocumentSaisieLimitee(40));
// ==========================================================================
package utilitairesMG.graphique.texte;

import javax.swing.text.*;

public class DocumentSaisieLimitee extends PlainDocument
{
    private int limite;

    public DocumentSaisieLimitee(int limite)
    {
        super();
        this.limite = limite;
    }

    public DocumentSaisieLimitee(int limite, String init)
    {
        super();
        this.limite = limite;
        if (init != null)
        {
            insertString(0, init, null);
        }
    }

// --------------------------------------------------------------------------
// Insertion de la chaine s dans le document
// --------------------------------------------------------------------------
// s : chaine a inserer (en saisie, il s'agit du caractere tape).
// off : position de l'insertion (a partir de 0).
// getLength() retourne la taille de la chaine courante.
// --------------------------------------------------------------------------
    public void insertString(int off, String s, AttributeSet a)
    {
        if (s.length() + getLength() <= limite)
        {
            try
            {
                super.insertString(off, s, a);
            }
            catch (BadLocationException e)
            {
                e.printStackTrace();
            }
        }
    }
}
