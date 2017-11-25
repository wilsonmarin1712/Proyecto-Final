
package Juego;

import Controlador.ControladorTeclado;
import java.awt.Color;

/**
 *
 * @author wmarin, almartinez, wflorez
 */
public abstract class ObjetoJuegoControlado extends ObjetoJuego implements Movible {
    
    ControladorTeclado control;
    
    // Constructor for any controllable object
    public ObjetoJuegoControlado(int xPosition, int yPosition, Color color, ControladorTeclado control)
    {
        super(xPosition, yPosition, color);
        this.control = control;
    }
}
