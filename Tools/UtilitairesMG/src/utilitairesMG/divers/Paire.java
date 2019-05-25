// ==========================================================================
// package utilitairesMG.divers
// --------------------------------------------------------------------------
// Classe Paire
// --------------------------------------------------------------------------
// Paire est une classe generique car elle comporte une variable de type.
// Une classe peut comporter plusieurs variables de type... On pourrait
// declarer : class Paire<T, U, V>
// --------------------------------------------------------------------------
// La classe Paire aurait pu etre definie sans variable de type en utilisant
// la declaration Object partout ou il y a T. Cela aurait marche tout aussi
// bien. Par contre, on aurait pu creer des objets Paire contenant deux
// elements de type differents, ce que le compilateur refuse ici...
// ==========================================================================
package utilitairesMG.divers;

// --------------------------------------------------------------------------
// <T> est une variable de type.
// --------------------------------------------------------------------------
public class Paire<T>
{
    private T premierElement;
    private T deuxiemeElement;

// --------------------------------------------------------------------------
// Dans le constructeur par defaut, on ne peut initialiser autrement qu'aux
// valeurs null, car on ne connait pas le type T.
// --------------------------------------------------------------------------
    public Paire()
    {
    }

    public Paire(T premierElement, T deuxiemeElement)
    {
        this.premierElement = premierElement;
        this.deuxiemeElement = deuxiemeElement;
    }

    public T getPremierElement()
    {
        return premierElement;
    }

    public T getDeuxiemeElement()
    {
        return deuxiemeElement;
    }

    public void setPremierElement(T premierElement)
    {
        this.premierElement = premierElement;
    }

    public void setDeuxiemeElement(T deuxiemeElement)
    {
        this.deuxiemeElement = deuxiemeElement;
    }

    public String toString()
    {
        return premierElement + " --+-- " + deuxiemeElement;
    }
}
