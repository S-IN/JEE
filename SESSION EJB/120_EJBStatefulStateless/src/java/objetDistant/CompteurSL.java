package objetDistant;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

@Stateless
public class CompteurSL implements CompteurSLRemote
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
        System.out.print("Dechargement EJB Stateless.........................");
    }

    @PostConstruct
    public void charge()
    {
        System.out.print("Chargement EJB Stateless...........................");
    }
  }
