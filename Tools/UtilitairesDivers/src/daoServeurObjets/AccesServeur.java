// ==========================================================================
// Classe AccesServeur
// --------------------------------------------------------------------------
// Un objet AccesBase permet d'obtenir un accès au ServeurObjets.
// ==========================================================================
package daoServeurObjets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import utilitairesMG.jdbc.JeuResultat;

public class AccesServeur
{
    private PriseServeur prise;
    private Socket socketClient;
    private ObjectInputStream entree;
    private ObjectOutputStream sortie;

// --------------------------------------------------------------------------
// Constructeur
// --------------------------------------------------------------------------
    public AccesServeur(PriseServeur prise)
    {
        this.prise = prise;
    }

    public PriseServeur getPrise()
    {
        return prise;
    }

// --------------------------------------------------------------------------
// Methode d'ouverture d'une connexion (socket) au ServeurObjets
// --------------------------------------------------------------------------
    public Socket getConnection() throws IOException
    {
        socketClient = new Socket(prise.getNomMachine(),
                                  prise.getNumeroPort());

        sortie = new ObjectOutputStream(socketClient.getOutputStream());
        entree = new ObjectInputStream(socketClient.getInputStream());
        return socketClient;
    }

// --------------------------------------------------------------------------
// Methode de fermeture de la connexion
// --------------------------------------------------------------------------
    public void closeConnection() throws IOException
    {
        if ((socketClient != null) && (!socketClient.isClosed()))
        {
            socketClient.close();
        }
    }

// --------------------------------------------------------------------------
// executeQuery (SELECT)
// --------------------------------------------------------------------------
// Cette methode retourne le jeu de resultats obtenu par le Select
// --------------------------------------------------------------------------
    public JeuResultat executeQuery(String requete)
            throws IOException, ClassNotFoundException
    {
        JeuResultat jeuResultat;
        Integer codeRetour;
        String messageErreur;

// --------------------------------------------------------------------------
// Cas de la requete vide.
// --------------------------------------------------------------------------
        if (requete.compareTo("") == 0)
        {
            throw new IOException("Requete vide");
        }

// --------------------------------------------------------------------------
// Ouverture de la connexion avec le serveur JDBC.
// --------------------------------------------------------------------------
// Il faut ouvrir (et fermer) la connexion a chaque appel (executeQuery ou
// executeUpdate), a cause de la conception du serveur d'objets...
// --------------------------------------------------------------------------
// En effet, dans le serveur, le thread client ouvre une socket a la
// reception de la requete (accept), exÃ©cute le requete, envoie le resultat,
// et FERME la socket.
// --------------------------------------------------------------------------
        getConnection();

        try
        {

// --------------------------------------------------------------------------
// Envoi de la requete vers le serveur.
// --------------------------------------------------------------------------
            sortie.writeObject(requete);

            codeRetour = (Integer) entree.readObject();

            if (codeRetour == 0)
            {
                messageErreur = (String) entree.readObject();
                throw new IOException(messageErreur);
            }
            else
            {
                jeuResultat = (JeuResultat) entree.readObject();
                return jeuResultat;
            }
        }
        finally
        {
            closeConnection();
        }
    }

// --------------------------------------------------------------------------
// executeUpdate (INSERT, DELETE, UPDATE)
// --------------------------------------------------------------------------
// Cette methode retourne le nombre de lignes concernees par la requete
// --------------------------------------------------------------------------
    public Integer executeUpdate(String requete)
        throws IOException, ClassNotFoundException
    {
        Integer codeRetour;
        String messageErreur;

// --------------------------------------------------------------------------
// Cas de la requete vide.
// --------------------------------------------------------------------------
        if (requete.compareTo("") == 0)
        {
            throw new IOException("Requete vide");
        }

// --------------------------------------------------------------------------
// Ouverture de la connexion avec le serveur JDBC.
// --------------------------------------------------------------------------
        getConnection();

        try
        {

// --------------------------------------------------------------------------
// Envoi de la requete vers le serveur.
// --------------------------------------------------------------------------
            sortie.writeObject(requete);

            codeRetour = (Integer) entree.readObject();

            if (codeRetour == 0)
            {
                messageErreur = (String) entree.readObject();
                throw new IOException(messageErreur);
            }
            else
            {
                codeRetour = (Integer) entree.readObject();
                return codeRetour;
            }
        }
        finally
        {
            closeConnection();
        }
    }
}
