// ==========================================================================
// package utilitairesMG.jdbc
// --------------------------------------------------------------------------
// Classe JeuResultatXML
// --------------------------------------------------------------------------
// Utilisation pratique d'un ResultSet obtenu par un SELECT SQL
// Le resultat est obtenu sous forme d'une String XML (et d'une String DTD)
// ==========================================================================
package utilitairesMG.jdbc;

import java.sql.*;

public class JeuResultatXML implements java.io.Serializable
{

// ==========================================================================
// PROPRIETES
// ==========================================================================
// --------------------------------------------------------------------------
// Proprietes du jeu de resultats : chaine au format XML contenant la DTD
// --------------------------------------------------------------------------
    private String resultatXML;

// ==========================================================================
// CONSTRUCTEUR
// ==========================================================================
    public JeuResultatXML(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd;
        int i;

// --------------------------------------------------------------------------
// Renseignements sur les colonnes
// --------------------------------------------------------------------------
        rsmd = rs.getMetaData();

// --------------------------------------------------------------------------
// Initialisation de la chaine XML
// --------------------------------------------------------------------------
        resultatXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n";
        resultatXML += "<!DOCTYPE RACINE [\n";

// --------------------------------------------------------------------------
// Debut de la chaine DTD
// --------------------------------------------------------------------------
        resultatXML += "<!ELEMENT RACINE (COLONNE*, LIGNE*)>\n";
        resultatXML += "   <!ELEMENT COLONNE (NomColonne, "
            + "TailleColonne, ClasseColonne)>\n";
        resultatXML += "      <!ELEMENT NomColonne (#PCDATA)>\n";
        resultatXML += "      <!ELEMENT TailleColonne (#PCDATA)>\n";
        resultatXML += "      <!ELEMENT ClasseColonne (#PCDATA)>\n";

// --------------------------------------------------------------------------
// Completer la chaine DTD : <!ELEMENT LIGNE (
// --------------------------------------------------------------------------
        resultatXML += "   <!ELEMENT LIGNE (";
        for (i = 1; i <= rsmd.getColumnCount(); i++)
        {
            resultatXML += rsmd.getColumnName(i);
            if (i < rsmd.getColumnCount())
            {
                resultatXML += ", ";
            }
            else
            {
                resultatXML += ")>\n";
            }
        }

// --------------------------------------------------------------------------
// Completer la chaine DTD : divers elements correspondant aux colonnes
// --------------------------------------------------------------------------
        for (i = 1; i <= rsmd.getColumnCount(); i++)
        {
            resultatXML += "      <!ELEMENT ";
            resultatXML += rsmd.getColumnName(i);
            resultatXML += " (#PCDATA)>\n";
        }

        resultatXML += "]>\n\n";

// --------------------------------------------------------------------------
// Creation de l'arbre XML : racine et description des colonnes selectionnees
// --------------------------------------------------------------------------
        resultatXML += "<RACINE>\n";

        for (i = 1; i <= rsmd.getColumnCount(); i++)
        {
            resultatXML += "   <COLONNE>\n";
            resultatXML += "      <NomColonne>";
            resultatXML += rsmd.getColumnName(i);
            resultatXML += "</NomColonne>\n      <TailleColonne>";
            resultatXML += new Integer(rsmd.getColumnDisplaySize(i));
            resultatXML += "</TailleColonne>\n      <ClasseColonne>";
            resultatXML += rsmd.getColumnClassName(i);
            resultatXML += "</ClasseColonne>\n";
            resultatXML += "   </COLONNE>\n";
        }

// --------------------------------------------------------------------------
// Boucle sur les lignes selectionnees
// --------------------------------------------------------------------------
        while (rs.next())
        {
            resultatXML += "   <LIGNE>\n";

// --------------------------------------------------------------------------
// Boucle sur les colonnes selectionnees
// --------------------------------------------------------------------------
// On fait un traitement special pour les colonnes de dates (Timestamp, Time,
// java.sql.Date, java.util.Date).
// Toutes ces classes heritent de java.util.Date.
// --------------------------------------------------------------------------
            for (i = 1; i <= rsmd.getColumnCount(); i++)
            {
                resultatXML += "      <" + rsmd.getColumnName(i) + ">";

                if (rs.getObject(i) != null)
                {
                    String nomClasse = rsmd.getColumnClassName(i);
                    if ((nomClasse.compareTo("java.sql.Timestamp") == 0)
                        || (nomClasse.compareTo("java.sql.Time") == 0)
                        || (nomClasse.compareTo("java.sql.Date") == 0)
                        || (nomClasse.compareTo("java.util.Date") == 0))
                    {
                        resultatXML += ((java.util.Date) rs.getObject(i)).getTime();
                    }
                    else
                    {
                        resultatXML += rs.getObject(i);
                    }
                }

                resultatXML += "</"
                    + rsmd.getColumnName(i)
                    + ">\n";
            }
            resultatXML += "   </LIGNE>\n";
        }

// --------------------------------------------------------------------------
// Fermeture du bloc RACINE
// --------------------------------------------------------------------------
        resultatXML += "</RACINE>";
    }

// ==========================================================================
// METHODES
// ==========================================================================
// --------------------------------------------------------------------------
// Entetes des colonnes selectionnees
// --------------------------------------------------------------------------
    public String getResultatXML()
    {
        return resultatXML;
    }
}
