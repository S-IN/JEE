// ==========================================================================
// package daoServeurObjet
// --------------------------------------------------------------------------
// CLASSE PriseServeur
// --------------------------------------------------------------------------
// Un objet de cette classe reference un serveur de type JDBC.
// Le constructeur recoit l'adreese IP et le port du serveur.
// ==========================================================================
package daoServeurObjets;

public class PriseServeur
{

// ==========================================================================
// PROPRIETES
// ==========================================================================
    private String nomMachine;
    private int numeroPort;
    private String formatDate = "yyyy/MM/dd";

// ==========================================================================
// Constructeur
// --------------------------------------------------------------------------
// Le constructeur prend deux parametres :
//
// nomMachine : nom (ou adresse IP) de la machine distante
// numeroPort : numero de port de l'application distante
// ==========================================================================
    public PriseServeur(String nomMachine, int numeroPort)
    {
        this.nomMachine = nomMachine;
        this.numeroPort = numeroPort;
    }

    public String getNomMachine()
    {
        return nomMachine;
    }

    public void setNomMachine(String nomMachine)
    {
        this.nomMachine = nomMachine;
    }

    public int getNumeroPort()
    {
        return numeroPort;
    }

    public void setNumeroPort(int numeroPort)
    {
        this.numeroPort = numeroPort;
    }
   
    public void setFormatDate(String formatDate)
    {
        this.formatDate = formatDate;
    }

// ==========================================================================
// Get du format des donnees 'DATE'
// ==========================================================================
    public String getFormatDate()
    {
        return formatDate;
    }
}