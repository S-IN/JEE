// ==========================================================================
// package utilitairesMG.divers
// --------------------------------------------------------------------------
// Classe Chaine
// ==========================================================================
package utilitairesMG.divers;

public class Chaine
{

// ==========================================================================
// recherche de la position du premier(Pc) et du dernier caractere (Dc) non
// blanc d'une chaine.
// --------------------------------------------------------------------------
// valeur retournee : 0   ==> normal
//                    1   ==> chaine vide
//                    2   ==> chaine entierement a blanc
// ==========================================================================
    public static int pcdc(String chaine, Paire<Integer> pairePcDc)
    {
        int longChaine;
        int retour;
        int pc, dc;

        retour = 0;
        longChaine = chaine.length();

        if (longChaine == 0)
        {
            retour = 1;
        }
        else
        {
            pc = 0;

            while ((pc < longChaine) && (chaine.charAt(pc) == ' '))
            {
                pc++;
            }

            if (pc == longChaine)
            {
                retour = 2;
            }
            else
            {
                dc = longChaine - 1;

                while (chaine.charAt(dc) == ' ')
                {
                    dc--;
                }
                pairePcDc.setPremierElement(pc);
                pairePcDc.setDeuxiemeElement(dc);
            }
        }

        return retour;
    }

// ==========================================================================
// Longueur d'une chaine (du premier au dernier caractere non blanc)
// ==========================================================================
    public static int lChaine(String chaine)
    {
        int longChaine;
        Paire<Integer> pairePcDc = new Paire<Integer>();
        int retour;

        retour = Chaine.pcdc(chaine, pairePcDc);

        if (retour == 0)
        {
            longChaine = pairePcDc.getDeuxiemeElement()
                - pairePcDc.getPremierElement() + 1;
        }
        else
        {
            longChaine = 0;
        }
        return longChaine;
    }

// ==========================================================================
// Placer une sous-chaine dans une chaine, a une certaine position, avec
// cadrage a gauche ou a droite de la position
// --------------------------------------------------------------------------
// Parametres :
// chaine  : chaine a remplir
// sChaine : sous chaine a placer dans chaine
// pos     : position de la sous-chaine dans la chaine (0 : debut)
// sens    : 'g' ==> la sous-chaine est placee a gauche de pos
//           'd' ==> la sous-chaine est placee a droite de pos
// ==========================================================================
    public static String placeSousChaine(String chaine,
        String sChaine,
        int pos,
        char sens)
    {
        String chaineTravail;

        int posFinChaine;
        int posDebSChaine;
        int posFinSChaine;
        int i;

// --------------------------------------------------------------------------
// Position de la fin de la chaine
// --------------------------------------------------------------------------
        posFinChaine = chaine.length() - 1;

// --------------------------------------------------------------------------
// Positions du debut et de la fin de la sous chaine
// --------------------------------------------------------------------------
        if (sens == 'd')
        {
            posDebSChaine = pos;
            posFinSChaine = pos + sChaine.length() - 1;
        }
        else
        {
            posDebSChaine = pos - sChaine.length() + 1;
            posFinSChaine = pos;
        }

// --------------------------------------------------------------------------
// Recopier la chaine de la position 0 a posDebSChaine
// Le deuxieme parametre de subString est exclu de la recopie...
// --------------------------------------------------------------------------
        if (posDebSChaine >= 0)
        {
            if (posFinChaine >= posDebSChaine - 1)
            {
                chaineTravail = chaine.substring(0, posDebSChaine);
            }
            else
            {
                chaineTravail = chaine;
                for (i = posFinChaine + 1; i < posDebSChaine; i++)
                {
                    chaineTravail += ' ';
                }
            }

// --------------------------------------------------------------------------
// Ajouter la sous-chaine
// --------------------------------------------------------------------------
            chaineTravail += sChaine;

// --------------------------------------------------------------------------
// Si la chaine depasse posFinSChaine, recopier le reste de la chaine
// --------------------------------------------------------------------------
            if (posFinChaine > posFinSChaine)
            {
                chaineTravail += chaine.substring(posFinSChaine + 1);
            }
        }
        else
        {
            chaineTravail = chaine;
        }

        return chaineTravail;
    }

// ==========================================================================
// Remplir une chaine avec un caractere, entre deux positions
// --------------------------------------------------------------------------
// Parametres :
// chaine  : chaine a remplir
// caract  : caractere a placer dans chaine
// pos1    : position du premier caractere insere
// pos2    : position du dernier caractere insere
// ==========================================================================
    public static String remplirChaine(String chaine, char caract, int pos1, int pos2)
    {
        String chaineTravail;
        int posFinChaine;
        int i;

// --------------------------------------------------------------------------
// Position de la fin de la chaine
// --------------------------------------------------------------------------
        posFinChaine = chaine.length() - 1;

// --------------------------------------------------------------------------
// Recopier la chaine de la position 0 a pos1
// Le deuxieme parametre de subString est exclu de la recopie...
// --------------------------------------------------------------------------
        if (pos1 >= 0)
        {
            if (posFinChaine >= pos1 - 1)
            {
                chaineTravail = chaine.substring(0, pos1);
            }
            else
            {
                chaineTravail = chaine;
                for (i = posFinChaine + 1; i < pos1; i++)
                {
                    chaineTravail += ' ';
                }
            }

// --------------------------------------------------------------------------
// Ajouter les caracteres entre pos1 et pos2
// --------------------------------------------------------------------------
            for (i = pos1; i <= pos2; i++)
            {
                chaineTravail += caract;
            }

// --------------------------------------------------------------------------
// Si la chaine depasse posFinSChaine, recopier le reste de la chaine
// --------------------------------------------------------------------------
            if (posFinChaine > pos2)
            {
                chaineTravail += chaine.substring(pos2 + 1);
            }
        }
        else
        {
            chaineTravail = chaine;
        }

        return chaineTravail;
    }
}
