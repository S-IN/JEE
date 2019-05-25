// ==========================================================================
// package utilitairesMG.divers
// --------------------------------------------------------------------------
// Classe CreationObjet  : creation d'objets
// ==========================================================================
package utilitairesMG.divers;

import java.lang.reflect.*;

public class CreationObjet
{

// ==========================================================================
// Creation d'un objet de la classe "nomClasse".
// --------------------------------------------------------------------------
// Cet objet est cree en utilisant le constructeur dont les parametres sont
// transmis par le parametre "classesParametres". Ce qui est transmis, c'est
// la classe de chaque parametre.
// --------------------------------------------------------------------------
// Cet objet est cree en transmettant les valeurs de chacun des parametres
// dans le tableau "valeursParametres".
// ==========================================================================
    public static Object creeObjet(String nomClasse,
                                   Class<?> classesParametres[],
                                   Object valeursParametres[])
        throws ClassNotFoundException, // forName()
               NoSuchMethodException, // getConstructor()
               InstantiationException, // newInstance()
               IllegalAccessException, // newInstance()
               InvocationTargetException              // newInstance()
    {
        Object retour;

// --------------------------------------------------------------------------
// classe :             classe de l'objet a instancier.
//
// constructeur       : constructeur choisi pour l'instanciation.
//                      S'il n'existe pas, il y a une Exception.
// --------------------------------------------------------------------------
        Class<?> classe;
        Constructor<?> constructeur;

// --------------------------------------------------------------------------
// Creation de l'objet classe.
// --------------------------------------------------------------------------
        classe = Class.forName(nomClasse);

// --------------------------------------------------------------------------
// Recherche du constructeur avec les parametres des types du tableau
// classesParametres.
// --------------------------------------------------------------------------
        constructeur = classe.getConstructor(classesParametres);

// --------------------------------------------------------------------------
// Creation de l'objet avec les valeurs initiales du tableau 
// valeursParametres.
// --------------------------------------------------------------------------
        retour = constructeur.newInstance(valeursParametres);

        return retour;
    }

// ==========================================================================
// Creation d'un objet de la classe "nomClasse".
// --------------------------------------------------------------------------
// Cette methode est une version simplifiee de la methode creeObjet ci-dessus
// pour creer un objet d'une classe qui possede un constructeur avec un
// parametre de type String. Exemples : Integer, String, Double, BigDecimal.
// ==========================================================================
    public static Object creeObjet(String nomClasse, String valeur)
        throws ClassNotFoundException, // forName()
               NoSuchMethodException, // getConstructor()
               InstantiationException, // newInstance()
               IllegalAccessException, // newInstance()
               InvocationTargetException              // newInstance()
    {
        Object retour;

        Class<?> classesParametres[] = new Class<?>[1];
        classesParametres[0] = Class.forName("java.lang.String");
        Object valeursParametres[] = new Object[1];
        valeursParametres[0] = valeur;

        retour = creeObjet(nomClasse, classesParametres, valeursParametres);
        return retour;
    }

// ==========================================================================
// Creation d'un objet de la classe "nomClasse".
// --------------------------------------------------------------------------
// Cette methode est une version simplifiee de la methode creeObjet ci-dessus
// pour creer un objet d'une classe qui possede un constructeur avec un
// parametre de type long. Exemples : Date, Timestamp.
// ==========================================================================
    public static Object creeObjet(String nomClasse, long valeur)
        throws ClassNotFoundException, // forName()
               NoSuchMethodException, // getConstructor()
               InstantiationException, // newInstance()
               IllegalAccessException, // newInstance()
               InvocationTargetException              // newInstance()
    {
        Object retour;

        Class<?> classesParametres[] = new Class<?>[1];
        classesParametres[0] = long.class;
        Object valeursParametres[] = new Object[1];
        valeursParametres[0] = valeur;

        retour = creeObjet(nomClasse, classesParametres, valeursParametres);

        return retour;
    }
}
