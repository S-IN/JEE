package objetDistant;

import javax.ejb.Remote;

@Remote
public interface CompteurSLRemote
{
    public int getCpt();
    public void setCpt(int a);
}
