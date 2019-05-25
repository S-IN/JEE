// ==========================================================================
// package animationMG      (interfaces, classes abstraites pour l'animation)
// --------------------------------------------------------------------------
// Classe abstraite DessinAbstract
// ==========================================================================

package animationMG;

import java.awt.*;
import java.awt.geom.*;

public abstract class DessinAbstract implements Animable
{
    public Shape getDessin()
    {
        Arc2D camembert;

        camembert = new Arc2D.Float(Arc2D.PIE);
        camembert.setFrame(0, 0, 100, 100);
        camembert.setAngleStart(30);
        camembert.setAngleExtent(300);

        return camembert;
    }
}