package objetDistant;

import javax.ejb.Remote;

@Remote
public interface CompteurSFRemote
{
    public int getCpt();
    public void setCpt(int a);
}
