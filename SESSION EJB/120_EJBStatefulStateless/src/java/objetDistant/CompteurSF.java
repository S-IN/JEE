package objetDistant;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;

@Stateful
public class CompteurSF implements CompteurSFRemote
{
    private int cpt;

    public int getCpt()
    {
        return cpt;
    }

    public void setCpt(int a)
    {
        cpt = a;
    }

    @PreDestroy
    public void decharge()
    {
        System.out.print("Dechargement EJB Stateful..........................");
    }

    @PostConstruct
    public void charge()
    {
        System.out.print("Chargement EJB Stateful............................");
    }
}
