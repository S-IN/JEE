package clientmapping;

import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import objetDistant.MappingRemote;

public class Main
{
    public static void main(String[] args) throws NamingException
    {

// ----------------------------------------------------------------------------
// Acces a l'objet distant
// ----------------------------------------------------------------------------
        Hashtable variablesEnv = new Hashtable();
        variablesEnv.put("org.omg.CORBA.ORBInitialHost", "localhost");
        variablesEnv.put("org.omg.CORBA.ORBInitialPort", "3700");

        Context ctx = new InitialContext(variablesEnv);
        MappingRemote contact =
                (MappingRemote)ctx.lookup("jndiMapping");

// ----------------------------------------------------------------------------
// Appel de la methode distante
// ----------------------------------------------------------------------------
        try
        {
            System.out.println(contact.lireListeContacts());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
